package com.tcc.helpinghand.services;

import com.tcc.helpinghand.models.Lesson;
import com.tcc.helpinghand.repositories.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {

    private LessonRepository repository;

    public List<Lesson> getAll() {
        return repository.findAll();
    }
}
