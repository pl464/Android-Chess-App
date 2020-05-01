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
    private TextView turnNumber;
    private TextView turnColor;
    private Button nextButton;

    private int turn = 1;
    private Piece[][] board = new Piece[8][8];
    private ArrayList<String[][]> moveList = new ArrayList<String[][]>();

    public void loadGame(String filename) {
        try {
            FileInputStream fis = openFileInput(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            moveList = (ArrayList<String[][]>) ois.readObject();
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

        tableLayout = findViewById(R.id.board);
        turnNumber = findViewById(R.id.turnNumber);
        turnNumber.setText(getString(R.string.turn_number, turn));
        turnColor = findViewById(R.id.turnColor);

        Intent intent = getIntent();
        String selected = intent.getStringExtra("game");
        loadGame(selected);

        drawBoard();
    }

    public void nextMove(View v) {
        turnNumber.setText(getString(R.string.turn_number, ++turn));
        turnColor.setText((turn % 2 == 1) ? "White's Turn" : "Black's Turn");
        drawBoard();
    }

    public void drawBoard() {
        //todo
    }
}
