package com.tcc.helpinghand;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tcc.helpinghand.R;
import com.tcc.helpinghand.models.User;
import com.tcc.helpinghand.services.RetrofitConfig;
import com.tcc.helpinghand.services.TokenService;
import com.tcc.helpinghand.services.UserService;

public class PointsFragment extends Fragment {

    private TextView tvLevel;
    private TextView tvPoints;
    public UserService userService;

    public PointsFragment() {

    }

    public static PointsFragment newInstance() {
        PointsFragment fragment = new PointsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_points, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void init() {
        View view = getView();
        tvLevel = view.findViewById(R.id.tv_level);
        tvPoints = view.findViewById(R.id.tv_points);
    }

    private void getData() {
        RetrofitConfig retrofitConfig = new RetrofitConfig();
        UserService userService = retrofitConfig.getUserService();
        String token = TokenService.getToken(
                getActivity(), getString(R.string.user_token_key)
        );

        Call<User> call = userService.getCurrentUser(token);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    tvLevel.setText(String.valueOf(user.getLevel().getDescription()));
                    tvPoints.setText(String.valueOf(user.getPoints()));

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "Erro ao carregar dados." , Toast.LENGTH_LONG).show();
            }
        });


    }
}