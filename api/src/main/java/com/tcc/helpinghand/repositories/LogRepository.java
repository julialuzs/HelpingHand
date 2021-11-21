package com.tcc.helpinghand.repositories;

import com.tcc.helpinghand.models.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {

    List<Log> findAllByUserIdUser(long id);

}