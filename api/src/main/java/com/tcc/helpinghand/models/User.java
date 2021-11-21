package com.tcc.helpinghand.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Random;

@Data
@Entity
public class User {

    public static final int INVITE_CODE_SIZE = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUser;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    private boolean isDeaf;

    private long points;

    private String state;

    @JsonFormat(pattern="dd-MM-yyyy")
    private Date registrationDate;

    @Column(length = 10, nullable = false)
    private String inviteCode;

    @ManyToOne
    @JoinColumn(name = "idLevel")
    private Level level;

    public String generateInviteCode() {
        int leftLimit = 48; // number 0
        int rightLimit = 122; // letter Z

        return new Random()
                .ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(INVITE_CODE_SIZE)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
