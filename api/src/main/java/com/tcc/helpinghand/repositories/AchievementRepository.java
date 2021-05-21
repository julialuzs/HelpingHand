package com.tcc.helpinghand.repositories;

import com.tcc.helpinghand.models.Achievement;
import com.tcc.helpinghand.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    @Query("SELECT a FROM UserAchievement ua INNER JOIN ua.achievement a where ua.user=?1")
    List<Achievement> findByUser(User user);
}
