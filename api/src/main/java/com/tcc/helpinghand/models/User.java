package com.tcc.helpinghand.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;

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

}
