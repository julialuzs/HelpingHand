package com.tcc.helpinghand.services;

import com.tcc.helpinghand.controllers.response.QuestionResponse;
import com.tcc.helpinghand.exceptions.ItemNotFoundException;
import com.tcc.helpinghand.models.Lesson;
import com.tcc.helpinghand.models.Question;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.models.UserQuestion;
import com.tcc.helpinghand.repositories.QuestionRepository;
import com.tcc.helpinghand.repositories.UserQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserQuestionRepository userQuestionRepository;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private UserService userService;

    public List<Question> getQuestionsByLesson(Long lessonId) {
        return questionRepository.findAllByLessonIdLesson(lessonId);
    }

    public Question findById(long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Question"));
    }

    public QuestionResponse answerQuestion(UserQuestion userQuestion) {
        Question question = findById(userQuestion.getQuestion().getIdQuestion());

        long lessonId = userQuestion.getQuestion().getLesson().getIdLesson();
        Lesson lesson = lessonService.findById(lessonId);

        boolean userAnsweredCorrectly = question.getCorrectAnswer().equalsIgnoreCase(userQuestion.getAnswer());

        QuestionResponse response = new QuestionResponse();
        response.setAnswerCorrect(userAnsweredCorrectly);
        userQuestion.setCorrect(userAnsweredCorrectly);

        long previousLevelId = userQuestion.getUser().getLevel().getIdLevel();
        User user = new User();

        if (userAnsweredCorrectly) {
            user = userService.receivePoints(userQuestion.getUser(), lesson.getPoints());
            response.setPointsGained(lesson.getPoints());
        } else {
            response.setPointsGained(0);
        }

        userQuestionRepository.save(userQuestion);
        // TODO: refactor
        boolean userHasLeveledUp = previousLevelId != user.getLevel().getIdLevel();
        response.setLeveledUp(userHasLeveledUp);
        response.setNewLevel(user.getLevel());

        return response;
    }
}
