package com.tcc.helpinghand;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tcc.helpinghand.models.Lesson;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import static com.tcc.helpinghand.constants.Keys.LESSON_ID;

public class LessonButtonFragment extends Fragment {

    private static final String BUNDLE_LESSON_KEY = "Lesson";

    private ImageButton btLesson;
    private TextView tvLesson;
    private Lesson lesson;

    public LessonButtonFragment() {
    }

    public static LessonButtonFragment newInstance(Lesson lesson) {
        LessonButtonFragment fragment = new LessonButtonFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_LESSON_KEY, lesson);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lesson = (Lesson) getArguments().getSerializable(BUNDLE_LESSON_KEY);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lesson_button, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        initializeComponents();
        boolean isBlocked = lesson.getStatus().toString().equals("BLOCKED"); 

        tvLesson.setText(lesson.getModule().toString());

        if (isBlocked) {
            btLesson.setColorFilter(
                    ContextCompat.getColor(getContext(), R.color.locked),
                    android.graphics.PorterDuff.Mode.MULTIPLY
            );
        }

        btLesson.setOnClickListener(v -> {
            if (isBlocked) {
                Toast.makeText(getContext(), "Lição bloqueada!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getActivity().getApplicationContext(), QuestionActivity.class);
                intent.putExtra(LESSON_ID, this.lesson.getIdLesson());
                startActivity(intent);
            }

        });
    }

    public void initializeComponents() {
        View view = getView();
        this.btLesson = view.findViewById(R.id.bt_lesson);
        this.tvLesson = view.findViewById(R.id.tv_lesson);
        this.setImageOnButton();
    }

    private void setImageOnButton() {
        int resId = getResources().getIdentifier(
                lesson.getImageName(),
                "drawable",
                getActivity().getPackageName());

        this.btLesson.setImageResource(resId);
    }
}