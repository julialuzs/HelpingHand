package com.tcc.helpinghand.models.requests;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserRequest implements Serializable {

    private String email;
    private String password;

}
