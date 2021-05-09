package com.tcc.helpinghand.repositories;

import com.tcc.helpinghand.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
