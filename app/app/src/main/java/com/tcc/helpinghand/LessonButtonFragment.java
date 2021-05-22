package com.tcc.helpinghand;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.tcc.helpinghand.models.Lesson;

import org.jetbrains.annotations.NotNull;

import static com.tcc.helpinghand.constants.Keys.LESSON_ID;

public class LessonButtonFragment extends Fragment {

    private static final String BUNDLE_LESSON_KEY = "Lesson";

    private ImageButton btLesson;
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

        btLesson.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), QuestionActivity.class);
            intent.putExtra(LESSON_ID, this.lesson.getIdLesson());
            startActivity(intent);
        });
    }

    public void initializeComponents() {
        View view = getView();
        this.btLesson = view.findViewById(R.id.bt_lesson);
        this.setImageOnButton();
    }

    private void setImageOnButton() {
        int resId = getResources().getIdentifier(
                lesson.getImageName(),
                "drawable" ,
                getActivity().getPackageName());

        this.btLesson.setImageResource(resId);
    }
}