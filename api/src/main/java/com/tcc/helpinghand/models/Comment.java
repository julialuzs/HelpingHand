package com.tcc.helpinghand.models;

import lombok.Data;

import javax.persistence.*;
import java.io.File;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idComment;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "idPost")
    private Post post;

    @Column(nullable=false)
    private String content;

    private File attachment;

}
