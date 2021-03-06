package com.tcc.helpinghand;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tcc.helpinghand.models.Lesson;
import com.unity3d.player.UnityPlayer;

public class UnityPlayerFragment extends Fragment {

    public String sign;
    public boolean disableSubs;
    protected UnityPlayer mUnityPlayer;
    FrameLayout frameLayoutForUnity;

    public static UnityPlayerFragment newInstance(
            String signToTranslate, boolean disableSubs
    ) {
        UnityPlayerFragment fragment = new UnityPlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("sign", signToTranslate);
        bundle.putBoolean("disableSubs", disableSubs);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mUnityPlayer = new UnityPlayer(getActivity());
        sign = getArguments().getString("sign");
        disableSubs = getArguments().getBoolean("disableSubs");
        View view = inflater.inflate(R.layout.fragment_unity_player, container, false);

        this.frameLayoutForUnity = view.findViewById(R.id.fl_unity_layout);
        this.frameLayoutForUnity.addView(
                mUnityPlayer.getView(),
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );

        mUnityPlayer.requestFocus();
        mUnityPlayer.windowFocusChanged(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (disableSubs) {
            this.disableSubs();
        }
    }

    @Override
    public void onDestroy() {
        mUnityPlayer.quit();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mUnityPlayer.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mUnityPlayer.resume();

        this.translate();
    }

    public void disableSubs() {
        UnityPlayer.UnitySendMessage("ScreenManager", "DisableSubtitles", "disable");
    }

    public void translate() {
        UnityPlayer.UnitySendMessage("PlayerManager", "translate", sign);
    }
}