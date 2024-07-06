package com.example.tictactoe;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private boolean playerXTurn = true;
    private int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2}; // 0: O, 1: X, 2: empty
    private int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
            {0, 4, 8}, {2, 4, 6}             // diagonals
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView gameStatus = findViewById(R.id.gameStatus);
        final GridLayout gridLayout = findViewById(R.id.gridLayout);
        final Button resetButton = findViewById(R.id.resetButton);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            final int index = i;
            Button button = (Button) gridLayout.getChildAt(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (gameState[index] == 2 && !isGameOver()) {
                        gameState[index] = playerXTurn ? 1 : 0;
                        String symbol = playerXTurn ? "X" : "O";
                        ((Button) v).setText(symbol);

                        // Set text color of the button based on player's turn
                        int textColor = playerXTurn ? Color.RED : Color.BLUE; // Example colors
                        ((Button) v).setTextColor(textColor);

                        playerXTurn = !playerXTurn;

                        // Update game status text and text color
                        String statusText = playerXTurn ? "Player X's Turn" : "Player O's Turn";
                        int statusColor = playerXTurn ? Color.RED : Color.BLUE; // Example colors
                        gameStatus.setText(statusText);
                        gameStatus.setTextColor(statusColor);

                        checkForWinner(gameStatus);
                    }
                }


            });
        }

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame(gridLayout, gameStatus);
            }
        });
    }

    private boolean isGameOver() {
        for (int pos : gameState) {
            if (pos == 2) return false;
        }
        return true;
    }

    private void checkForWinner(TextView gameStatus) {
        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2) {
                String winner = gameState[winningPosition[0]] == 1 ? "X" : "O";
                gameStatus.setText("Player " + winner + " Wins!");
                return;
            }
        }
        if (isGameOver()) {
            gameStatus.setText("It's a Draw!");
        }
    }

    private void resetGame(GridLayout gridLayout, TextView gameStatus) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ((Button) gridLayout.getChildAt(i)).setText("");
        }
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        playerXTurn = true;
        gameStatus.setText("Player X's Turn");
    }
}
