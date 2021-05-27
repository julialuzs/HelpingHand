package com.tcc.helpinghand;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tcc.helpinghand.enums.Difficulty;
import com.tcc.helpinghand.models.Lesson;
import com.tcc.helpinghand.services.LessonService;
import com.tcc.helpinghand.services.RetrofitConfig;
import com.tcc.helpinghand.services.TokenService;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tcc.helpinghand.constants.Keys.LESSON_ID;

public class HomeFragment extends Fragment {

    private static final String LESSONS_LIST_KEY = "Lessons";

    List<Lesson> lessons;
    public LessonService lessonService;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    private void loadLessons() {
        String token = TokenService.getToken(getContext(), getString(R.string.user_token_key));

        this.lessonService.getAllByCurrentUser(token).enqueue(new Callback<List<Lesson>>() {
            @Override
            public void onResponse(Call<List<Lesson>> call, Response<List<Lesson>> response) {
                if (response.isSuccessful()) {
                    lessons = response.body();
                    openLessonsGroupFragment();
                } else {
                    Toast.makeText(getContext(), "Erro de conexão", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Lesson>> call, Throwable t) {
                Toast.makeText(getContext(), "Ocorreu um problema ao carregar os dados do servidor. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openLessonsGroupFragment() {
        List<Lesson> basicLessons = filterByDifficulty(Difficulty.BASIC);
        List<Lesson> intermediateLessons = filterByDifficulty(Difficulty.INTERMEDIATE);
        List<Lesson> advancedLessons = filterByDifficulty(Difficulty.ADVANCED);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.initializeComponents();
        this.loadLessons();

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        initializeComponents();
    }

    private void initializeComponents() {
        RetrofitConfig config = new RetrofitConfig();
        this.lessonService = config.getLessonService();
    }


}