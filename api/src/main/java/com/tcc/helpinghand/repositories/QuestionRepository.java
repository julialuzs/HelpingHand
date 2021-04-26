package com.tcc.helpinghand.repositories;

import com.tcc.helpinghand.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByLessonIdLesson(Long idLesson);
}
