package com.tcc.helpinghand.services;

import com.tcc.helpinghand.models.Log;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class LogService {

    @Autowired
    private LogRepository repository;

    public Log registerLog(User user) {
        Log log = new Log(user, LocalDateTime.now());

        return repository.save(log);
    }
}
