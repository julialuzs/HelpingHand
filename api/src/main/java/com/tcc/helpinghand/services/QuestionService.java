package com.tcc.helpinghand.services;

import com.tcc.helpinghand.models.Question;
import com.tcc.helpinghand.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getQuestionsByLesson(Long lessonId) {
        return questionRepository.findAllByLessonIdLesson(lessonId);
    }
}
