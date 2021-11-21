package com.tcc.helpinghand.repositories;

import com.tcc.helpinghand.controllers.response.UserDataResponse;
import com.tcc.helpinghand.controllers.response.UserResponse;
import com.tcc.helpinghand.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT " +
            "new com.tcc.helpinghand.controllers.response.UserResponse" +
            "(u.email, u.password, u.name, u.isDeaf, u.points, u.state, u.registrationDate, u.inviteCode, u.level.description) " +
            "FROM User u")
    List<UserResponse> findAllWithoutId();

    @Query("SELECT new com.tcc.helpinghand.controllers.response.UserDataResponse(u.name, u.email, u.isDeaf) " +
            "FROM User u WHERE u.name LIKE %?1%")
    List<UserDataResponse> findLimitedDataByName(String name);

    List<User> findByNameContainingIgnoreCase(String name);

    List<User> findUsersByRegistrationDateBetween(Date initialDate, Date finishDate);

    //
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}