package com.tcc.helpinghand.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String email;
    private String password;
    private String name;
    private boolean isDeaf;
    private long points;
    private String state;
    private Date registrationDate;
    private String inviteCode;
    private String level;
}