package com.tcc.helpinghand;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.tcc.helpinghand.dialogs.MessageDialog;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.services.RetrofitConfig;
import com.tcc.helpinghand.services.TokenService;
import com.tcc.helpinghand.services.UserService;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private static final String ACHIEVEMENT = "Selo conquistado";

    public UserService userService;
    public User user;

    private TextView tvLogout;
    private TextView tvPoints;
    private TextView tvLevel;
    private MaterialToolbar mtProfile;
    private ImageButton btnBeginner;
    private ImageButton btnResearcher;
    private ImageButton btnPupil;
    private ImageButton btnButterfly;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        this.initializeComponents();

        btnBeginner.setOnClickListener(this);
        btnResearcher.setOnClickListener(this);
        btnPupil.setOnClickListener(this);
        btnButterfly.setOnClickListener(this);

        String token = TokenService.getToken(
                getActivity()
        );

        Call<User> call = userService.getCurrentUser(token);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    mtProfile.setTitle(user.getName());
                    tvLevel.setText("Nível " + user.getLevel().getDescription());
                    tvPoints.setText(String.valueOf(user.getPoints()) + " pontos");
                    tvLogout.setOnClickListener(view -> logout());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void openDialog(String title, String message, String tag) {
        MessageDialog exampleDialog = new MessageDialog(title, message);
        exampleDialog.show(getChildFragmentManager(), tag);
    }

    @Override
    public void onClick(View view) {
        String dialogTitle ="";
        String dialogMessage="";

        switch (view.getId()) {
            case R.id.butterfly:
                dialogTitle = "Borboleta social!";
                dialogMessage = "Fez 5 postagens de interação.";
                break;
        }

        switch (view.getId()) {
            case R.id.researcher:
                dialogTitle = "Pesquisador entusiasta!";
                dialogMessage = "Verificou a tradução de 30 palavras diferentes.";
                break;
        }

        switch (view.getId()) {
            case R.id.beginner:
                dialogTitle = "Iniciante atento!";
                dialogMessage = "Acertou 5 questões seguidas.";
                break;
        }

        switch (view.getId()) {
            case R.id.pupil:
                dialogTitle = "Pupilo Incansável !";
                dialogMessage = "Ganhou 100 pontos em um dia.";
                break;
        }
        openDialog(dialogTitle, dialogMessage, ACHIEVEMENT);
    }

    public void logout() {
        TokenService.removeToken(getActivity().getApplicationContext());
        Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void initializeComponents() {
        View view = getView();
         tvLogout = view.findViewById(R.id.tv_profile_logout);
        tvPoints = view.findViewById(R.id.tv_profile_points);
        tvLevel = view.findViewById(R.id.tv_profile_level);
        mtProfile = view.findViewById(R.id.mt_profile);
        btnBeginner = view.findViewById(R.id.beginner);
        btnResearcher = view.findViewById(R.id.researcher);
        btnPupil = view.findViewById(R.id.pupil);
        btnButterfly = view.findViewById(R.id.butterfly);
        RetrofitConfig config = new RetrofitConfig();
        this.userService = config.getUserService();

    }
}