package app.a3_in_1_game;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static app.a3_in_1_game.Tic_Tac_Toe.AiCol;
import static app.a3_in_1_game.Tic_Tac_Toe.AiRow;

public class Tic_Tac_Toe_Activity extends AppCompatActivity {
   static Button b1,b2,b3,b4,b5,b6,b7,b8,b9, b10;
    static TextView textView;
    final Tic_Tac_Toe t = new Tic_Tac_Toe();
    static int score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);
        b6 = (Button) findViewById(R.id.button6);
        b7 = (Button) findViewById(R.id.button7);
        b8 = (Button) findViewById(R.id.button8);
        b9 = (Button) findViewById(R.id.button9);
        b10 =(Button) findViewById(R.id.button10);
        textView =(TextView) findViewById(R.id.textView3);
       // textView.setText("sdf");

       // final Tic_Tac_Toe t = new Tic_Tac_Toe();
        //t.playGame();
        int turnCounter = 0;
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //Tic_Tac_Toe t = new Tic_Tac_Toe();
                t.playGame();
                b1.setText("");
                b2.setText("");
                b3.setText("");
                b4.setText("");
                b5.setText("");
                b6.setText("");
                b7.setText("");
                b8.setText("");
                b9.setText("");
                score--;
                String message = "score: " + score;
                textView.setText(message);
              //  Context context = getApplicationContext();
             //   CharSequence text = "Score Decreased!";
             //   int duration = Toast.LENGTH_SHORT;

              //  Toast toast = Toast.makeText(context, text, duration);
              //  toast.show();
    b10.setText("Forfeit?");
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b1.getText()!= ""){
                    return;
                }
                if (t.gameOver() == true){
                    return;
                }
                b1.setText("X");
                t.playerTurn(0,0);
                if (t.gameOver() == true){
                    updateScore(1);
                    b10.setText("Restart");
                    score++;
                    return;
                }
                boolean check = t.computerTurn();
                if (t.gameOver() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "You Lose! Score Decreased!";
                    int duration = Toast.LENGTH_SHORT;
                    score--;
                    String message = "score: " + score;
                    textView.setText(message);
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    b10.setText("Restart");
                    score++;
                }
                if (check == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Its a Tie!";
                    int duration = Toast.LENGTH_SHORT;
        score++;
                    b10.setText("Restart");
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();                }
                else
                updateBoard(AiRow, AiCol);

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b2.getText()!= ""){
                    return;
                }
                if (t.gameOver() == true){
                    return;
                }
                b2.setText("X");
                t.playerTurn(0,1);
                if (t.gameOver() == true){
                    updateScore(1);
                    b10.setText("Restart");
                    score++;
                    return;
                }
                boolean check = t.computerTurn();
                if (t.gameOver() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "You Lose! Score Decreased!";
                    int duration = Toast.LENGTH_SHORT;
                    score--;
                    String message = "score: " + score;
                    textView.setText(message);
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    b10.setText("Restart");
                    score++;
                }
                if (check == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Its a Tie!";
                    int duration = Toast.LENGTH_SHORT;
                    score++;
                    b10.setText("Restart");

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else
                    updateBoard(AiRow, AiCol);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b3.getText()!= ""){
                    return;
                }
                if (t.gameOver() == true){
                    return;
                }
                b3.setText("X");
                t.playerTurn(0,2);
                if (t.gameOver() == true){
                    updateScore(1);
                    b10.setText("Restart");
                    score++;
                    return;
                }
                boolean check = t.computerTurn();
                if (t.gameOver() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "You Lose! Score Decreased!";
                    int duration = Toast.LENGTH_SHORT;
                    score--;
                    String message = "score: " + score;
                    textView.setText(message);
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    b10.setText("Restart");
                    score++;}
                if (check == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Its a Tie!";
                    int duration = Toast.LENGTH_SHORT;
                    score++;
                    b10.setText("Restart");

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();                }
                else
                    updateBoard(AiRow, AiCol);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b4.getText()!= ""){
                    return;
                }
                if (t.gameOver() == true){
                    return;
                }
                b4.setText("X");
                t.playerTurn(1,0);
                if (t.gameOver() == true){
                    updateScore(1);
                    b10.setText("Restart");
                    score++;
                    return;
                }
                boolean check = t.computerTurn();
                if (t.gameOver() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "You Lose! Score Decreased!";
                    int duration = Toast.LENGTH_SHORT;
                    score--;
                    String message = "score: " + score;
                    textView.setText(message);
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    b10.setText("Restart");
                    score++;}
                if (check == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Its a Tie!";
                    int duration = Toast.LENGTH_SHORT;
                    score++;
                    b10.setText("Restart");

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();                }
                else
                    updateBoard(AiRow, AiCol);
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b5.getText()!= ""){
                    return;
                }
                if (t.gameOver() == true){
                    return;
                }
                b5.setText("X");
                t.playerTurn(1,1);
                if (t.gameOver() == true){
                    updateScore(1);
                    b10.setText("Restart");
                    score++;
                    return;
                }
                boolean check = t.computerTurn();
                if (t.gameOver() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "You Lose! Score Decreased!";
                    int duration = Toast.LENGTH_SHORT;
                    score--;
                    String message = "score: " + score;
                    textView.setText(message);
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    b10.setText("Restart");
                    score++;}
                if (check == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Its a Tie!";
                    int duration = Toast.LENGTH_SHORT;
                    score++;
                    b10.setText("Restart");

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();                }
                else
                    updateBoard(AiRow, AiCol);
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b6.getText()!= ""){
                    return;
                }
                if (t.gameOver() == true){
                    return;
                }
                b6.setText("X");
                t.playerTurn(1,2);
                if (t.gameOver() == true){
                    updateScore(1);
                    b10.setText("Restart");
                    score++;
                    return;
                }
                boolean check = t.computerTurn();
                if (t.gameOver() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "You Lose! Score Decreased!";
                    int duration = Toast.LENGTH_SHORT;
                    score--;
                    String message = "score: " + score;
                    textView.setText(message);
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    b10.setText("Restart");
                    score++;}
                if (check == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Its a Tie!";
                    int duration = Toast.LENGTH_SHORT;
                    score++;
                    b10.setText("Restart");

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();                }
                else
                    updateBoard(AiRow, AiCol);
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b7.getText()!= ""){
                    return;
                }
                if (t.gameOver() == true){
                    return;
                }
                b7.setText("X");
                t.playerTurn(2,0);
                if (t.gameOver() == true){
                    updateScore(1);
                    b10.setText("Restart");
                    score++;
                    return;
                }
                boolean check = t.computerTurn();
                if (t.gameOver() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "You Lose! Score Decreased!";
                    int duration = Toast.LENGTH_SHORT;
                    score--;
                    String message = "score: " + score;
                    textView.setText(message);
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    b10.setText("Restart");
                    score++;}
                if (check == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Its a Tie!";
                    int duration = Toast.LENGTH_SHORT;
                    score++;
                    b10.setText("Restart");

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();                }
                else
                    updateBoard(AiRow, AiCol);
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b8.getText()!= ""){
                    return;
                }
                if (t.gameOver() == true){
                    return;
                }
                b8.setText("X");

                t.playerTurn(2,1);
                if (t.gameOver() == true){
                    updateScore(1);
                    b10.setText("Restart");
                    score++;

                    return;
                }
                boolean check = t.computerTurn();
                if (t.gameOver() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "You Lose! Score Decreased!";
                    int duration = Toast.LENGTH_SHORT;
                    score--;
                    String message = "score: " + score;
                    textView.setText(message);
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    b10.setText("Restart");
                    score++;}
                if (check == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Its a Tie!";
                    int duration = Toast.LENGTH_SHORT;
                    score++;
                    b10.setText("Restart");

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else
                    updateBoard(AiRow, AiCol);

            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b9.getText()!= ""){
                    return;
                }
                if (t.gameOver() == true){
                    return;
                }
                b9.setText("X");

                t.playerTurn(2,2);
                if (t.gameOver() == true){
                    updateScore(1);
                    return;
                }
                boolean check = t.computerTurn();
                if (t.gameOver() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "You Lose! Score Decreased!";
                    int duration = Toast.LENGTH_SHORT;
                    score--;
                    String message = "score: " + score;
                    textView.setText(message);
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();                }
                if (check == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Its a Tie!";
                    int duration = Toast.LENGTH_SHORT;
                    score++;
                    b10.setText("Restart");

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();                }
                else
                    updateBoard(AiRow, AiCol);

            }
        });


    }

    private void updateBoard(int row, int col) {
        if (row == 0 && col == 0){
            b1.setText("O");
        }
        if (row == 0 && col == 1){
            b2.setText("O");
        }
        if (row == 0 && col == 2){
            b3.setText("O");
        }
        if (row == 1 && col == 0){
            b4.setText("O");
        }
        if (row == 1 && col == 1){
            b5.setText("O");
        }
        if (row == 1 && col == 2){
            b6.setText("O");
        }
        if (row == 2 && col == 0){
            b7.setText("O");
        }
        if (row == 2 && col == 1){
            b8.setText("O");
        }
        if (row == 2 && col == 2){
            b9.setText("O");
        }

    }

public void updateScore(int player){
    Context context = getApplicationContext();
    CharSequence text = "You win!";
    int duration = Toast.LENGTH_SHORT;

    Toast toast = Toast.makeText(context, text, duration);
    toast.show();
    score++;
    String message = "score: " + score;
textView.setText(message);
}
}
