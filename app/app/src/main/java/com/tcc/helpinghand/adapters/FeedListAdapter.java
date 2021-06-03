package com.tcc.helpinghand.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tcc.helpinghand.R;
import com.tcc.helpinghand.models.Post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FeedListAdapter extends ArrayAdapter<Post> {

    public FeedListAdapter(Context context, List<Post> postList) {
        super(context, 0, postList);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Post quote = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.post, parent, false);
        }

        TextView tvUserName = convertView.findViewById(R.id.tv_post_username);
        TextView tvTitle = convertView.findViewById(R.id.tv_post_title);
        TextView tvLike = convertView.findViewById(R.id.tv_post_like);
        TextView tvText = convertView.findViewById(R.id.tv_post_text);
        TextView tvDate = convertView.findViewById(R.id.tv_post_date);
        tvUserName.setText(quote.getAuthor().getName());
        tvTitle.setText(quote.getTitle());
        tvText.setText(quote.getBody());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/y h:m");
        LocalDateTime dateTime = LocalDateTime.parse(quote.getCreatedDate());
        String date = dateTime.format(formatter);
        tvDate.setText(date);

        return convertView;
    }
}
