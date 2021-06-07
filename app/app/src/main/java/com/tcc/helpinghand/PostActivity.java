package com.tcc.helpinghand;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.tcc.helpinghand.models.Post;
import com.tcc.helpinghand.models.requests.PostRequest;
import com.tcc.helpinghand.services.PostService;
import com.tcc.helpinghand.services.RetrofitConfig;
import com.tcc.helpinghand.services.TokenService;

public class PostActivity extends AppCompatActivity {

    private TextInputLayout etPostBody;
    private TextInputLayout etPostTitle;

    private Button btSave;
    private Button btCancel;

    public PostService postService;
    public String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        this.initializeComponents();

        this.btSave.setOnClickListener(view -> {
            PostRequest post = getPost();
            Call<Post> call = postService.post(token, post);
            call.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    if (response.isSuccessful()) {
                        response.body();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        });

        this.btCancel.setOnClickListener(view -> {
            finish();
        });
    }

    public PostRequest getPost() {
        PostRequest post = new PostRequest();
        String body = etPostBody.getEditText().getText().toString();
        String title = etPostTitle.getEditText().getText().toString();
        post.setBody(body);
        post.setTitle(title);
//        post.setAttachment();
        return post;
    }

    private void initializeComponents() {
        this.etPostBody = findViewById(R.id.et_post_body);
        this.etPostTitle = findViewById(R.id.et_post_title);
        this.btCancel = findViewById(R.id.bt_post_back);
        this.btSave = findViewById(R.id.bt_post_save);

        RetrofitConfig retrofit = new RetrofitConfig();
        postService = retrofit.getPostService();
        token = TokenService.getToken(getApplicationContext());
    }
}