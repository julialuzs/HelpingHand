package com.tcc.helpinghand.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUserQuestion;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "idQuestion")
    private Question question;
}
