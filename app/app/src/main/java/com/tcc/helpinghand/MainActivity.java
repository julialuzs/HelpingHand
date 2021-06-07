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

import static com.tcc.helpinghand.constants.Keys.FRAGMENT_DICTIONARY;
import static com.tcc.helpinghand.constants.Keys.FRAGMENT_HOME;
import static com.tcc.helpinghand.constants.Keys.FRAGMENT_TO_REDIRECT;
import static com.tcc.helpinghand.constants.Keys.SIGN_TO_TRANSLATE;


public class MainActivity extends AppCompatActivity  {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

    }

    public void setFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment home = getFragment(bottomNavigation.getSelectedItemId());
        transaction.replace(R.id.fl_fragment_container, home);

        Fragment pointsBar = PointsFragment.newInstance();
        transaction.add(R.id.points_bar, pointsBar);

        transaction.commit();
    }

    private void setNavBarClickListener() {
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment = new HomeFragment();
            fragment = getFragment(item.getItemId());

            transaction.replace(R.id.fl_fragment_container, fragment);
//            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.initializeComponents();
        this.setFragments();
        this.setNavBarClickListener();
    }

    Fragment getFragment(int id) {
        switch (id) {
            case R.id.nav_item_home:
                return new HomeFragment();
            case R.id.nav_item_dictionary:
                return new DictionaryFragment();
            case R.id.nav_item_feed:
                return new FeedFragment();
            case R.id.nav_item_person:
                return new ProfileFragment();
        }
        return new HomeFragment();
    }

    private void initializeComponents() {
        this.bottomNavigation = (BottomNavigationView) findViewById(R.id.bnv_nav_bar);
    }

}