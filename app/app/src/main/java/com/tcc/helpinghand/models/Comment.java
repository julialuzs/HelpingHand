package com.tcc.helpinghand.models;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Comment implements Serializable {

    private long idComment;

    private User user;

    private Post post;

    private String content;

    private File attachment;

    private String createdDate;

}
