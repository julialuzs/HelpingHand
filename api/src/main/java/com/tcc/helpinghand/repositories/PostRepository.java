package com.tcc.helpinghand.repositories;

import com.tcc.helpinghand.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
