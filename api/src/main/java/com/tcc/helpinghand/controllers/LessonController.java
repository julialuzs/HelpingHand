package com.tcc.helpinghand.controllers;

import com.tcc.helpinghand.controllers.response.LessonProjection;
import com.tcc.helpinghand.controllers.response.LessonResponse;
import com.tcc.helpinghand.controllers.response.QuestionResponse;
import com.tcc.helpinghand.models.Lesson;
import com.tcc.helpinghand.models.Question;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.models.UserQuestion;
import com.tcc.helpinghand.security.CurrentUser;
import com.tcc.helpinghand.services.LessonService;
import com.tcc.helpinghand.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @GetMapping("/current-user")
    public List<Lesson> getAllByCurrentUser(@AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();

        // usuario tem q ter um minimo de pontos
        // + minimo de licoes respondidas corretamente para progredir
        return lessonService.getAllByUser(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/questions")
    public List<Question> getQuestionsByLesson(@PathVariable long id) {
        return questionService.getQuestionsByLesson(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{lessonId}/question/{questionId}")
    public QuestionResponse answerQuestion(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable long lessonId,
            @PathVariable long questionId,
            @RequestBody String answer
    ) {

        User user = currentUser.getUser();
        Lesson lesson = new Lesson();
        lesson.setIdLesson(lessonId);
        Question question = new Question();
        question.setLesson(lesson);
        question.setIdQuestion(questionId);

        UserQuestion userQuestion = new UserQuestion(user, question, answer);

        return questionService.answerQuestion(userQuestion);
    }

}
