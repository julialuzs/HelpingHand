package com.tcc.helpinghand.services;

import com.tcc.helpinghand.exceptions.ItemNotFoundException;
import com.tcc.helpinghand.models.Lesson;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

//    public List<Lesson> getAllByUser(User user) {
//        return repository.findAll();
//    }
}
