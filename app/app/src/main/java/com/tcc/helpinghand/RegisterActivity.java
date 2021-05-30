package com.tcc.helpinghand;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.models.responses.LoginResponse;
import com.tcc.helpinghand.services.RetrofitConfig;
import com.tcc.helpinghand.services.TokenService;
import com.tcc.helpinghand.services.UserService;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import androidx.appcompat.app.AppCompatDelegate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tcc.helpinghand.constants.RequestMessages.SERVER_ERROR;
import static com.tcc.helpinghand.constants.RequestMessages.USER_CREATED;
import static com.tcc.helpinghand.constants.ValidationMessages.FIELD_EMPTY;
import static com.tcc.helpinghand.constants.ValidationMessages.FIELD_INVALID;
import static com.tcc.helpinghand.constants.ValidationMessages.INVALID_PASSWORD_CONFIRMATION;
import static com.tcc.helpinghand.constants.ValidationMessages.PASSWORD_INVALID;
import static com.tcc.helpinghand.constants.ValidationMessages.SIZE_LIMIT_EXCEEDED;

public class RegisterActivity extends AppCompatActivity implements Validator.ValidationListener{

    public UserService userService;
    private CheckBox cbDeaf;
    private Button btSignIn;
    private Button btBack;
    private Validator validator;

    @NotEmpty(message = FIELD_EMPTY)
    @Length(max = 150, message = SIZE_LIMIT_EXCEEDED)
    private EditText etName;

    @NotEmpty(message = FIELD_EMPTY)
    @Email(message = FIELD_INVALID)
    private EditText etEmail;

    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC, message=PASSWORD_INVALID)
    private EditText etPassword;

    @ConfirmPassword(message=INVALID_PASSWORD_CONFIRMATION)
    private EditText etConfirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_register);

        validator = new Validator(this);
        validator.setValidationListener(this);
        this.initializeComponents();

        this.btSignIn.setOnClickListener(view -> {
            validator.validate();
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

    public void registerToken(String token) {
        TokenService.registerToken(
                getApplicationContext(),
                getString(R.string.user_token_key),
                token
        );
    }

    @Override
    public void onValidationSucceeded() {
        User user = getUser();
        Call<LoginResponse> call = userService.signin(user);


        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful()) {
                    showToast(USER_CREATED);
                    registerToken(response.body().getAccessToken());
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));

                } else {
                    showToast("Um erro ocorreu. " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                showToast(SERVER_ERROR);
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