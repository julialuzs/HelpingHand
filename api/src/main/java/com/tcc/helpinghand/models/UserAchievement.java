package com.tcc.helpinghand.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserAchievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUserAchievement;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "idAchievement")
    private Achievement achievement;
}
