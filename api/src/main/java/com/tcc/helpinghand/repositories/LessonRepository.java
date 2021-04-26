package com.tcc.helpinghand.repositories;

import com.tcc.helpinghand.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

}
