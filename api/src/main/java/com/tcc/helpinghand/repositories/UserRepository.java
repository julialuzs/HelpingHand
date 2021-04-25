package com.tcc.helpinghand.repositories;

import com.tcc.helpinghand.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.name LIKE %?1% OR u.email LIKE %?1%")
    Optional<User> findByNameOrEmail(String nomeOuEmail);

}
