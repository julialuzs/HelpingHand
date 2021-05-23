package com.tcc.helpinghand.models.responses;

import java.io.Serializable;

import lombok.Data;

@Data
public class LoginResponse implements Serializable {

    private String accessToken;
}
