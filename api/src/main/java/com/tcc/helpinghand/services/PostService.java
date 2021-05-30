package com.tcc.helpinghand.services;

import com.tcc.helpinghand.controllers.requests.PostRequest;
import com.tcc.helpinghand.models.Post;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    public PostRepository repository;

    public Post post(User user, PostRequest request) {
        Post post = new Post();

        post.setTitle(request.getTitle());
        post.setBody(request.getBody());
        post.setTag(request.getTag());
        post.setCreatedDate(LocalDateTime.now());
        post.setAuthor(user);

        return repository.save(post);
    }

    public List<Post> getPosts(String tag) {
        if (tag != null) {
            return repository.findByTag(tag);
        }
        return repository.findAll();
    }
}
