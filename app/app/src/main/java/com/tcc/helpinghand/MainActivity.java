package com.tcc.helpinghand;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.tcc.helpinghand.enums.Difficulty;
import com.tcc.helpinghand.models.Lesson;
import com.tcc.helpinghand.services.LessonService;
import com.tcc.helpinghand.services.RetrofitConfig;
import com.tcc.helpinghand.services.TokenService;

import java.util.List;
import java.util.stream.Collectors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity  {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        this.initializeComponents();
        this.setFragments();
        this.setNavBarClickListener();
    }

    public void setFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment home = new HomeFragment();
        transaction.replace(R.id.fl_fragment_container, home);

        Fragment pointsBar = PointsFragment.newInstance();
        transaction.add(R.id.points_bar, pointsBar);

        transaction.commit();
    }

    private void setNavBarClickListener() {
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment = new HomeFragment();

            switch (item.getItemId()) {
                case R.id.nav_item_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.nav_item_dictionary:
                    fragment = new DictionaryFragment();
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