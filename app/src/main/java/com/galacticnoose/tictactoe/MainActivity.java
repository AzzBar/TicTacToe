package com.galacticnoose.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.view.View.OnClickListener;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView player_textView;

    boolean xTurn = true;
    int round = 0;

    Button[][] buttons = new Button[3][3];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player_textView = findViewById(R.id.player_textView);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                buttons[i][j] = findViewById(getResources().getIdentifier(buttonID, "id", getPackageName()));
                buttons[i][j].setOnClickListener(this);
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (xTurn) {
            ((Button) v).setText("X");
            player_textView.setText(R.string.Player_O_Text);
        } else {
            ((Button) v).setText("O");
            player_textView.setText(R.string.player_textView);
        }

        round++;

        if (round > 4 && checkForWin()) {
            if (xTurn) {
                xWins();
            } else {
                oWins();
            }
        } else if (round == 9) {
            draw();
        } else {
            xTurn = !xTurn;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }

    private void xWins() {
        player_textView.setText(R.string.Player_X_win);
    }

    private void oWins() {
        player_textView.setText(R.string.Player_O_win);
    }

    private void draw() {
        player_textView.setText(R.string.draw_text);
    }

    public void newGame(View view) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        player_textView.setText(R.string.player_textView);
        round = 0;
        xTurn = true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("round", round);
        outState.putBoolean("xTurn", xTurn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        round = savedInstanceState.getInt("round");
        xTurn = savedInstanceState.getBoolean("xTurn");
    }
}
