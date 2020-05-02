package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class PlaybackActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private TextView turnNumber;
    private TextView turnColor;
    private TextView gameResult;

    private int turn = 1;
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

        gameResult = findViewById(R.id.gameResult);
        gameResult.setVisibility(View.INVISIBLE);

        tableLayout = findViewById(R.id.board);
        turnNumber = findViewById(R.id.turnNumber);
        turnNumber.setText(getString(R.string.turn_number, turn));
        turnColor = findViewById(R.id.turnColor);

        Intent intent = getIntent();
        String selected = intent.getStringExtra("game");
        loadGame(selected);

        drawBoard();
        moveList.remove(0);
    }

    public void nextMove(View v) {
        switch (moveList.get(0)[0][0]) {
            case "white":
                gameResult.setText(R.string.white_wins);
                gameResult.setVisibility(View.VISIBLE);
                return;
            case "black":
                gameResult.setText(R.string.black_wins);
                gameResult.setVisibility(View.VISIBLE);
                return;
            case "draw":
                gameResult.setText(R.string.draw);
                gameResult.setVisibility(View.VISIBLE);
                return;
            default:
                break;
        }
        turnNumber.setText(getString(R.string.turn_number, ++turn));
        turnColor.setText((turn % 2 == 1) ? "White's Turn" : "Black's Turn");
        drawBoard();
        moveList.remove(0);
    }

    public void drawBoard() {
        int tileWidth = Math.round(getResources().getDimension(R.dimen.tile_width));
        int tileHeight = Math.round(getResources().getDimension(R.dimen.tile_height));
        for (int i = 0; i < 8; i++) {
            tableLayout.removeView(findViewById(i));
            TableRow tableRow = new TableRow(this);
            tableRow.setId(i);
            tableLayout.addView(tableRow);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < 8; j++) {
                Piece p = convert(moveList.get(0)[i][j]);
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new TableRow.LayoutParams(tileWidth, tileHeight));
                if (p != null && p.color == 'b'){
                    if (p.type == 'B') imageView.setImageResource(R.drawable.ic_black_bishop);
                    else if (p.type == 'K') imageView.setImageResource(R.drawable.ic_black_king);
                    else if (p.type == 'N') imageView.setImageResource(R.drawable.ic_black_knight);
                    else if (p.type == 'p') imageView.setImageResource(R.drawable.ic_black_pawn);
                    else if (p.type == 'Q') imageView.setImageResource(R.drawable.ic_black_queen);
                    else if (p.type == 'R') imageView.setImageResource(R.drawable.ic_black_rook);
                }
                else if (p != null && p.color == 'w'){
                    if (p.type == 'B') imageView.setImageResource(R.drawable.ic_white_bishop);
                    else if (p.type == 'K') imageView.setImageResource(R.drawable.ic_white_king);
                    else if (p.type == 'N') imageView.setImageResource(R.drawable.ic_white_knight);
                    else if (p.type == 'p') imageView.setImageResource(R.drawable.ic_white_pawn);
                    else if (p.type == 'Q') imageView.setImageResource(R.drawable.ic_white_queen);
                    else if (p.type == 'R') imageView.setImageResource(R.drawable.ic_white_rook);
                }
                tableRow.addView(imageView);
            }
        }
    }

    public Piece convert(String s) {
        switch (s) {
            case "bB":
                return new Bishop('b');
            case "bK":
                return new King('b');
            case "bN":
                return new Knight('b');
            case "bp":
                return new Pawn('b');
            case "bQ":
                return new Queen('b');
            case "bR":
                return new Rook('b');
            case "wB":
                return new Bishop('w');
            case "wK":
                return new King('w');
            case "wN":
                return new Knight('w');
            case "wp":
                return new Pawn('w');
            case "wQ":
                return new Queen('w');
            case "wR":
                return new Rook('w');
            default:
                return null;
        }
    }
}
