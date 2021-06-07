package com.tcc.helpinghand.services;

import com.tcc.helpinghand.models.Comment;
import com.tcc.helpinghand.models.Post;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;

    public Comment comment(User user, Comment comment) {
        comment.setUser(user);
        comment.setCreatedDate(LocalDateTime.now());
        return this.repository.save(comment);
    }

    public void deleteComment(Comment comment) {
        repository.delete(comment);
    }

    public List<Comment> findAllByPost(Post post) {
        return this.repository.findAllByPost(post);
    }
}
