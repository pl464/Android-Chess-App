package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends AppCompatActivity {

    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("me", "HELLO"); //go to logcat and search "me" in the Debug menu to find this message
        tableLayout = findViewById(R.id.board);
        drawBoard();
    }

    private void drawBoard(){

        int tileWidth = Math.round(getResources().getDimension(R.dimen.tile_width));
        int tileHeight = Math.round(getResources().getDimension(R.dimen.tile_height));
        int rowWidth = Math.round(getResources().getDimension(R.dimen.row_width));

        for (int i = 0; i < 8; i++){
            TableRow tableRow = new TableRow(this);
            //tableRow.setLayoutParams(new TableRow.LayoutParams(rowWidth, tileHeight));
            tableRow.setId(i);
            tableLayout.addView(tableRow);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < 8; j++){
                View tile = new View(this);
                tile.setLayoutParams(new TableRow.LayoutParams(tileWidth, tileHeight));
                if ((i + j) % 2 == 0) {
                    tile.setBackgroundColor(Color.parseColor("#000000"));
                } else tile.setBackgroundColor(Color.parseColor("#ffffff"));
                String id = Integer.toString(i) + Integer.toString(j);
                tile.setId(Integer.parseInt(id));
                tableRow.addView(tile);
                Log.d("me",id);
            }
        }
    }
}
