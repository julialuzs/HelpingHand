package com.tcc.helpinghand.services;

import com.tcc.helpinghand.models.Achievement;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.repositories.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchievementService {

    @Autowired
    private AchievementRepository achievementRepository;

    public List<Achievement> getUserAchievements(User user) {

        return achievementRepository.findByUser(user);
    }

}
