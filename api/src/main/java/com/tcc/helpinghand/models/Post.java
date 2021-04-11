package com.tcc.helpinghand.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.File;
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

}
