package com.tcc.helpinghand.repositories;

import com.tcc.helpinghand.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT distinct q.correctAnswer FROM Question q")
    List<String> findAllCorrectAnswers();

    List<Question> findAllByLessonIdLesson(Long idLesson);
}
