package com.tcc.helpinghand.repositories;

import com.tcc.helpinghand.controllers.response.LessonProjection;
import com.tcc.helpinghand.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {


    @Query(nativeQuery = true,
            value = "with questions_answered as (\n" +
                    "\tselect * from user_question where id_user = :userId and is_correct = 1\n" +
                    ")\n" +
                    "select \n" +
                    "\tl.id_lesson as idLesson,\n" +
                    "\tl.image_name as imageName,\n" +
                    "\tl.difficulty,\n" +
                    "\tl.module,\n" +
                    "\tcount(qa.is_correct) as rightAnswers\n" +
                    "from lesson l\n" +
                    "left join question q on l.id_lesson = q.id_lesson\n" +
                    "left join questions_answered qa on qa.id_question = q.id_question\n" +
                    "group by idLesson")
    List<LessonProjection> getAllByCurrentUser(@Param("userId") Long userId);

}
