package com.example.demo.Controllers;

import com.example.demo.Interfaces.IPost;
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
public class BlogController {

    @Autowired
    private IPost postRepo;

    @GetMapping("/blog")
    public String blog(Model model) {
        Iterable<Post> posts = postRepo.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("title", "Блог");
        return "blog_main";
    }

    @GetMapping("/blog/post/new")
    public String new_post(Model model){
        return "new_post_form";
    }

    @GetMapping("/blog/post/{id}/edit")
    public String edit_post(@PathVariable(value = "id") long id, Model model){
        if (!postRepo.existsById(id)){
            return "redirect:/blog";
        }

        Optional<Post> post = postRepo.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("posts", res);
        return "post_edit";
    }

    @PostMapping("/blog/post/{id}/edit")
    public String save_edit_post(@PathVariable(value = "id") long id,
                                 @RequestParam(value = "title") String title,
                                 @RequestParam(value = "anons") String anons,
                                 @RequestParam(value = "full_text") String full_text,
                                 Model model){
        Post post = postRepo.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepo.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/post/{id}/delete")
    public String delete_post(@PathVariable(value = "id") long id,
                                 Model model){
        Post post = postRepo.findById(id).orElseThrow();
        postRepo.delete(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/post/new")
    public String save_post(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        Post post = new Post(title, anons, full_text);
        postRepo.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/post/{id}")
    public String post_details(@PathVariable(value = "id") long id, Model model){
        if (!postRepo.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post = postRepo.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("posts", res);
        return "post_details";
    }
}