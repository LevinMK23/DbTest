package com.example.dbtest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dbtest.adapter.PostAdapter;
import com.example.dbtest.dialog.Post;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private void createPostTable(SQLiteDatabase db) {
        db.execSQL("create table if not exists post(id integer primary key autoincrement, post_title text, post_text text);");
    }

    private void addPost(SQLiteDatabase db, Post post) {
        String query = String.format("insert into post (post_title, post_text) values('%s', '%s');", post.getTitle(), post.getText());
        db.execSQL(query);
    }

    private List<Post> getPosts(SQLiteDatabase db) {
        List<Post> posts = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from post;", new String[]{});
        if(!cursor.moveToFirst()) {
            return posts;
        }
        do {
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("post_title"));
            @SuppressLint("Range") String text = cursor.getString(cursor.getColumnIndex("post_text"));
            Post post = new Post(title, text);
            posts.add(post);
        } while (cursor.moveToNext());
        return posts;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = getApplicationContext()
                .openOrCreateDatabase("app_db", MODE_PRIVATE,null);

        createPostTable(db);

        Button add = findViewById(R.id.button);

        List<Post> posts = getPosts(db);

        PostAdapter adapter = new PostAdapter(posts);


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        add.setOnClickListener(v -> {
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
            Button ok = dialogView.findViewById(R.id.buttonOk);
            Button cancel = dialogView.findViewById(R.id.buttonCancel);
            TextView title = dialogView.findViewById(R.id.dialogTitle);
            TextView text = dialogView.findViewById(R.id.dialogText);

            Dialog dialog = new AlertDialog.Builder(this)
                    .setView(dialogView)
                    .show();

            ok.setOnClickListener(dv -> {
                Post post = new Post(title.getText().toString(), text.getText().toString());
                posts.add(post);
                addPost(db, post);
                recyclerView.setAdapter(new PostAdapter(posts));
                dialog.dismiss();
            });

            cancel.setOnClickListener(dv -> dialog.dismiss());
        });

    }
}