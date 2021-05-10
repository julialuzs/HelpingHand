package com.tcc.helpinghand.controllers;

import com.tcc.helpinghand.models.Comment;
import com.tcc.helpinghand.models.Post;
import com.tcc.helpinghand.security.CurrentUser;
import com.tcc.helpinghand.services.CommentService;
import com.tcc.helpinghand.services.LikeService;
import com.tcc.helpinghand.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/post")
@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post post(@AuthenticationPrincipal CurrentUser currentUser, @RequestBody Post post) {

        return postService.post(currentUser.getUser(), post);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Post> post() {

        return postService.getPosts();
    }

    @PostMapping("/{id}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment comment(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestBody Comment comment,
            @PathVariable("id") int idPost
    ) {

        Post post = new Post();
        post.setIdPost(idPost);
        comment.setPost(post);

        return commentService.comment(currentUser.getUser(), comment);
    }

    @PostMapping("/{id}/like")
    @ResponseStatus(HttpStatus.CREATED)
    public void like(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable("id") int idPost
    ) {
        Post post = new Post();
        post.setIdPost(idPost);
        likeService.like(currentUser.getUser(), post);
    }

}
