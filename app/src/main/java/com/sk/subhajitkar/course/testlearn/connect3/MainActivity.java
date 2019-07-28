package com.sk.subhajitkar.course.testlearn.connect3;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private final int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {2, 4, 6}, {0, 4, 8}};

    int activePlayer = 0;
    LinearLayout player1,player2;
    TextView player1Text, player2Text;
    Button retry;
    GridLayout grid;
    boolean gameActive=true;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};  //0: Yellow, 1: Red, 2: Blank

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        setContentView(R.layout.activity_main);

        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        player1Text = findViewById(R.id.tv_player1);
        player2Text = findViewById(R.id.tv_player2);
        grid = findViewById(R.id.grid);

        retry = findViewById(R.id.retry_button);
        retry.setVisibility(View.INVISIBLE);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry.setVisibility(View.INVISIBLE);
                for(int i=0;i<grid.getChildCount();i++){
                    ImageView counter = (ImageView) grid.getChildAt(i);
                    counter.setImageDrawable(null);
                }
                gameActive=true;
                activePlayer = 0;
                for(int i=0;i<gameState.length;i++){
                    gameState[i]=2;
                }
            }
        });

        player1.setVisibility(View.VISIBLE);
        player2.setVisibility(View.INVISIBLE);
        player1Text.setText("Player 1's turn");
    }

    public void onClick(View view) {
        ImageView counter = (ImageView) view;

        int currentCounter = Integer.valueOf(counter.getTag().toString());

        if (gameState[currentCounter] == 2 && gameActive) {
            counter.setTranslationY(-2000);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
                gameState[currentCounter] = 0;
                player2.setVisibility(View.VISIBLE);
                player1.setVisibility(View.INVISIBLE);
                player2Text.setText("Player 2's turn");
            } else if (activePlayer == 1) {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
                gameState[currentCounter] = 1;
                player1.setVisibility(View.VISIBLE);
                player2.setVisibility(View.INVISIBLE);
                player1Text.setText("Player 1's turn");
            }
            counter.animate().translationYBy(2000).setDuration(200);
            //winning checking logic
            for (int[] temp : winningPositions) {
                String playerWon = "";
                if (gameState[temp[0]] == gameState[temp[1]] && gameState[temp[1]] == gameState[temp[2]] && gameState[temp[0]] != 2) {
                    //someone has won
                    gameActive=false;
                    player1.setVisibility(View.VISIBLE);
                    player2.setVisibility(View.VISIBLE);
                    if (gameState[temp[0]] == 0) {
                        playerWon = "Player 1";
                        player1Text.setText("You Win!");
                        player2Text.setText("You Lose!");
                    } else {
                        playerWon = "Player 2";
                        player1Text.setText("You Lose!");
                        player2Text.setText("You Win!");
                    }
                    //player.setText(playerWon + " has won!");
                    //Toast.makeText(getApplicationContext(), playerWon + " has won!", Toast.LENGTH_SHORT).show();
                    retry.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
