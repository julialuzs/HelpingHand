package com.tcc.helpinghand.controllers;

import com.tcc.helpinghand.controllers.requests.LoginRequest;
import com.tcc.helpinghand.controllers.response.LoginResponse;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.security.AuthenticationService;
import com.tcc.helpinghand.services.LogService;
import com.tcc.helpinghand.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/public/authentication")
@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private LogService logService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        String email = request.getEmail();
        String password = request.getPassword();

        String token = authenticationService.authenticate(email , password);

        //TODO: register log

        User user = userService.findByEmail(email);
        logService.registerLog(user);

        return new LoginResponse(token);
    }

}
