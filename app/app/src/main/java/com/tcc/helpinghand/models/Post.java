package com.tcc.helpinghand.models;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Post implements Serializable {

    private long idPost;

    private String title;

    private String body;

    private File attachment;

    private String tag;

    private User author;

    private String createdDate;

}
