package com.tcc.helpinghand.repositories;

import com.tcc.helpinghand.models.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LevelRepository extends JpaRepository<Level, Long> {

    //TODO: com essa query, caso o usuário seja Expert, ele não consegue achar o level, já que a quantidade Máxima de pontos é 0. Corrigir
    @Query("SELECT l from Level l where ?1 between l.minPoints and l.maxPoints")
    Level findByAmountOfPoints(long points);
}
