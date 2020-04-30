package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.Arrays;

public class GamesActivity extends AppCompatActivity {

    private ListView gameList;
    private ArrayAdapter adapter;
    private File[] files;
    private String[] names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        File dir = this.getFilesDir();
        files = dir.listFiles();
        names = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            names[i] = files[i].getName();
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, names);
        gameList = findViewById(R.id.gameList);
        gameList.setAdapter(adapter);
    }

    public void dateSort(View v) {
        Arrays.sort(files, (a, b) -> Long.compare(a.lastModified(), b.lastModified()));
        for (int i = 0; i < files.length; i++) {
            names[i] = files[i].getName();
        }
        adapter.notifyDataSetChanged();
    }

    public void titleSort(View v) {
        Arrays.sort(names);
        adapter.notifyDataSetChanged();
    }

    public void showPlayback(View v) {
        startActivity(new Intent(this, PlaybackActivity.class));
    }
}
