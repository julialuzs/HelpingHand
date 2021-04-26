package com.tcc.helpinghand.services;

import com.tcc.helpinghand.models.Post;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    public Post post(User user, Post post) {

        post.setAuthor(user);

        return repository.save(post);
    }
}
