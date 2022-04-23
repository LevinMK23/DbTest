package com.example.dbtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbtest.R;
import com.example.dbtest.dialog.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private final List<Post> posts;


    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        return new PostViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.setPost(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void addPost(Post post) {
        posts.add(post);
    }
}
