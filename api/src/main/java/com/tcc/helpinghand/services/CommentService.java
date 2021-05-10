package com.tcc.helpinghand.services;

import com.tcc.helpinghand.models.Comment;
import com.tcc.helpinghand.models.Post;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    public CommentRepository repository;

    public Comment comment(User user, Comment comment) {
        comment.setUser(user);
        return repository.save(comment);
    }

    public void deleteComment(Comment comment) {
        repository.delete(comment);
    }

    public List<Comment> findAllByPost(Post post) {
        return repository.findAllByPost(post);
    }
}
