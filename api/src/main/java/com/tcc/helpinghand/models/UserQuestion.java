package com.tcc.helpinghand.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserQuestion {

    public UserQuestion(User user, Question question, String answer) {
        this.question = question;
        this.user = user;
        this.answer = answer;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUserQuestion;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "idQuestion")
    private Question question;

    @Column(nullable = false)
    private String answer;

    @Column(nullable = false)
    private boolean isCorrect;
}
