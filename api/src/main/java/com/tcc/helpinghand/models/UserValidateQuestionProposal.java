package com.tcc.helpinghand.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserValidateQuestionProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUserValidateQuestionProposal;

    @ManyToOne
    @JoinColumn(name = "idApprover")
    private User approver;

    @ManyToOne
    @JoinColumn(name = "idQuestionProposal")
    private QuestionProposal questionProposal;

    private boolean isApproved;
}
