package com.tcc.helpinghand;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tcc.helpinghand.enums.Difficulty;
import com.tcc.helpinghand.models.Lesson;
import com.tcc.helpinghand.services.LessonService;
import com.tcc.helpinghand.services.RetrofitConfig;
import com.tcc.helpinghand.services.TokenService;

import java.util.List;
import java.util.stream.Collectors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity  {

    private BottomNavigationView bottomNavigation;

    List<Lesson> lessons;
    public LessonService lessonService;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        this.initializeComponents();
        this.setPointsBar();
        this.setNavBarClickListener();
        this.loadLessons();
    }

    private void loadLessons() {
        String token = TokenService.getToken(MainActivity.this, getString(R.string.user_token_key));

        this.lessonService.getAllByCurrentUser(token).enqueue(new Callback<List<Lesson>>() {
            @Override
            public void onResponse(Call<List<Lesson>> call, Response<List<Lesson>> response) {
                if (response.isSuccessful()) {
                    lessons = response.body();
                    openLessonsGroupFragment();
                } else {
                    Toast.makeText(MainActivity.this, ":(((", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Lesson>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ocorreu um problema ao carregar os dados do servidor. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openLessonsGroupFragment() {
        List<Lesson> basicLessons = filterByDifficulty(Difficulty.BASIC);
        List<Lesson> intermediateLessons = filterByDifficulty(Difficulty.INTERMEDIATE);
        List<Lesson> advancedLessons = filterByDifficulty(Difficulty.ADVANCED);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Fragment fragmentBasic = LessonGroupFragment.newInstance(basicLessons);
        transaction.add(R.id.fl_lesson_group, fragmentBasic);

        Fragment fragmentIntermediate = LessonGroupFragment.newInstance(intermediateLessons);
        transaction.add(R.id.fl_lesson_group, fragmentIntermediate);

        Fragment fragmentAdvanced = LessonGroupFragment.newInstance(advancedLessons);
        transaction.add(R.id.fl_lesson_group, fragmentAdvanced);

        transaction.commit();
    }


    private List<Lesson> filterByDifficulty(Difficulty difficulty) {
        return lessons.stream()
                .filter(lesson -> lesson.getDifficulty() == difficulty)
                .collect(Collectors.toList());
    }

    private void initializeComponents() {
        this.bottomNavigation = (BottomNavigationView) findViewById(R.id.bnv_nav_bar);

        RetrofitConfig config = new RetrofitConfig();
        this.lessonService = config.getLessonService();
    }

    public void setPointsBar() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = PointsFragment.newInstance();
        transaction.add(R.id.teste1, fragment);
        transaction.commit();
    }

    private void setNavBarClickListener() {
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment = new HomeFragment();

            switch (item.getItemId()) {
                case R.id.nav_item_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.nav_item_dictionary:
                    fragment = new DictionaryFragment();
                    break;
                case R.id.nav_item_feed:
                    fragment = new FeedFragment();
                    break;
                case R.id.nav_item_person:
                    fragment = new ProfileFragment();
                    break;
            }

            transaction.replace(R.id.fl_fragment_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        });
    }


}