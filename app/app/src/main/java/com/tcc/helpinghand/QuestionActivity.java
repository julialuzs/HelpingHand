package com.tcc.helpinghand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.tcc.helpinghand.models.Question;
import com.tcc.helpinghand.services.QuestionService;
import com.tcc.helpinghand.services.RetrofitConfig;

import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private ChipGroup cgOptions;
    private TextView tvDescription;

    public QuestionService questionService;

    public List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        initializeComponents();

        // TODO: change this parameter to use lesson id sent by Intent instead
        Call<List<Question>> call = this.questionService.getQuestionsByLesson(1);

        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if (response.isSuccessful()) {
                    questions = response.body();
                    Question question = questions.get(1);

                    loadUnityFragment(question.getSign());
                    setChipsOnScreen(question);
                    setQuestionDescription(question.getDescription());
                }
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {

            }
        });

    }

    public void loadUnityFragment(String sign) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("sign", sign);
        UnityPlayerFragment fragment = new UnityPlayerFragment();
        fragment.setArguments(bundle);

        fragmentTransaction.add(R.id.fl_unity_fragment, fragment).commit();
    }

    public void setChipsOnScreen(Question question) {
        String[] options = question.getAnswerOptions().split(";");

        for (String option : options) {
            Chip chip = new Chip(this);
            chip.setText(option);
//            chip.setChipBackgroundColorResource(R.color.colorAccent);
//            chip.setTextColor(getResources().getColor(R.color.white));
//            chip.setTextAppearance(R.style.ChipTextAppearance);

            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            cgOptions.addView(chip);
        }
    }

    public void setQuestionDescription(String description) {
        tvDescription.setText(description);
    }

    private void initializeComponents() {
        this.tvDescription = findViewById(R.id.tv_question_description);
        this.cgOptions = findViewById(R.id.cg_options);
        RetrofitConfig config = new RetrofitConfig();
        this.questionService = config.getQuestionService();
    }
}