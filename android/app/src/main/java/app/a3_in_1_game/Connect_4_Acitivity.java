package app.a3_in_1_game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Connect_4_Acitivity extends AppCompatActivity {
    private ImageView[][] board;
    private View boardView;
    private Connect_4 connect_4;
    private boolean inProgress = true;
    private int score = 0;

    private TextView scoreText;
    private String text;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_4__acitivity);
        connect_4 = new Connect_4();
        boardView = findViewById(R.id.game_board);
        setBoard();
        boardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_POINTER_UP:
                    case MotionEvent.ACTION_UP: {
                        int col = getCol(motionEvent.getX());
                        if (col != -1) {
                            drop(col, connect_4.CROSS);
                            if (inProgress) {
                                drop(-1, connect_4.CIRCLE);
                            }
                        }
                    }
                }
                return true;
            }
        });

        scoreText = (TextView) findViewById(R.id.connect_4_score);

        button = (Button) findViewById(R.id.connect_4_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inProgress) {
                    score--;
                    text = "Score: " + score;
                    scoreText.setText(text);
                    reset();
                } else {
                    reset();
                    text = "Forfeit";
                    button.setText(text);
                }
            }
        });
    }

    private void setBoard() {
        board = new ImageView[connect_4.NUM_ROWS][connect_4.NUM_COLS];
        for (int row = 0; row < connect_4.NUM_ROWS; row++) {
            ViewGroup rowV = (ViewGroup) ((ViewGroup) boardView).getChildAt(row);
            rowV.setClipChildren(false);
            for (int col = 0; col < connect_4.NUM_COLS; col++) {
                ImageView imageView = (ImageView) rowV.getChildAt(col);
                imageView.setImageResource(android.R.color.transparent);
                board[row][col] = imageView;
            }
        }
    }

    private void drop(int col, char currentPlayer) {
        if (!inProgress)
            return;
        if (col == -1) {
            connect_4.AImove(currentPlayer);
            col = connect_4.lastCol;
        } else if (!connect_4.playerMove(col, currentPlayer)) {
            return;
        }
        int row = connect_4.lastRow;
        final ImageView cell = board[row][col];
        float move = -(cell.getHeight() * row + cell.getHeight() + 15);
        cell.setY(move);
        cell.setImageResource(resourceForTurn(currentPlayer));
        TranslateAnimation anim = new TranslateAnimation(0, 0, 0, Math.abs(move));
        anim.setDuration(850);
        anim.setFillAfter(true);
        cell.startAnimation(anim);
        if (connect_4.checkWinner(currentPlayer)) {
            win(currentPlayer);
        }
    }

    private void win(char currentPlayer) {
        inProgress = false;
        text = "Restart";
        button.setText(text);

        String winner;
        if (currentPlayer == connect_4.CROSS) {
            winner = "RED";
            score++;
            text = "Score: " + score;
            scoreText.setText(text);
        } else {
            winner = "BLACK";
            score--;
            text = "Score: " + score;
            scoreText.setText(text);
        }
        Toast.makeText(getApplicationContext(), winner + " WON!", Toast.LENGTH_SHORT).show();
        //int color = board.turn == Board.Turn.FIRST ? getResources().getColor(R.color.primary_player) : getResources().getColor(R.color.secondary_player);
        //winnerText.setTextColor(color);
        //winnerText.setVisibility(View.VISIBLE);
    }

    private int getCol(float x) {
        float colWidth = board[0][0].getWidth();
        int col = (int) x / (int) colWidth;
        if (col < 0 || col > 6)
            return -1;
        return col;
    }


    private int resourceForTurn(char currentPlayer) {
        switch (currentPlayer) {
            case 'X':
                return R.drawable.red;
            case 'O':
                return R.drawable.black;
        }
        return R.drawable.red;
    }


    private void reset() {
        inProgress = true;
        connect_4 = new Connect_4();
        //winnerText.setVisibility(View.GONE);
        //turnIndicatorImageView.setImageResource(resourceForTurn());
        for (int row = 0; row < connect_4.NUM_ROWS; row++) {
            for (int col = 0; col < connect_4.NUM_COLS; col++) {
                board[row][col].setImageResource(android.R.color.transparent);
            }
        }
    }
}
