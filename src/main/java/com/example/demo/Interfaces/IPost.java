package com.example.demo.Interfaces;

import com.example.demo.Models.Post;
import org.springframework.data.repository.CrudRepository;

public interface IPost extends CrudRepository<Post, Long> {
}
