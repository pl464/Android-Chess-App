package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.Arrays;

public class GamesActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private File[] files;
    private String[] names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        File dir = this.getFilesDir();
        files = dir.listFiles();
        names = (files != null) ? new String[files.length] : new String[0];
        for (int i = 0; i < names.length; i++) {
            names[i] = files[i].getName();
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        ListView gameList = findViewById(R.id.gameList);
        gameList.setAdapter(adapter);

        gameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(GamesActivity.this, PlaybackActivity.class);
                intent.putExtra("game", selected);
                startActivity(intent);
            }
        });
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
}
