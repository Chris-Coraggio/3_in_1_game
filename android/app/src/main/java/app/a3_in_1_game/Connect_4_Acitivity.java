package app.a3_in_1_game;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Connect_4_Acitivity extends AppCompatActivity {
    private static boolean myTurn = false;
    private final String url = MySingleton.url;
    RequestQueue requestQueue;
    private Context context;
    private String host = MySingleton.connect_4_host;
    private SharedPreferences sharedPref;
    private String user;
    private Thread thread;
    private ImageView[][] board;
    private View boardView;
    private Connect_4 connect_4;
    private boolean inProgress = true;
    private int score = 0;
    private boolean multiplayer = MySingleton.connect_4_multiplayer;
    private TextView scoreText;
    private String text;
    private Button button;
    private int setCol = -1;
    private boolean run;

    protected void restartState() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String req = url + "/connect_4_restart/" + host + "/" + user;
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
                String req = url + "/connect_4/" + host + "/" + user;
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
                                        if (!inProgress && state == null) {
                                            // 1st player to hit restart
                                            run = false;
                                            (Connect_4_Acitivity.this).runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    reset();
                                                    restartState();
                                                }
                                            });
                                            return;
                                        } else if (!inProgress && state.equals("RESTART")) {
                                            // 2nd player to hit restart
                                            run = false;
                                            (Connect_4_Acitivity.this).runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    reset();
                                                    restartState();
                                                }
                                            });
                                            return;
                                        } else if (inProgress && state != null
                                                && state.equals("RESTART")) {
                                            // 1st player is waiting for 2nd player to hit restart
                                            return;
                                        }
                                        myTurn = Objects.equals(response.getString("turn"), user);
                                        if (myTurn) {
                                            setCol = response.getInt("col");
                                            if (setCol != -1) {
                                                (Connect_4_Acitivity.this).runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        drop(setCol, connect_4.CIRCLE);
                                                        setCol = -1;
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
                                    Toast.makeText(context, "(update) " + error.toString(),
                                            Toast.LENGTH_SHORT).show();
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

    protected void post(int col) {
        String req = url + "/connect_4/" + host + "/" + user + "/" + col;
        System.err.println(req);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, req,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Move successful!")) {
                            update();
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_4__acitivity);
        connect_4 = new Connect_4();
        boardView = findViewById(R.id.game_board);
        context = this;
        sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        user = sharedPref.getString("user", "");
        requestQueue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        setBoard();
        if (multiplayer) {
            run = true;
            update();
        }

        boardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_POINTER_UP:
                    case MotionEvent.ACTION_UP: {
                        int col = getCol(motionEvent.getX());
                        if (col != -1 && multiplayer && myTurn && inProgress) {
                            myTurn = false;
                            drop(col, connect_4.CROSS);
                            post(col);
                        } else if (col != -1 && !multiplayer & inProgress) {
                            drop(col, connect_4.CROSS);
                            drop(-1, connect_4.CIRCLE);
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
                    // Game in progress
                    score--;
                    text = "Score: " + score;
                    scoreText.setText(text);
                    reset();
                } else {
                    // Game ended
                    if (!multiplayer) {
                        reset();
                    } else {
                        run = true;
                        myTurn = false;
                        update();
                    }
                    text = "Forfeit";
                    button.setText(text);
                    if (multiplayer) {
                        button.setVisibility(View.GONE);
                    }
                }
            }
        });
        if (multiplayer) {
            button.setVisibility(View.GONE);
        }
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
        if (col == -1 && !multiplayer) {
            // TODO: Create new thread and sleep
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
            if (multiplayer) {
                run = false;
            }
            win(currentPlayer);
        }
    }

    private void win(char currentPlayer) {
        inProgress = false;
        text = "Restart";
        button.setText(text);
        if (multiplayer) {
            button.setVisibility(View.VISIBLE);
        }

        String winner;
        if (currentPlayer == connect_4.CROSS) {
            winner = "RED";
            score++;
            text = "Score: " + score;
            scoreText.setText(text);
        } else {
            winner = "BLACK";
            score -= 2;
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

    @Override
    protected void onResume() {
        super.onResume();
        if (multiplayer) {
            myTurn = false;
            run = true;
            update();
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
