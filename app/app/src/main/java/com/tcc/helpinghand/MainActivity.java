package com.tcc.helpinghand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tcc.helpinghand.models.Lesson;
import com.tcc.helpinghand.models.Question;
import com.tcc.helpinghand.services.LessonService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BottomNavigationView bottomNavigation;
    private ImageView imgLock1;
    private ImageView imgLock2;
    private ImageButton btnBasicM1;
    private ImageButton btnBasicM2;
    private ImageButton btnBasicM3;
    private ImageButton btnBasicM4;
    private ImageButton btnIntermediaryM1;
    private ImageButton btnIntermediaryM2;
    private ImageButton btnIntermediaryM3;
    private ImageButton btnIntermediaryM4;
    private ImageButton btnAdvancedM1;
    private ImageButton btnAdvancedM2;
    private ImageButton btnAdvancedM3;
    private ImageButton btnAdvancedM4;

    private Lesson lesson;
    private Question question;
    List<Lesson> lessons;
    public LessonService lessonService;
    ArrayAdapter<Lesson> adapterLesson;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initializeComponents();
        this.checkLessonsStatus();

        btnBasicM1.setOnClickListener(this);
        btnBasicM2.setOnClickListener(this);
        btnBasicM3.setOnClickListener(this);
        btnBasicM4.setOnClickListener(this);
        btnIntermediaryM1.setOnClickListener(this);
        btnIntermediaryM2.setOnClickListener(this);
        btnIntermediaryM3.setOnClickListener(this);
        btnIntermediaryM4.setOnClickListener(this);
        btnAdvancedM1.setOnClickListener(this);
        btnAdvancedM2.setOnClickListener(this);
        btnAdvancedM3.setOnClickListener(this);
        btnAdvancedM4.setOnClickListener(this);


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

    @Override
    public void onClick(View view) {

        Toast.makeText(MainActivity.this, "Teste do click", Toast.LENGTH_SHORT).show();

        switch (view.getId()) {
            case R.id.btnBasicM1:
                btnBasicM1.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.locked), android.graphics.PorterDuff.Mode.MULTIPLY);
                break;
        }

        switch ((view.getId())){
            case R.id.btnBasicM2:
                btnBasicM1.setColorFilter(null);
                break;
        }

        switch ((view.getId())){
            case R.id.btnBasicM3:
                imgLock1.setVisibility(View.INVISIBLE);
                break;
        }

        switch ((view.getId())){
            case R.id.btnBasicM4:
                imgLock1.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void checkLessonsStatus() {
        this.lessonService.getAll().enqueue(new Callback<List<Lesson>>() {

            @Override
            public void onResponse(Call<List<Lesson>> call, Response<List<Lesson>> response) {
                if(response.isSuccessful()){
                    lessons = response.body();
                }else{
///aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
                }
            }

            @Override
            public void onFailure(Call<List<Lesson>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ocorreu um problema ao carregar os dados do servidor. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeComponents() {
        this.bottomNavigation = (BottomNavigationView) findViewById(R.id.bnv_nav_bar);
        imgLock1 = findViewById(R.id.imgLock1);
        imgLock2 = findViewById(R.id.imgLock2);
        btnBasicM1 = findViewById(R.id.btnBasicM1);
        btnBasicM2 = findViewById(R.id.btnBasicM2);
        btnBasicM3 = findViewById(R.id.btnBasicM3);
        btnBasicM4 = findViewById(R.id.btnBasicM4);
        btnIntermediaryM1 = findViewById(R.id.btnIntermediaryM1);
        btnIntermediaryM2 = findViewById(R.id.btnIntermediaryM2);
        btnIntermediaryM3 = findViewById(R.id.btnIntermediaryM3);
        btnIntermediaryM4 = findViewById(R.id.btnIntermediaryM4);
        btnAdvancedM1 = findViewById(R.id.btnAdvancedM1);
        btnAdvancedM2 = findViewById(R.id.btnAdvancedM2);
        btnAdvancedM3 = findViewById(R.id.btnAdvancedM3);
        btnAdvancedM4 = findViewById(R.id.btnAdvancedM4);

    }


}