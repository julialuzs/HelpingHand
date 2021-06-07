package com.tcc.helpinghand.adapters;

import android.content.Context;
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
import com.tcc.helpinghand.models.Comment;
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

public class CommentListAdapter extends ArrayAdapter<Comment> {
    public CommentListAdapter(Context context, List<Comment> commentList) {
        super(context, 0, commentList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment comment = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment, parent, false);
        }

        TextView tvUserName = convertView.findViewById(R.id.tv_comment_username);
        TextView tvText = convertView.findViewById(R.id.tv_comment_text);
        TextView tvDate = convertView.findViewById(R.id.tv_comment_date);

        tvUserName.setText(comment.getUser().getName());
        tvText.setText(comment.getContent());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/y h:m");
        LocalDateTime dateTime = LocalDateTime.parse(comment.getCreatedDate());
        String date = dateTime.format(formatter);
        tvDate.setText(date);

        return convertView;
    }

}
