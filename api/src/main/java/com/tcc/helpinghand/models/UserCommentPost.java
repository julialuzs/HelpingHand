package com.tcc.helpinghand.models;

import lombok.Data;

import javax.persistence.*;
import java.io.File;

@Data
@Entity
public class UserCommentPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUserCommentPost;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "idPost")
    private Post post;

    private String content;

    private File attachment;

}
