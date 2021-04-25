package com.tcc.helpinghand.controllers;

import com.tcc.helpinghand.controllers.requests.LoginRequest;
import com.tcc.helpinghand.controllers.response.LoginResponse;
import com.tcc.helpinghand.security.AuthenticationService;
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

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        String username = request.getEmail();
        String password = request.getPassword();

        String token = authenticationService.authenticate(username, password);

        return new LoginResponse(token);
    }

}
