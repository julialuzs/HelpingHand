package com.tcc.helpinghand.repositories;

import com.tcc.helpinghand.models.Like;
import com.tcc.helpinghand.models.Post;
import com.tcc.helpinghand.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findAllByPost(Post post);

    boolean existsByUserAndPost(User user, Post post);

    Optional<Like> findByUserAndPost(User user, Post post);
}
