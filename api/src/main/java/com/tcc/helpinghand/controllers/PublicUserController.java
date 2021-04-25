package com.tcc.helpinghand.controllers;

import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/public/user")
@RestController
public class PublicUserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User signIn(@RequestBody User user) {
        return userService.signIn(user);
    }
}
