package com.tcc.helpinghand;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.tcc.helpinghand.models.requests.UserRequest;
import com.tcc.helpinghand.models.responses.LoginResponse;
import com.tcc.helpinghand.services.RetrofitConfig;
import com.tcc.helpinghand.services.TokenService;
import com.tcc.helpinghand.services.UserService;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tcc.helpinghand.constants.RequestMessages.AUTH_ERROR;
import static com.tcc.helpinghand.constants.RequestMessages.SERVER_ERROR;
import static com.tcc.helpinghand.constants.ValidationMessages.EMAIL_EMPTY;
import static com.tcc.helpinghand.constants.ValidationMessages.EMAIL_INVALID;
import static com.tcc.helpinghand.constants.ValidationMessages.PASSWORD_INVALID;

public class LoginActivity extends AppCompatActivity  implements Validator.ValidationListener {

    private Button btLogin;
    private TextView tvLinkRegister;
    private Validator validator;
    public UserService userService;

    @NotEmpty(message = EMAIL_EMPTY)
    @Email(message = EMAIL_INVALID)
    private EditText etEmail;

    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC, message=PASSWORD_INVALID)
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_login);

        validator = new Validator(this);
        validator.setValidationListener(this);
        this.initializeComponents();
        this.setOnClickListeners();
    }

    private void setOnClickListeners() {

        this.tvLinkRegister.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        this.btLogin.setOnClickListener(view -> {
            validator.validate();
        });

    }

    public UserRequest getUserRequest() {
        UserRequest user = new UserRequest();

        user.setEmail(this.etEmail.getText().toString());
        user.setPassword(this.etPassword.getText().toString());

        return user;
    }

    public void registerToken(String token) {
        TokenService.registerToken(
                getApplicationContext(),
                getString(R.string.user_token_key),
                token
        );
    }

    private void initializeComponents() {
        this.btLogin = findViewById(R.id.bt_login);
        this.etPassword = findViewById(R.id.et_password);
        this.etEmail = findViewById(R.id.et_email);
        this.tvLinkRegister = findViewById(R.id.tv_link_register);

        RetrofitConfig config = new RetrofitConfig();
        this.userService = config.getUserService();
    }

    @Override
    public void onValidationSucceeded() {
        UserRequest user = getUserRequest();
        Call<LoginResponse> call = userService.login(user);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {

                    registerToken(response.body().getAccessToken());

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), AUTH_ERROR + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), SERVER_ERROR, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for(ValidationError e:errors) {
            View view = e.getView();
            String message = e.getCollatedErrorMessage(this);

            if(view instanceof EditText){
                ((EditText) view).setError(message);
            }
        }

    }
}