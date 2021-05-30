package com.tcc.helpinghand.models.requests;

import java.io.File;

import lombok.Data;

@Data
public class PostRequest {

    private String title;

    private String body;

    private File attachment;

    private String tag;

}
