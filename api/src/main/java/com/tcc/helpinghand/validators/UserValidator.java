package com.tcc.helpinghand.validators;

import com.tcc.helpinghand.exceptions.ValidationErrorException;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements Validator<User> {

    @Autowired
    private UserRepository repository;

    @Override
    public void validate(User user) {
        if (repository.existsByEmail(user.getEmail())) {
            throw new ValidationErrorException("Email já está em uso");
        }
    }
}
