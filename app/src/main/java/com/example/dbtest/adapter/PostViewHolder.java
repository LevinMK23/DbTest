package com.example.dbtest.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbtest.R;
import com.example.dbtest.dialog.Post;

public class PostViewHolder extends RecyclerView.ViewHolder {

    private final TextView title;
    private final TextView text;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.rvTitle);
        text = itemView.findViewById(R.id.rvText);
    }

    public void setPost(Post post) {
        this.title.setText(post.getTitle());
        this.text.setText(post.getText());
    }


}
