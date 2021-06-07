package com.tcc.helpinghand;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.tcc.helpinghand.adapters.CommentListAdapter;
import com.tcc.helpinghand.models.Comment;
import com.tcc.helpinghand.models.Post;
import com.tcc.helpinghand.services.PostService;
import com.tcc.helpinghand.services.RetrofitConfig;
import com.tcc.helpinghand.services.TokenService;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tcc.helpinghand.constants.Keys.POST;

public class CommentsActivity extends AppCompatActivity {

    private PostService postService;

    private Button btAddComment;
    private TextInputLayout etComment;
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
        loadComments();
        this.setAddCommentClickListener();
    }

    private void setAddCommentClickListener() {
        this.btAddComment.setOnClickListener(view -> {
            String commentContent = this.etComment.getEditText().getText().toString();
            String token = TokenService.getToken(getApplicationContext());

            Comment comment = new Comment();
            comment.setContent(commentContent);

            Call<Comment> call = this.postService.comment(token, comment, post.getIdPost());

            call.enqueue(new Callback<Comment>() {
                @Override
                public void onResponse(Call<Comment> call, Response<Comment> response) {
                    if (response.isSuccessful()) {
                        loadComments();
                        etComment.getEditText().setText(null);
                    }
                }

                @Override
                public void onFailure(Call<Comment> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        });
    }

    void setCommentsInList(List<Comment> comments) {
        CommentListAdapter adapter = new CommentListAdapter(CommentsActivity.this, comments);
        this.listView.setAdapter(adapter);
    }

    void loadComments() {
        progressCircle.show();

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
        this.btAddComment = findViewById(R.id.bt_add_comment);
        this.etComment = findViewById(R.id.til_comment);
        this.progressCircle = findViewById(R.id.cpi_loading);
    }
}