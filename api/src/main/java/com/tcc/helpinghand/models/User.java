package com.tcc.helpinghand.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Random;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUser;

    private String email;

    private String name;

    private String password;

    private boolean isDeaf;

    private long points;

    private String inviteCode;

    @ManyToOne
    @JoinColumn(name = "idLevel")
    private Level level;

    public String generateInviteCode() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;

        return new Random()
                .ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
