package com.tcc.helpinghand.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable {
    private long idUser;

    private String email;

    private String name;

    private String password;

    private boolean isDeaf;

    private long points;

    private String inviteCode;

    private Level level;
}
