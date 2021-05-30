package com.tcc.helpinghand.controllers.requests;

import lombok.Data;

import java.io.File;

@Data
public class PostRequest {

    private String title;

    private String body;

    private File attachment;

    private String tag;

}
