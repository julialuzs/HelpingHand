package com.tcc.helpinghand;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.services.RetrofitConfig;
import com.tcc.helpinghand.services.UserService;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private CheckBox cbDeaf;

    private Button btSignIn;
    private Button btBack;

    public UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.initializeComponents();

        this.btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = getUser();
                Call<User> call = userService.signin(user);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Usuário criado com sucesso!", Toast.LENGTH_LONG).show();

                            startActivity(
                                    new Intent(RegisterActivity.this, MainActivity.class)
                            );

                        } else {
                            Toast.makeText(getApplicationContext(), "Não foi possível pegar os dados. " + response.errorBody().toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Não foi possível pegar os dados. Tente novamente mais tarde", Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

        this.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public User getUser() {
        User user = new User();

        user.setName(this.etName.getText().toString());
        user.setEmail(this.etEmail.getText().toString());
        user.setPassword(this.etPassword.getText().toString());
        user.setDeaf(this.cbDeaf.isChecked());

        return user;
    }

    private void initializeComponents() {
        this.etName = findViewById(R.id.et_name);
        this.etEmail = findViewById(R.id.et_email);
        this.etPassword = findViewById(R.id.et_password);
        this.etConfirmPassword = findViewById(R.id.et_confirm_password);
        this.cbDeaf = findViewById(R.id.cb_deaf);
        this.btSignIn = findViewById(R.id.bt_sign_in);
        this.btBack = findViewById(R.id.bt_back);

        RetrofitConfig config = new RetrofitConfig();
        this.userService = config.getUserService();
    }
}