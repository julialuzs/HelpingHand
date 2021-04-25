package com.tcc.helpinghand.controllers;

import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.security.CurrentUser;
import com.tcc.helpinghand.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public User getCurrentUser(@AuthenticationPrincipal CurrentUser userPrincipal) {
        return userPrincipal.getUser();
    }

}
