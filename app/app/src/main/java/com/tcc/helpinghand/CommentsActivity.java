package com.tcc.helpinghand;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.tcc.helpinghand.adapters.CommentListAdapter;
import com.tcc.helpinghand.models.Comment;
import com.tcc.helpinghand.models.Post;
import com.tcc.helpinghand.services.PostService;
import com.tcc.helpinghand.services.RetrofitConfig;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tcc.helpinghand.constants.Keys.POST;

public class CommentsActivity extends AppCompatActivity {

    private PostService postService;
    private ListView listView;
    private CircularProgressIndicator progressCircle;

    private List<Comment> comments;
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        post = (Post) getIntent().getExtras().getSerializable(POST);

        this.initializeComponents();
        this.loadComments();
    }

    void setCommentsInList(List<Comment> comments) {
        CommentListAdapter adapter = new CommentListAdapter(CommentsActivity.this, comments);
        this.listView.setAdapter(adapter);
    }

    private void loadComments() {
        progressCircle.show();
//        String token = TokenService.getToken(getApplicationContext());

        this.postService.getComments(post.getIdPost()).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    comments = response.body();
                    setCommentsInList(comments);
                    progressCircle.hide();
                } else {
                    Toast.makeText(CommentsActivity.this, ":(((", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(CommentsActivity.this,
                        "Ocorreu um problema ao carregar os dados do servidor. Tente novamente mais tarde.",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    void initializeComponents() {
        RetrofitConfig config = new RetrofitConfig();
        this.postService = config.getPostService();

        this.listView = findViewById(R.id.lv_comments);
        progressCircle = findViewById(R.id.cpi_loading);
    }
}