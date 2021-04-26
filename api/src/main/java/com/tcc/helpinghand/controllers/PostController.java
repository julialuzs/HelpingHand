package com.tcc.helpinghand.controllers;

import com.tcc.helpinghand.models.Post;
import com.tcc.helpinghand.security.CurrentUser;
import com.tcc.helpinghand.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/post")
@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post post(@AuthenticationPrincipal CurrentUser currentUser, @RequestBody Post post) {

        return postService.post(currentUser.getUser(), post);
    }

}
