package com.tcc.helpinghand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initializeComponents();

        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment = new HomeFragment();

            switch (item.getItemId()) {
                case R.id.nav_item_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.nav_item_dictionary:
                    fragment = new DictionaryFragment();
//                    fragment = new UnityPlayerFragment();
                    break;
                case R.id.nav_item_feed:
                    fragment = new FeedFragment();
                    break;
                case R.id.nav_item_person:
                    fragment = new ProfileFragment();
                    break;
            }

            transaction.replace(R.id.fl_fragment_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        });
    }

    private void initializeComponents() {
        this.bottomNavigation = (BottomNavigationView) findViewById(R.id.bnv_nav_bar);
    }
}