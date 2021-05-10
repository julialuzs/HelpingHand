package com.tcc.helpinghand.services;

import com.tcc.helpinghand.models.Like;
import com.tcc.helpinghand.models.Post;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    public LikeRepository repository;

    public void like(User user, Post post) {
        Optional<Like> optional = repository.findByUserAndPost(user, post);

        if (optional.isPresent()) {
            repository.delete(optional.get());
        } else {
            Like like = new Like(user, post);
            repository.save(like);
        }

    }

    public boolean existsByPostAndUser(User user, Post post) {
        return repository.existsByUserAndPost(user, post);
    }

}
