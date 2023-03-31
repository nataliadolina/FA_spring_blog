package com.example.demo.Controllers;

import com.example.demo.Interfaces.IComment;
import com.example.demo.Interfaces.IPost;
import com.example.demo.Models.Comment;
import com.example.demo.Models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class CommentController {

    @Autowired
    private IComment commentRepo;
    @Autowired
    private IPost postRepo;

    @GetMapping("/blog/post/{id}/comments")
    public String comments(@PathVariable(value = "id") long post_id, Model model){
        Optional<Post> post = postRepo.findById(post_id);
        ArrayList<Post> post_res = new ArrayList<>();
        post.ifPresent(post_res::add);
        ArrayList<Comment> comments = commentRepo.findByPost(post_res.get(0));
        model.addAttribute("comments", comments);
        model.addAttribute("post_id", post_id);
        return "comments";
    }

    @GetMapping("/blog/post/{post_id}/comment/new")
    public String add_comment(@PathVariable(value = "post_id") long post_id, Model model) {
        model.addAttribute("title", "Новый комментарий");
        model.addAttribute("post_id", post_id);
        return "new_comment_form";
    }

    @PostMapping("/blog/post/{post_id}/comment/{comment_id}/delete")
    public String delete_comment(@PathVariable(value = "post_id") long post_id, @PathVariable(value = "comment_id") long comment_id, Model model) {
        Comment comment = commentRepo.findById(comment_id).orElseThrow();
        commentRepo.delete(comment);
        return "redirect:/blog/post/{post_id}/comments";
    }

    @PostMapping("/blog/post/{id}/comment/new")
    public String save_new_comment(@PathVariable(value = "id") long post_id,
                                   @RequestParam String text,
                                   Model model) {

        Optional<Post> post = postRepo.findById(post_id);
        ArrayList<Post> postRes = new ArrayList<>();
        post.ifPresent(postRes::add);
        Comment comment = new Comment(text, postRes.get(0));
        commentRepo.save(comment);
        return "redirect:/blog/post/{id}/comments";
    }
}
