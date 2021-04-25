package com.tcc.helpinghand.security;

import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User pessoa = getUser(() -> repository.findByEmail(username));
        return CurrentUser.create(pessoa);
    }

    public UserDetails loadUserById(Long id) {
        User pessoa = getUser(() -> repository.findById(id));
        return CurrentUser.create(pessoa);
    }

    private User getUser(Supplier<Optional<User>> supplier) {
        return supplier.get().orElseThrow(() ->
                new UsernameNotFoundException("User not found on database")
        );
    }
}
