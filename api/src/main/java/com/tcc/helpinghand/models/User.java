package com.tcc.helpinghand.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Random;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUser;

    // @Email(message = "O e-mail precisa estar em um formato válido!")
    private String email;

    private String name;

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private boolean isDeaf;

    private long points;

    private String inviteCode;

    @ManyToOne
    @JoinColumn(name = "idLevel")
    private Level level;

//    private ArrayList<QuestionProposal> questionsProposed;
//
//    private ArrayList<Log> logs;

    public String generateInviteCode() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
