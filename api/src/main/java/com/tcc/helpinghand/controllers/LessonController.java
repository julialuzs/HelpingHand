package com.tcc.helpinghand.controllers;

import com.tcc.helpinghand.models.Lesson;
import com.tcc.helpinghand.models.Question;
import com.tcc.helpinghand.services.LessonService;
import com.tcc.helpinghand.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/lesson")
@RestController
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private QuestionService questionService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Lesson> getAll() {
        return lessonService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/questions")
    public List<Question> getQuestionsByLesson(@PathVariable long id) {
        return questionService.getQuestionsByLesson(id);
    }

}
