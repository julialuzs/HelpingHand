package com.tcc.helpinghand.services;

import com.tcc.helpinghand.controllers.response.LessonProjection;
import com.tcc.helpinghand.controllers.response.LessonResponse;
import com.tcc.helpinghand.enums.Difficulty;
import com.tcc.helpinghand.enums.Status;
import com.tcc.helpinghand.exceptions.ItemNotFoundException;
import com.tcc.helpinghand.models.Lesson;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonService {

    @Autowired
    private LessonRepository repository;

    public List<Lesson> getAll() {
        return repository.findAll();
    }

    public Lesson findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Lesson"));
    }

    //TODO: refactor
    public List<Lesson> getAllByUser(User user) {
        List<LessonProjection> projections = repository.getAllByCurrentUser(user.getIdUser());

        int rightAnswers = 0;

        if (user.getPoints() > 999) {
            rightAnswers = projections.stream()
                    .filter(projection -> projection.getDifficulty().equalsIgnoreCase("Básico"))
                    .mapToInt(LessonProjection::getRightAnswers)
                    .sum();
        } else if (user.getPoints() > 3999) {
            rightAnswers = projections.stream()
                    .filter(projection -> projection.getDifficulty().equalsIgnoreCase("Intermediário"))
                    .mapToInt(LessonProjection::getRightAnswers)
                    .sum();
        }

        int finalRightAnswers = rightAnswers;
        return projections
                .stream()
                .map(lesson -> mapLesson(lesson, finalRightAnswers))
                .collect(Collectors.toList());
    }

    //TODO: refactor
    private Lesson mapLesson(LessonProjection projection, int rightAnswers) {

        Difficulty difficulty = Difficulty.getByName(projection.getDifficulty());
        Lesson lesson = new Lesson();
        lesson.setModule(projection.getModule());
        lesson.setDifficulty(difficulty);
        lesson.setIdLesson(projection.getIdLesson());
        lesson.setImageId(projection.getImageId());

        if (projection.getDifficulty().equals("Básico")) {
            lesson.setStatus(Status.INPROGRESS);
        } else if (projection.getDifficulty().equals("Intermediário")) {

            if (rightAnswers >= 20) {
                lesson.setStatus(Status.INPROGRESS);
            } else {
                lesson.setStatus(Status.BLOCKED);
            }
        } else if (projection.getDifficulty().equals("Avançado")) {

            if (rightAnswers >= 30) {
                lesson.setStatus(Status.INPROGRESS);
            } else {
                lesson.setStatus(Status.BLOCKED);
            }
        }
        return lesson;
    }
}
