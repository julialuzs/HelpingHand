package com.tcc.helpinghand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.models.requests.UserRequest;
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
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_login);

        this.initializeComponents();
        this.setOnClickListeners();
    }

    private void setOnClickListeners() {

        this.tvLinkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        this.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserRequest user = getUserRequest();
                Call<User> call = userService.login(user);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Usuário criado com sucesso!", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Um erro ocorreu. " + response.errorBody().toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Um erro ocorreu. Tente novamente mais tarde", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    public UserRequest getUserRequest() {
        UserRequest user = new UserRequest();

        user.setEmail(this.etEmail.getText().toString());
        user.setPassword(this.etPassword.getText().toString());

        return user;
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