package com.tcc.helpinghand;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.unity3d.player.UnityPlayer;

public class UnityPlayerFragment extends Fragment {

    protected UnityPlayer mUnityPlayer;
    FrameLayout frameLayoutForUnity;

    public String sign;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mUnityPlayer = new UnityPlayer(getActivity());
        sign = getArguments().getString("sign");
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

        this.translate(true);
    }

    public void translate(boolean disableSubs) {
        UnityPlayer.UnitySendMessage("PlayerManager", "translate", sign);

        if (disableSubs) {
            UnityPlayer.UnitySendMessage("ScreenManager", "DisableSubtitles", "disable");
        }
    }
}