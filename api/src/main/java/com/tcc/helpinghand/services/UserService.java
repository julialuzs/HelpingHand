package com.tcc.helpinghand.services;

import com.tcc.helpinghand.controllers.response.UserDataResponse;
import com.tcc.helpinghand.controllers.response.UserResponse;
import com.tcc.helpinghand.exceptions.ItemNotFoundException;
import com.tcc.helpinghand.models.Level;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.repositories.LevelRepository;
import com.tcc.helpinghand.repositories.UserRepository;
import com.tcc.helpinghand.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
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
        user.setRegistrationDate(Date.from(Instant.now()));

        Level level = new Level();
        level.setIdLevel(1);
        user.setLevel(level);

        return repository.save(user);
    }

    public User findById(long id) {
        Optional<User> optional = repository.findById(id);
        return optional.orElseThrow(() -> new ItemNotFoundException("User"));
    }

    public List<UserResponse> getAllWithoutId() {
        return repository.findAllWithoutId();
    }

    public List<UserDataResponse> findLimitedDataByName(String name) {
        return repository.findLimitedDataByName(name);
    }

    public List<User> findByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    public Page<User> getAllPaginated(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<User> findByRegistrationDate(Date initialDate, Date finishDate) {
        return repository.findUsersByRegistrationDateBetween(initialDate, finishDate);
    }

    public User edit(long id, User user) {
        user.setIdUser(id);
        return repository.save(user);
    }

    public void delete(long id) {
        User user = new User();
        user.setIdUser(id);
        repository.delete(user);
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
