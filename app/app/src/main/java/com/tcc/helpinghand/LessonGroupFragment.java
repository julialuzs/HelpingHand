package com.tcc.helpinghand;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tcc.helpinghand.models.Lesson;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class LessonGroupFragment extends Fragment {

    private static final String ARGS_LESSONS_KEY = "Lessons";
    private TextView tvDifficulty;

    private List<Lesson> lessonsGroup;

    public LessonGroupFragment() {
    }

    public static LessonGroupFragment newInstance(List<Lesson> lessons) {
        LessonGroupFragment fragment = new LessonGroupFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_LESSONS_KEY, (Serializable) lessons);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.lessonsGroup = (List<Lesson>) getArguments().getSerializable(ARGS_LESSONS_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lesson_group, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        initializeComponents();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        for (Lesson lesson: lessonsGroup) {
            Fragment lessonButton = LessonButtonFragment.newInstance(lesson);
            transaction.add(R.id.fl_lesson_buttons, lessonButton);
        }
        transaction.commit();
    }

    public void initializeComponents() {
        View view = getView();
        this.tvDifficulty = view.findViewById(R.id.tv_difficulty);
    }
}