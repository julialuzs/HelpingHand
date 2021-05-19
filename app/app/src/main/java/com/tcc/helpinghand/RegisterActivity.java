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
import com.tcc.helpinghand.services.RetrofitConfig;
import com.tcc.helpinghand.services.UserService;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements Validator.ValidationListener{

    public UserService userService;
    private CheckBox cbDeaf;
    private Button btSignIn;
    private Button btBack;
    private Validator validator;

    @NotEmpty(message = "O nome precisa ser preenchido")
    @Length(max = 150, message = "O tamanho máximo é de 150 caracteres")
    private EditText etName;

    @NotEmpty(message = "O email precisa ser preenchido")
    @Email(message = "E-mail inválido")
    private EditText etEmail;

    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC, message="A senha deve ter no mínimo 6 caracteres alfabéticos e numéricos")
    private EditText etPassword;

    @ConfirmPassword(message="As senhas devem ser iguais")
    private EditText etConfirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public void onValidationSucceeded() {
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