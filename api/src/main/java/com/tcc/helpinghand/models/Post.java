package com.tcc.helpinghand.models;

import lombok.Data;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPost;

    private String title;

    private String body;

    private File attachment;

    private String tag;

    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "idAuthor")
    private User author;

}
