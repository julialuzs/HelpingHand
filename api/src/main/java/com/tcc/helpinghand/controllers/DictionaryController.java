package com.tcc.helpinghand.controllers;

import com.tcc.helpinghand.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@RequestMapping("/dictionary")
@RestController
public class DictionaryController {

    @Autowired
    private QuestionService questionService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<String> getDictionary() throws IOException {
        return questionService.getAllCorrectAnswers();
    }
}
