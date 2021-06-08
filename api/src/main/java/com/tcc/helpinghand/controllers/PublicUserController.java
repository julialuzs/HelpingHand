package com.tcc.helpinghand.controllers;

import com.tcc.helpinghand.controllers.requests.LoginRequest;
import com.tcc.helpinghand.controllers.response.LoginResponse;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.security.AuthenticationService;
import com.tcc.helpinghand.services.LogService;
import com.tcc.helpinghand.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/public")
@RestController
public class PublicUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private LogService logService;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse signIn(@RequestBody User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        userService.signIn(user);

        String token = authenticationService.authenticate(email, password);

        logService.registerLog(user);

        return new LoginResponse(token);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse login(@RequestBody LoginRequest request) {

        String email = request.getEmail();
        String password = request.getPassword();

        User user = userService.findByEmail(email);

        String token = authenticationService.authenticate(email, password);

        logService.registerLog(user);

        return new LoginResponse(token);
    }
}
