package com.tcc.helpinghand.repositories;

import com.tcc.helpinghand.models.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {

}