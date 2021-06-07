package com.tcc.helpinghand.services;

import com.tcc.helpinghand.controllers.requests.PostRequest;
import com.tcc.helpinghand.models.Like;
import com.tcc.helpinghand.models.Post;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.repositories.LikeRepository;
import com.tcc.helpinghand.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    public PostRepository postRepository;

    @Autowired
    public LikeRepository likeRepository;

    public Post post(User user, PostRequest request) {
        Post post = new Post();

        post.setTitle(request.getTitle());
        post.setBody(request.getBody());
        post.setTag(request.getTag());
        post.setCreatedDate(LocalDateTime.now());
        post.setAuthor(user);

        return postRepository.save(post);
    }

    public List<Post> getPosts(String tag, User user) {
        List<Post> posts;
        posts = tag != null ?
                postRepository.findByTag(tag) :
                postRepository.findAll();

        for (Post post : posts) {

           List<Like> likes = likeRepository.findAllByPost(post);
           post.setLikesCount(likes.size());

           boolean isLikedByMe = likes
                   .stream()
                   .anyMatch(like -> like.getUser().getIdUser() == user.getIdUser());

           post.setLikedByMe(isLikedByMe);
        }
        return posts;
    }
}
