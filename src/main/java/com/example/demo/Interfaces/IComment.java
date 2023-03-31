package com.example.demo.Interfaces;

import com.example.demo.Models.Comment;
import com.example.demo.Models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface IComment extends CrudRepository<Comment, Long> {
    ArrayList<Comment> findByPost(@Param("post") Post post);
}
