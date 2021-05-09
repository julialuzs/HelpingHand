package com.tcc.helpinghand.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="\"like\"")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idLike;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "idPost")
    private Post post;

}
