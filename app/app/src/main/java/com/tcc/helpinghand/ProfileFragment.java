package com.tcc.helpinghand;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.models.responses.LoginResponse;
import com.tcc.helpinghand.services.RetrofitConfig;
import com.tcc.helpinghand.services.UserService;

public class ProfileFragment extends Fragment {

    public UserService userService;

    private TextView tvUserName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.initializeComponents();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.user_token_key), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(getString(R.string.user_token_key), null);

        Call<User> call = userService.getCurrentUser(token);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();

                    Toast.makeText(getContext(),user.getEmail() + ',' + user.getName(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private void initializeComponents() {
        RetrofitConfig config = new RetrofitConfig();
        this.userService = config.getUserService();
    }
}