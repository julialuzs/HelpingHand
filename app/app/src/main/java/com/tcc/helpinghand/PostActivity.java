package com.tcc.helpinghand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PostActivity extends AppCompatActivity {

    private EditText etPostBody;

    private Button btSave;
    private Button btCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        this.initializeComponents();

        this.btCancel.setOnClickListener(view -> {
            Intent intent = new Intent();
            startActivity(intent);
        });
    }

    private void initializeComponents() {
        this.etPostBody = findViewById(R.id.et_post_body);
        this.btCancel = findViewById(R.id.bt_post_back);
        this.btSave = findViewById(R.id.bt_post_save);
    }
}