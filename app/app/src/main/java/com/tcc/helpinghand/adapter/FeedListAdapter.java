package com.tcc.helpinghand.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tcc.helpinghand.R;
import com.tcc.helpinghand.models.Post;

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
        TextView tvLike = convertView.findViewById(R.id.tv_post_like);
        TextView tvText = convertView.findViewById(R.id.tv_post_text);
        tvUserName.setText(quote.getAuthor().getName());
//        tvLike.setText(quote.getLi.getAuthor().getName());
        tvText.setText(quote.getBody());

        return convertView;
    }
}
