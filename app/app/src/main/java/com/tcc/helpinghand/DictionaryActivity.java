package com.tcc.helpinghand;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import static com.tcc.helpinghand.constants.Keys.SIGN_TO_TRANSLATE;

public class DictionaryActivity extends AppCompatActivity {

    private UnityPlayerFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        String signToTranslate = getIntent().getExtras().getString(SIGN_TO_TRANSLATE);

        FragmentTransaction transaction = this
                .getSupportFragmentManager()
                .beginTransaction();

        fragment = UnityPlayerFragment.newInstance(
                signToTranslate, false
        );

        transaction.replace(R.id.fl_unity_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}