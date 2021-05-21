package com.tcc.helpinghand.controllers;

import com.tcc.helpinghand.models.Achievement;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.security.CurrentUser;
import com.tcc.helpinghand.services.AchievementService;
import com.tcc.helpinghand.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AchievementService achievementService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public User getCurrentUser(@AuthenticationPrincipal CurrentUser currentUser) {
        return currentUser.getUser();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}/achievements")
    public List<Achievement> getUserAchievements(@AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();

        return achievementService.getUserAchievements(user);
    }

}
