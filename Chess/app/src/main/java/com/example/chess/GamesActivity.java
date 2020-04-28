package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GamesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        Intent intent = getIntent();
    }

    public void dateSort(View v) {
        //todo
    }

    public void titleSort(View v) {
        //todo
    }

    public void showPlayback(View v) {
        startActivity(new Intent(this, PlaybackActivity.class));
    }
}
