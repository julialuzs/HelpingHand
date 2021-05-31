package com.tcc.helpinghand;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.services.RetrofitConfig;
import com.tcc.helpinghand.services.TokenService;
import com.tcc.helpinghand.services.UserService;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    public UserService userService;
    public User user;

    private TextView tvLogout;
    private TextView tvPoints;
    private TextView tvLevel;
    private MaterialToolbar mtProfile;

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

        String token = TokenService.getToken(
                getActivity(), getString(R.string.user_token_key)
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

    public void logout() {
        // TODO: use constant instead of string resource
        TokenService.removeToken(getActivity().getApplicationContext(), getString(R.string.user_token_key));
        Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void initializeComponents() {
        View view = getView();
        tvLogout = view.findViewById(R.id.tv_profile_logout);
        tvPoints = view.findViewById(R.id.tv_profile_points);
        tvLevel = view.findViewById(R.id.tv_profile_level);
        mtProfile = view.findViewById(R.id.mt_profile);
        RetrofitConfig config = new RetrofitConfig();
        this.userService = config.getUserService();
    }
}