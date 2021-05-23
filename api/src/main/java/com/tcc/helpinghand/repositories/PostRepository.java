package com.tcc.helpinghand.repositories;

import com.tcc.helpinghand.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTag(String tag);
}
