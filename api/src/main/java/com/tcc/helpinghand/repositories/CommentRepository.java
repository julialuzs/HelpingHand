package com.tcc.helpinghand.repositories;

import com.tcc.helpinghand.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository  extends JpaRepository<Comment, Long> {
}
