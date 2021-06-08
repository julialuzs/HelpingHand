package com.tcc.helpinghand.services;

import com.tcc.helpinghand.exceptions.ItemNotFoundException;
import com.tcc.helpinghand.models.Level;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.repositories.LevelRepository;
import com.tcc.helpinghand.repositories.UserRepository;
import com.tcc.helpinghand.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserValidator validator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LevelRepository levelRepository;

    public User signIn(User user) {

        validator.validate(user);
        user.setInviteCode(user.generateInviteCode());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Level level = new Level();
        level.setIdLevel(1);
        user.setLevel(level);

        return repository.save(user);
    }

    public User findById(long id) {
        Optional<User> optional = repository.findById(id);

        return optional.orElseThrow(() -> new ItemNotFoundException("User"));
    }

    public User findByEmail(String email) {
        Optional<User> optional = repository.findByEmail(email);

        return optional.orElseThrow(() -> new ItemNotFoundException("User"));
    }

    public User receivePoints(User user, long points) {
        long currentPoints = user.getPoints();
        user.setPoints(currentPoints + points);

        Level level = levelRepository.findByAmountOfPoints(user.getPoints());

        boolean userHasLeveledUp = level.getIdLevel() != user.getLevel().getIdLevel();

        if (userHasLeveledUp) {
            user.setLevel(level);
        }

        return repository.save(user);
//        return user.getPoints();
    }

}
