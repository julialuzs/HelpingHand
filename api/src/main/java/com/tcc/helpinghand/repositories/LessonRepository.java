package com.tcc.helpinghand.repositories;

import com.tcc.helpinghand.models.Lesson;
import com.tcc.helpinghand.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {


//    @Query
//    List<Lesson> findByCurrentUser(User user);

//    with questions_answered as (
//            select * from user_question where id_user = 5
//    )
//    select l.*, qa.* from lesson l
//    left join question q on l.id_lesson = q.id_lesson
//    left join questions_answered qa on qa.id_question = q.id_question
}
