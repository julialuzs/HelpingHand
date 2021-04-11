package com.tcc.helpinghand.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserLikePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUserLikePost;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "idPost")
    private Post post;

}
