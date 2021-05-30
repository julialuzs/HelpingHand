package com.tcc.helpinghand;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.tcc.helpinghand.dialogs.MessageDialog;
import com.tcc.helpinghand.models.Question;
import com.tcc.helpinghand.models.requests.AnswerQuestionRequest;
import com.tcc.helpinghand.models.responses.QuestionResponse;
import com.tcc.helpinghand.services.LessonService;
import com.tcc.helpinghand.services.RetrofitConfig;
import com.tcc.helpinghand.services.TokenService;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tcc.helpinghand.constants.Keys.LESSON_ID;

public class QuestionActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    private static final String LESSON_CONCLUDED = "Lição concluída";
    private static final String LESSON_ANSWERED = "Lição respondida";

    UnityPlayerFragment fragment;

    private TextView tvDescription;
    private TextView tvQuestionNumber;
    private FlexboxLayout flAnswerOptions;

    public LessonService lessonService;

    public long lessonId;
    public List<Question> questions;
    public Question currentQuestion;
    public int currentQuestionIndex;

    private QuestionResponse currentQuestionResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        initializeComponents();
        lessonId = getIntent().getExtras().getLong(LESSON_ID);

        Call<List<Question>> call = this.lessonService.getQuestionsByLesson(lessonId);

        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(@NotNull Call<List<Question>> call, @NotNull Response<List<Question>> response) {
                if (response.isSuccessful()) {
                    questions = response.body();

                    if (questions.size() > 0) {
                        currentQuestionIndex = 0;
                        loadCurrentQuestion();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {

            }
        });

    }

    void loadCurrentQuestion() {
        currentQuestion = questions.get(currentQuestionIndex);
//        removeCurrentFragment();

        if (fragment == null) {
            loadUnityFragment(currentQuestion.getSign());
        } else {
            fragment.sign = currentQuestion.getSign();
            fragment.translate(true);
        }

        setButtonsOnScreen(currentQuestion);
        setQuestionInfo(currentQuestionIndex, currentQuestion.getDescription());
    }

    public void goToNextQuestion() {
        currentQuestionIndex++;
        if (questions.size() > currentQuestionIndex) {
            loadCurrentQuestion();
        } else {
            currentQuestionResponse = null;
            openDialog("Lição concluída!", "Parabéns, você concluiu uma lição", LESSON_CONCLUDED);
            Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void openDialog(String title, String message, String tag) {
        MessageDialog exampleDialog = new MessageDialog(title, message);
        exampleDialog.show(getSupportFragmentManager(), tag);
    }

    public void loadUnityFragment(String sign) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("sign", sign);
        fragment = new UnityPlayerFragment();
        fragment.setArguments(bundle);

        transaction.add(R.id.fl_unity_fragment, fragment);
        transaction.commit();
    }

    public void setButtonsOnScreen(Question question) {
        String[] options = question.getAnswerOptions().split(";");
        flAnswerOptions.removeAllViews();

        for (String option : options) {

            Button button = new Button(this);
            button.setTextColor(ContextCompat.getColor(this, R.color.primary_blue));
            button.setPadding(8, 8, 8, 8);
            button.getBackground().setColorFilter(
                    ContextCompat.getColor(this, R.color.background_grey), PorterDuff.Mode.MULTIPLY
            );
            button.setText(option);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            button.setLayoutParams(params);

            button.setOnClickListener(view -> {
                String text = button.getText().toString();
                answerQuestion(text);
            });

            flAnswerOptions.addView(button);
        }
    }

    private void answerQuestion(String text) {
        String token = TokenService.getToken(QuestionActivity.this, getString(R.string.user_token_key));

        Call<QuestionResponse> call = lessonService.answerQuestion(
                token,
                lessonId,
                currentQuestion.getIdQuestion(),
                new AnswerQuestionRequest(text)
        );

        call.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                if (response.isSuccessful()) {
                    QuestionResponse result = response.body();
                    currentQuestionResponse = result;

                    String dialogTitle = result.isAnswerCorrect() ? "Resposta correta!" : "Resposta incorreta :(";
                    String dialogMessage = result.isAnswerCorrect() ?
                            "Você recebeu " + result.getPointsGained() + " pontos" : "Tente novamente";

                    if (result.isLeveledUp()) {
                        String levelUpMessage = "Você subiu para o nível " + result.getNewLevel().getDescription() + "!";
                        dialogMessage = dialogMessage + "\n" + levelUpMessage;
                    }

                    openDialog(dialogTitle, dialogMessage, LESSON_ANSWERED);
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {

            }
        });
    }

    public void setQuestionInfo(int questionNumber, String description) {
        tvDescription.setText(description);
        String question = "Questão " + questionNumber + 1;
        tvQuestionNumber.setText(question);
    }

    private void initializeComponents() {
        this.tvQuestionNumber = findViewById(R.id.tv_question_number);
        this.tvDescription = findViewById(R.id.tv_question_description);
        this.flAnswerOptions = findViewById(R.id.fl_answer_options);

        RetrofitConfig config = new RetrofitConfig();
        this.lessonService = config.getLessonService();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        if (currentQuestionResponse.isAnswerCorrect()) {
            goToNextQuestion();
        }
    }
}