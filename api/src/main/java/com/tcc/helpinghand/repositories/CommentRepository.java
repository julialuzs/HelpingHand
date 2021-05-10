package com.tcc.helpinghand.repositories;

import com.tcc.helpinghand.models.Comment;
import com.tcc.helpinghand.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository  extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);
}
