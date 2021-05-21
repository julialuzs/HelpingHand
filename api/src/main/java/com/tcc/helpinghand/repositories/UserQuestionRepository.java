package com.tcc.helpinghand.repositories;

import com.tcc.helpinghand.models.UserQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQuestionRepository extends JpaRepository<UserQuestion, Long> {

}
