package com.tcc.helpinghand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.models.requests.UserRequest;
import com.tcc.helpinghand.models.responses.LoginResponse;
import com.tcc.helpinghand.services.RetrofitConfig;
import com.tcc.helpinghand.services.UserService;

public class LoginActivity extends AppCompatActivity {

    private Button btLogin;
    private EditText etPassword;
    private EditText etEmail;

    private TextView tvLinkRegister;

    public UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_login);

        this.initializeComponents();
        this.setOnClickListeners();
    }

    private void setOnClickListeners() {

        this.tvLinkRegister.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        this.btLogin.setOnClickListener(view -> {
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
                        Toast.makeText(getApplicationContext(), "Um erro ocorreu. " + response.errorBody().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Um erro ocorreu. Tente novamente mais tarde", Toast.LENGTH_LONG).show();
                }
            });
        });

    }

    public UserRequest getUserRequest() {
        UserRequest user = new UserRequest();

        user.setEmail(this.etEmail.getText().toString());
        user.setPassword(this.etPassword.getText().toString());

        return user;
    }

    public void registerToken(String token) {

        SharedPreferences sharedPref = getApplicationContext().
                getSharedPreferences(
                        getString(R.string.user_token_key),
                        Context.MODE_PRIVATE
                );

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.user_token_key), token);

        editor.apply();
    }

    private void initializeComponents() {
        this.btLogin = findViewById(R.id.bt_login);
        this.etPassword = findViewById(R.id.et_password);
        this.etEmail = findViewById(R.id.et_email);
        this.tvLinkRegister = findViewById(R.id.tv_link_register);

        RetrofitConfig config = new RetrofitConfig();
        this.userService = config.getUserService();
    }
}