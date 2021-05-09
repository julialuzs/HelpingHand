package com.tcc.helpinghand;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.services.RetrofitConfig;
import com.tcc.helpinghand.services.UserService;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    public UserService userService;
    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private CheckBox cbDeaf;
    private Button btSignIn;
    private Button btBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.initializeComponents();

        this.btSignIn.setOnClickListener(view -> {
            User user = getUser();
            Call<User> call = userService.signin(user);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    if (response.isSuccessful()) {
                        showToast("Usuário criado com sucesso!");
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));

                    } else {
                        showToast("Um erro ocorreu. " + response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    showToast("Um erro ocorreu. Tente novamente mais tarde");
                }
            });

        });

        this.btBack.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
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

    public void showToast(String message) {
        Toast.makeText(
                getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }
}