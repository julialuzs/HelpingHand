package com.tcc.helpinghand.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.tcc.helpinghand.CommentsActivity;
import com.tcc.helpinghand.R;
import com.tcc.helpinghand.models.Post;
import com.tcc.helpinghand.services.PostService;
import com.tcc.helpinghand.services.RetrofitConfig;
import com.tcc.helpinghand.services.TokenService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tcc.helpinghand.constants.Keys.ACCESS_TOKEN;
import static com.tcc.helpinghand.constants.Keys.POST;

public class FeedListAdapter extends ArrayAdapter<Post> {


    public PostService service;

    public FeedListAdapter(Context context, List<Post> postList) {
        super(context, 0, postList);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Post post = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.post, parent, false);
        }

        RetrofitConfig retrofit = new RetrofitConfig();
        service = retrofit.getPostService();

        TextView tvUserName = convertView.findViewById(R.id.tv_post_username);
        TextView tvTitle = convertView.findViewById(R.id.tv_post_title);
        TextView tvLike = convertView.findViewById(R.id.tv_post_like);
        TextView tvComments = convertView.findViewById(R.id.tv_post_comment);
        TextView tvText = convertView.findViewById(R.id.tv_post_text);
        TextView tvDate = convertView.findViewById(R.id.tv_post_date);

        tvUserName.setText(post.getAuthor().getName());
        tvTitle.setText(post.getTitle());
        tvText.setText(post.getBody());

        if (post.isLikedByMe()) {
            setLikeButton(tvLike);
        } else {
            setDislikeButton(tvLike);
        }

        setLikeButtonClickListener(tvLike, post);
        setCommentsButtonClickListener(tvComments, post);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/y h:m");
        LocalDateTime dateTime = LocalDateTime.parse(post.getCreatedDate());
        String date = dateTime.format(formatter);
        tvDate.setText(date);

        return convertView;
    }

    private void setCommentsButtonClickListener(TextView tvComments, Post post) {
        tvComments.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), CommentsActivity.class);
            intent.putExtra(POST, post);
            getContext().startActivity(intent);
        });
    }

    private void setLikeButtonClickListener(TextView tvLike, Post post) {
        tvLike.setOnClickListener(view -> {
            if (post.isLikedByMe()) {
                post.setLikedByMe(false);
                setDislikeButton(tvLike);
            } else {
                post.setLikedByMe(true);
                setLikeButton(tvLike);
            }

            String token = TokenService.getToken(getContext());

            service.like(token, post.getIdPost()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext().getApplicationContext(), "Erro", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        });
    }

    private void setLikeButton(TextView tvLike) {
        tvLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite, 0, 0, 0);
        setTextViewDrawableColor(tvLike, R.color.accent_green);
        tvLike.setText("Descurtir");
    }

    private void setDislikeButton(TextView tvLike) {
        tvLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite_border, 0, 0, 0);
        setTextViewDrawableColor(tvLike, R.color.design_default_color_on_secondary);
        tvLike.setText("Curtir");
    }

    private void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(getContext().getColor(color), PorterDuff.Mode.SRC_IN));
            }
        }
    }


}
