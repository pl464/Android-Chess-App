package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class PlaybackActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private Piece[][] board;
    private Button nextButton;

    private int turn = 1;
    private TextView turnNum;
    private TextView turnColor;

    private ArrayList<String> pastMoves = new ArrayList<String>();

    public void loadGame(String filename) {
        try {
            FileInputStream fis = openFileInput(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            pastMoves = (ArrayList<String>) ois.readObject();
            ois.close();
            fis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);

        Intent intent = getIntent();
        String selected = intent.getStringExtra("game");
        loadGame(selected);

        for (String s : pastMoves) {
            System.out.println(s);
        }
    }

    public void nextMove(View v) {
        //todo
    }
}
