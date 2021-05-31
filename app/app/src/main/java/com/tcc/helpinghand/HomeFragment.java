package com.tcc.helpinghand;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.tcc.helpinghand.enums.Difficulty;
import com.tcc.helpinghand.models.Lesson;
import com.tcc.helpinghand.services.LessonService;
import com.tcc.helpinghand.services.RetrofitConfig;
import com.tcc.helpinghand.services.TokenService;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tcc.helpinghand.constants.RequestMessages.SERVER_ERROR;

public class HomeFragment extends Fragment {

    public LessonService lessonService;
    List<Lesson> lessons;

    CircularProgressIndicator progressCircle;

    public HomeFragment() { }

    private void loadLessons() {
        String token = TokenService.getToken(getContext(), getString(R.string.user_token_key));
        progressCircle.show();
        this.lessonService.getAllByCurrentUser(token).enqueue(new Callback<List<Lesson>>() {
            @Override
            public void onResponse(Call<List<Lesson>> call, Response<List<Lesson>> response) {
                if (response.isSuccessful()) {
                    lessons = response.body();
                    openLessonsGroupFragment();
                    progressCircle.hide();
                } else {
                    Toast.makeText(getContext(), "Erro de conexão", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Lesson>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), SERVER_ERROR, Toast.LENGTH_SHORT).show();
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

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        this.initializeComponents();
        this.loadLessons();
    }

    private void initializeComponents() {
        RetrofitConfig config = new RetrofitConfig();
        this.lessonService = config.getLessonService();
        progressCircle = getView().findViewById(R.id.cpi_loading);
    }

}