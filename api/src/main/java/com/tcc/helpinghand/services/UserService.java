package com.tcc.helpinghand.services;

import com.tcc.helpinghand.exceptions.ItemNotFoundException;
import com.tcc.helpinghand.models.Level;
import com.tcc.helpinghand.models.User;
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

    public User signIn(User usuario) {

        validator.validate(usuario);
        usuario.setInviteCode(usuario.generateInviteCode());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Level level = new Level();
        level.setIdLevel(1);
        usuario.setLevel(level);

        return repository.save(usuario);
    }

    public User findById(long id) {

        Optional<User> optional = repository.findById(id);

        return optional.orElseThrow(() -> new ItemNotFoundException("User"));
    }

}
