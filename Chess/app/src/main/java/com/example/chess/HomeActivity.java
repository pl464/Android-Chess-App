package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void showMain(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void showGames(View v) {
        startActivity(new Intent(this, GamesActivity.class));
    }
}
