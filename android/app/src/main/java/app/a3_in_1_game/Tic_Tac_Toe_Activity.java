package app.a3_in_1_game;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static app.a3_in_1_game.Tic_Tac_Toe.AiCol;
import static app.a3_in_1_game.Tic_Tac_Toe.AiRow;

public class Tic_Tac_Toe_Activity extends AppCompatActivity {
    private static boolean myTurn = false;
    final Tic_Tac_Toe t = new Tic_Tac_Toe();
    private final String url = MySingleton.url;
    public boolean multiplayer = MySingleton.tic_tac_toe_multiplayer;
    RequestQueue requestQueue;
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10;
    private TextView textView;
    private Thread thread;
    private boolean run;
    private String host = MySingleton.tic_tac_toe_host;
    private String user;
    private int setCol = -1;
    private int setRow = -1;
    private Context context;
    private int score = 0;
    private SharedPreferences sharedPref;

    protected void restartState() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String req = url + "/tic_tac_toe_restart/" + host + "/" + user;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, req,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("State set to RESTART!") ||
                                        response.equals("State removed!")) {
                                    run = true;
                                    update();
                                } else {
                                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "(restartState) " + error.toString(),
                                Toast.LENGTH_SHORT).show();
                        System.err.println("(restartState) " + error.toString());
                    }
                });
                stringRequest.setTag(this);
                MySingleton.getInstance(context).addToRequestQueue(stringRequest);
            }
        }).start();
    }

    protected void update() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final String req = url + "/tic_tac_toe/" + host + "/" + user;
                System.err.println(req);
                while (!myTurn && run) {
                    JsonObjectRequest jsonObjectRequest =
                            new JsonObjectRequest(Request.Method.GET, req, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        System.err.println("RESPONSE: " + response.toString());
                                        if (myTurn) {
                                            return;
                                        }
                                        String state;
                                        try {
                                            state = response.getString("state");
                                        } catch (JSONException e) {
                                            state = null;
                                        }
                                        if ((t.gameOver() || t.spacesOccupied == 9) && state == null) {
                                            // 1st player to hit restart
                                            run = false;
                                            (Tic_Tac_Toe_Activity.this).runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
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
                                                    restartState();
                                                }
                                            });
                                            return;
                                        } else if ((t.gameOver() || t.spacesOccupied == 9) && state.equals("RESTART")) {
                                            // 2nd player to hit restart
                                            run = false;
                                            (Tic_Tac_Toe_Activity.this).runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
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
                                                    restartState();
                                                }
                                            });
                                            return;
                                        } else if (!t.gameOver() && state != null
                                                && state.equals("RESTART")) {
                                            // 1st player is waiting for 2nd player to hit restart
                                            return;
                                        }
                                        myTurn = Objects.equals(response.getString("turn"), user);
                                        if (myTurn) {
                                            setCol = response.getInt("col");
                                            setRow = response.getInt("row");
                                            if (setCol != -1 && setRow != -1) {
                                                (Tic_Tac_Toe_Activity.this).runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        updateBoard(setRow, setCol);
                                                        t.spacesOccupied++;
                                                        if (t.gameOver()) {
                                                            context = getApplicationContext();
                                                            CharSequence text = "You Lose! Score Decreased!";
                                                            int duration = Toast.LENGTH_SHORT;
                                                            score--;
                                                            String message = "score: " + score;
                                                            textView.setText(message);
                                                            Toast toast = Toast.makeText(context, text, duration);
                                                            toast.show();
                                                            b10.setText(R.string.restart);
                                                            b10.setVisibility(View.VISIBLE);
                                                            score++;
                                                        }
                                                        if (t.spacesOccupied == 9) {
                                                            context = getApplicationContext();
                                                            CharSequence text = "Its a Tie!";
                                                            int duration = Toast.LENGTH_SHORT;
                                                            score++;
                                                            b10.setText(R.string.restart);
                                                            b10.setVisibility(View.VISIBLE);
                                                            Toast toast = Toast.makeText(context, text, duration);
                                                            toast.show();
                                                        }
                                                        setCol = -1;
                                                        setRow = -1;
                                                    }
                                                });
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    jsonObjectRequest.setTag(this);
                    MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    protected void post(int row, int col) {
        if (!multiplayer) {
            return;
        }
        String req = url + "/tic_tac_toe/" + host + "/" + user + "/" + col + "-" + row;
        System.err.println(req);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, req,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Move successful!")) {
                            if (t.gameOver() || t.spacesOccupied == 9) {
                                run = false;
                            } else {
                                update();
                            }
                        } else {
                            Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setTag(this);
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
        myTurn = false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);
        context = this;
        run = true;
        sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        user = sharedPref.getString("user", "");
        requestQueue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();


        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);
        b6 = (Button) findViewById(R.id.button6);
        b7 = (Button) findViewById(R.id.button7);
        b8 = (Button) findViewById(R.id.button8);
        b9 = (Button) findViewById(R.id.button9);
        b10 = (Button) findViewById(R.id.button10);
        textView = (TextView) findViewById(R.id.textView3);

        if (multiplayer) {
            update();
        } else {
            myTurn = true;
        }
        // textView.setText("sdf");

        // final Tic_Tac_Toe t = new Tic_Tac_Toe();
        //t.playGame();
        int turnCounter = 0;
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tic_Tac_Toe t = new Tic_Tac_Toe();
                if (!multiplayer) {
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
                }
                score--;
                String message = "score: " + score;
                textView.setText(message);
                if (multiplayer) {
                    run = true;
                    myTurn = false;
                    update();
                    b10.setVisibility(View.GONE);
                }
                //  Context context = getApplicationContext();
                //   CharSequence text = "Score Decreased!";
                //   int duration = Toast.LENGTH_SHORT;

                //  Toast toast = Toast.makeText(context, text, duration);
                //  toast.show();
                b10.setText(R.string.forfeit);
            }
        });
        if (multiplayer) {
            b10.setVisibility(View.GONE);
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myTurn) {
                    if (b1.getText() != "") {
                        return;
                    }
                    if (t.gameOver()) {
                        return;
                    }
                    b1.setText("X");
                    t.playerTurn(0, 0);
                    post(0, 0);
                    if (t.gameOver()) {
                        updateScore(1);
                        b10.setText(R.string.restart);
                        score++;
                        b10.setVisibility(View.VISIBLE);
                        return;
                    }
                    boolean check = t.computerTurn();
                    if (t.gameOver()) {
                        context = getApplicationContext();
                        CharSequence text = "You Lose! Score Decreased!";
                        int duration = Toast.LENGTH_SHORT;
                        score--;
                        String message = "score: " + score;
                        textView.setText(message);
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        b10.setText(R.string.restart);
                        b10.setVisibility(View.VISIBLE);
                        score++;
                    }
                    if (check) {
                        context = getApplicationContext();
                        CharSequence text = "Its a Tie!";
                        int duration = Toast.LENGTH_SHORT;
                        score++;
                        b10.setText(R.string.restart);
                        b10.setVisibility(View.VISIBLE);
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else if (!multiplayer) {
                        updateBoard(AiRow, AiCol);
                    }
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myTurn) {
                    if (b2.getText() != "") {
                        return;
                    }
                    if (t.gameOver()) {
                        return;
                    }
                    b2.setText("X");
                    t.playerTurn(0, 1);
                    post(0, 1);
                    if (t.gameOver()) {
                        updateScore(1);
                        b10.setText(R.string.restart);
                        score++;
                        b10.setVisibility(View.VISIBLE);
                        return;
                    }
                    boolean check = t.computerTurn();
                    if (t.gameOver()) {
                        context = getApplicationContext();
                        CharSequence text = "You Lose! Score Decreased!";
                        int duration = Toast.LENGTH_SHORT;
                        score--;
                        String message = "score: " + score;
                        textView.setText(message);
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        b10.setText(R.string.restart);
                        b10.setVisibility(View.VISIBLE);
                        score++;
                    }
                    if (check) {
                        context = getApplicationContext();
                        CharSequence text = "Its a Tie!";
                        int duration = Toast.LENGTH_SHORT;
                        score++;
                        b10.setText(R.string.restart);
                        b10.setVisibility(View.VISIBLE);

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else if (!multiplayer) {
                        updateBoard(AiRow, AiCol);
                    }
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myTurn) {
                    if (b3.getText() != "") {
                        return;
                    }
                    if (t.gameOver()) {
                        return;
                    }
                    b3.setText("X");
                    t.playerTurn(0, 2);
                    post(0, 2);
                    if (t.gameOver()) {
                        updateScore(1);
                        b10.setText(R.string.restart);
                        score++;
                        b10.setVisibility(View.VISIBLE);
                        return;
                    }
                    boolean check = t.computerTurn();
                    if (t.gameOver()) {
                        context = getApplicationContext();
                        CharSequence text = "You Lose! Score Decreased!";
                        int duration = Toast.LENGTH_SHORT;
                        score--;
                        String message = "score: " + score;
                        textView.setText(message);
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        b10.setText(R.string.restart);
                        score++;
                        b10.setVisibility(View.VISIBLE);
                    }
                    if (check) {
                        context = getApplicationContext();
                        CharSequence text = "Its a Tie!";
                        int duration = Toast.LENGTH_SHORT;
                        score++;
                        b10.setText(R.string.restart);
                        b10.setVisibility(View.VISIBLE);

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else if (!multiplayer) {
                        updateBoard(AiRow, AiCol);
                    }
                }
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myTurn) {
                    if (b4.getText() != "") {
                        return;
                    }
                    if (t.gameOver()) {
                        return;
                    }
                    b4.setText("X");
                    t.playerTurn(1, 0);
                    post(1, 0);
                    if (t.gameOver()) {
                        updateScore(1);
                        b10.setText(R.string.restart);
                        score++;
                        b10.setVisibility(View.VISIBLE);
                        return;
                    }
                    boolean check = t.computerTurn();
                    if (t.gameOver()) {
                        context = getApplicationContext();
                        CharSequence text = "You Lose! Score Decreased!";
                        int duration = Toast.LENGTH_SHORT;
                        score--;
                        String message = "score: " + score;
                        textView.setText(message);
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        b10.setText(R.string.restart);
                        score++;
                        b10.setVisibility(View.VISIBLE);
                    }
                    if (check) {
                        context = getApplicationContext();
                        CharSequence text = "Its a Tie!";
                        int duration = Toast.LENGTH_SHORT;
                        score++;
                        b10.setText(R.string.restart);
                        b10.setVisibility(View.VISIBLE);

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else if (!multiplayer) {
                        updateBoard(AiRow, AiCol);
                    }
                }
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myTurn) {
                    if (b5.getText() != "") {
                        return;
                    }
                    if (t.gameOver()) {
                        return;
                    }
                    b5.setText("X");
                    t.playerTurn(1, 1);
                    post(1, 1);
                    if (t.gameOver()) {
                        updateScore(1);
                        b10.setText(R.string.restart);
                        score++;
                        b10.setVisibility(View.VISIBLE);
                        return;
                    }
                    boolean check = t.computerTurn();
                    if (t.gameOver()) {
                        context = getApplicationContext();
                        CharSequence text = "You Lose! Score Decreased!";
                        int duration = Toast.LENGTH_SHORT;
                        score--;
                        String message = "score: " + score;
                        textView.setText(message);
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        b10.setText(R.string.restart);
                        score++;
                        b10.setVisibility(View.VISIBLE);
                    }
                    if (check) {
                        context = getApplicationContext();
                        CharSequence text = "Its a Tie!";
                        int duration = Toast.LENGTH_SHORT;
                        score++;
                        b10.setText(R.string.restart);
                        b10.setVisibility(View.VISIBLE);

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else if (!multiplayer) {
                        updateBoard(AiRow, AiCol);
                    }
                }
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myTurn) {
                    if (b6.getText() != "") {
                        return;
                    }
                    if (t.gameOver()) {
                        return;
                    }
                    b6.setText("X");
                    t.playerTurn(1, 2);
                    post(1, 2);
                    if (t.gameOver()) {
                        updateScore(1);
                        b10.setText(R.string.restart);
                        score++;
                        b10.setVisibility(View.VISIBLE);
                        return;
                    }
                    boolean check = t.computerTurn();
                    if (t.gameOver()) {
                        context = getApplicationContext();
                        CharSequence text = "You Lose! Score Decreased!";
                        int duration = Toast.LENGTH_SHORT;
                        score--;
                        String message = "score: " + score;
                        textView.setText(message);
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        b10.setText(R.string.restart);
                        score++;
                        b10.setVisibility(View.VISIBLE);
                    }
                    if (check) {
                        context = getApplicationContext();
                        CharSequence text = "Its a Tie!";
                        int duration = Toast.LENGTH_SHORT;
                        score++;
                        b10.setText(R.string.restart);
                        b10.setVisibility(View.VISIBLE);

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else if (!multiplayer) {
                        updateBoard(AiRow, AiCol);
                    }
                }
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myTurn) {
                    if (b7.getText() != "") {
                        return;
                    }
                    if (t.gameOver()) {
                        return;
                    }
                    b7.setText("X");
                    t.playerTurn(2, 0);
                    post(2, 0);
                    if (t.gameOver()) {
                        updateScore(1);
                        b10.setText(R.string.restart);
                        score++;
                        b10.setVisibility(View.VISIBLE);
                        return;
                    }
                    boolean check = t.computerTurn();
                    if (t.gameOver()) {
                        context = getApplicationContext();
                        CharSequence text = "You Lose! Score Decreased!";
                        int duration = Toast.LENGTH_SHORT;
                        score--;
                        String message = "score: " + score;
                        textView.setText(message);
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        b10.setText(R.string.restart);
                        score++;
                        b10.setVisibility(View.VISIBLE);
                    }
                    if (check) {
                        context = getApplicationContext();
                        CharSequence text = "Its a Tie!";
                        int duration = Toast.LENGTH_SHORT;
                        score++;
                        b10.setText(R.string.restart);
                        b10.setVisibility(View.VISIBLE);

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else if (!multiplayer) {
                        updateBoard(AiRow, AiCol);
                    }
                }
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myTurn) {
                    if (b8.getText() != "") {
                        return;
                    }
                    if (t.gameOver()) {
                        return;
                    }
                    b8.setText("X");
                    t.playerTurn(2, 1);
                    post(2, 1);
                    if (t.gameOver()) {
                        updateScore(1);
                        b10.setText(R.string.restart);
                        score++;
                        b10.setVisibility(View.VISIBLE);
                        return;
                    }
                    boolean check = t.computerTurn();
                    if (t.gameOver()) {
                        context = getApplicationContext();
                        CharSequence text = "You Lose! Score Decreased!";
                        int duration = Toast.LENGTH_SHORT;
                        score--;
                        String message = "score: " + score;
                        textView.setText(message);
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        b10.setText(R.string.restart);
                        score++;
                        b10.setVisibility(View.VISIBLE);
                    }
                    if (check) {
                        context = getApplicationContext();
                        CharSequence text = "Its a Tie!";
                        int duration = Toast.LENGTH_SHORT;
                        score++;
                        b10.setText(R.string.restart);
                        b10.setVisibility(View.VISIBLE);

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else if (!multiplayer) {
                        updateBoard(AiRow, AiCol);
                    }
                }
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myTurn) {
                    if (b9.getText() != "") {
                        return;
                    }
                    if (t.gameOver()) {
                        return;
                    }
                    b9.setText("X");
                    t.playerTurn(2, 2);
                    post(2, 2);
                    if (t.gameOver()) {
                        updateScore(1);
                        b10.setText(R.string.restart);
                        score++;
                        b10.setVisibility(View.VISIBLE);
                        return;
                    }
                    boolean check = t.computerTurn();
                    if (t.gameOver()) {
                        context = getApplicationContext();
                        CharSequence text = "You Lose! Score Decreased!";
                        int duration = Toast.LENGTH_SHORT;
                        score--;
                        String message = "score: " + score;
                        textView.setText(message);
                        b10.setText(R.string.restart);
                        score++;
                        b10.setVisibility(View.VISIBLE);

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    if (check) {
                        context = getApplicationContext();
                        CharSequence text = "Its a Tie!";
                        int duration = Toast.LENGTH_SHORT;
                        score++;
                        b10.setText(R.string.restart);
                        b10.setVisibility(View.VISIBLE);

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else if (!multiplayer) {
                        updateBoard(AiRow, AiCol);
                    }
                }
            }
        });
    }

    private void updateBoard(int row, int col) {
        if (multiplayer) {
            t.board[row][col] = '0';
        }

        if (row == 0 && col == 0) {
            b1.setText("O");
        }
        if (row == 0 && col == 1) {
            b2.setText("O");
        }
        if (row == 0 && col == 2) {
            b3.setText("O");
        }
        if (row == 1 && col == 0) {
            b4.setText("O");
        }
        if (row == 1 && col == 1) {
            b5.setText("O");
        }
        if (row == 1 && col == 2) {
            b6.setText("O");
        }
        if (row == 2 && col == 0) {
            b7.setText("O");
        }
        if (row == 2 && col == 1) {
            b8.setText("O");
        }
        if (row == 2 && col == 2) {
            b9.setText("O");
        }

    }

    public void updateScore(int player) {
        context = getApplicationContext();
        CharSequence text = "You win!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        score += 3;
        String message = "score: " + score;
        textView.setText(message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (multiplayer) {
            myTurn = false;
            run = true;
            update();
        } else {
            myTurn = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (multiplayer) {
            run = false;
            thread.interrupt();
        }
    }
}
