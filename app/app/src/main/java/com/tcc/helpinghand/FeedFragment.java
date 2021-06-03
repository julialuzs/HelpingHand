package com.tcc.helpinghand;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.tcc.helpinghand.adapters.FeedListAdapter;
import com.tcc.helpinghand.models.Post;
import com.tcc.helpinghand.services.PostService;
import com.tcc.helpinghand.services.RetrofitConfig;

import java.util.List;

public class FeedFragment extends Fragment {

    CircularProgressIndicator progressCircle;
    private PostService postService;
    private List<Post> posts;
    private ListView listView;

    private FloatingActionButton fabAddPost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.initializeComponents();
        this.loadPosts();
        this.setOnFABClickListener();
    }

    void setPostsInList(List<Post> posts) {
        FeedListAdapter adapter = new FeedListAdapter(getActivity(), posts);
        this.listView.setAdapter(adapter);
    }

    private void loadPosts() {
        progressCircle.show();

        this.postService.getPosts(null).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    posts = response.body();
                    setPostsInList(posts);
                    progressCircle.hide();
                } else {
                    Toast.makeText(getActivity(), ":(((", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(),
                        "Ocorreu um problema ao carregar os dados do servidor. Tente novamente mais tarde.",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    void setOnFABClickListener() {
        this.fabAddPost.setOnClickListener((View view) -> {
            // go to post activity
            Intent intent = new Intent(getActivity(), PostActivity.class);
            startActivity(intent);
        });
    }

    void initializeComponents() {
        RetrofitConfig config = new RetrofitConfig();
        this.postService = config.getPostService();
        View view = getView();

        this.listView = view.findViewById(R.id.rv_feed_posts);
        this.fabAddPost = view.findViewById(R.id.fab_add_post);
        progressCircle = view.findViewById(R.id.cpi_loading);
    }
}