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
    private ArrayList<String> moveList = new ArrayList<String>();

    public void loadGame(String filename) {
        try {
            FileInputStream fis = openFileInput(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            moveList = (ArrayList<String>) ois.readObject();
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

        initialize();
    }

    public void nextMove(View v) {
        //todo
        turnNumber.setText(getString(R.string.turn_number, ++turn));
        turnColor.setText((turn % 2 == 1) ? "White's Turn" : "Black's Turn");
    }

    public void initialize() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 0) {
                    if (j == 0 || j == 7) {
                        board[i][j] = new Rook('b');
                    }
                    else if (j == 1 || j == 6) {
                        board[i][j] = new Knight('b');
                    }
                    else if (j == 2 || j == 5) {
                        board[i][j] = new Bishop('b');
                    }
                    else if (j == 3) {
                        board[i][j] = new Queen('b');
                    }
                    else {
                        board[i][j] = new King('b');
                    }
                }
                else if (i == 1) {
                    board[i][j] = new Pawn('b');
                }
                else if (i == 6) {
                    board[i][j] = new Pawn('w');
                }
                else if (i == 7) {
                    if (j == 0 || j == 7) {
                        board[i][j] = new Rook('w');
                    }
                    else if (j == 1 || j == 6) {
                        board[i][j] = new Knight('w');
                    }
                    else if (j == 2 || j == 5) {
                        board[i][j] = new Bishop('w');
                    }
                    else if (j == 3) {
                        board[i][j] = new Queen('w');
                    }
                    else {
                        board[i][j] = new King('w');
                    }
                }
                else {
                    board[i][j] = null;
                }
            }
        }
    }
}
