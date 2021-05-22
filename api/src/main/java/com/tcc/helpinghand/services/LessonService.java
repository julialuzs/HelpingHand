package com.tcc.helpinghand.services;

import com.tcc.helpinghand.controllers.response.LessonProjection;
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

    Lesson findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Lesson"));
    }

    public List<Lesson> getAllByUser(User user) {
        List<LessonProjection> projections = repository.getAllByCurrentUser(user.getIdUser());

        Difficulty difficulty = Difficulty.ADVANCED;

        if (user.getPoints() > 999) {
            difficulty = Difficulty.BASIC;
        } else if (user.getPoints() > 3999) {
            difficulty = Difficulty.INTERMEDIATE;
        }

        int rightAnswers = getAmountOfCorrectAnswers(projections, difficulty);

        return projections
                .stream()
                .map(lesson -> mapLesson(lesson, rightAnswers))
                .collect(Collectors.toList());
    }

    private int getAmountOfCorrectAnswers(List<LessonProjection> projections, Difficulty difficulty) {
        return projections.stream()
                .filter(projection -> projection.getDifficulty().equalsIgnoreCase(difficulty.getLabel()))
                .mapToInt(LessonProjection::getRightAnswers)
                .sum();
    }

    private Lesson mapLesson(LessonProjection projection, int rightAnswers) {
        Lesson lesson = new Lesson();
        lesson.setIdLesson(projection.getIdLesson());

        Difficulty difficulty = Difficulty.getByName(projection.getDifficulty());
        lesson.setDifficulty(difficulty);

        lesson.setModule(projection.getModule());
        lesson.setImageName(projection.getImageName());

        Status status = Status.INPROGRESS;

        if (projection.getDifficulty().equals(Difficulty.INTERMEDIATE.getLabel())) {
            status = rightAnswers >= 20 ? Status.INPROGRESS : Status.BLOCKED;

        } else if (projection.getDifficulty().equals(Difficulty.ADVANCED.getLabel())) {
            status = rightAnswers >= 30 ? Status.INPROGRESS : Status.BLOCKED;
        }

        lesson.setStatus(status);
        return lesson;
    }
}
