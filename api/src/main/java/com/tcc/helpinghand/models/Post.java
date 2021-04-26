package com.tcc.helpinghand.models;

import lombok.Data;

import javax.persistence.*;
import java.io.File;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPost;

    private String title;

    private String body;

    private File attachment;

    // TODO: create enum???
    private String tag;

    @ManyToOne
    @JoinColumn(name = "idAuthor")
    private User author;

}
