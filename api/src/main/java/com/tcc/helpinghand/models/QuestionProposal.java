package com.tcc.helpinghand.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class QuestionProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idQuestionProposal;

    @ManyToOne
    @JoinColumn(name = "idAuthor")
    private User author;

    private String sign;

    private String description;

    private String answer;

    private String module;

    @ManyToOne
    @JoinColumn(name = "idLesson")
    private Lesson lesson;

}
