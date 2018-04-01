package app.a3_in_1_game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static app.a3_in_1_game.Hangman.currentWord;
import static app.a3_in_1_game.Hangman.numWrongWordGuesses;

public class Hangman_Activity extends AppCompatActivity {
    private static int MAX_NUM_WORDS_MULTIPLAYER = 20;
    private static long startTime; //stores the start time for multiplayer timing
    private static int numWordsGuessed = 0;                          //score for multiplayer games
    private static int totalNumWrongGuesses = 0;                     //tiebreaker for multiplayer
    public InputStream inputStream;
    public String[] wordsList = new String[MAX_NUM_WORDS_MULTIPLAYER];
    public boolean multiplayer = MySingleton.hangman_multiplayer;
    int score = 0;
    RequestQueue requestQueue;
    int indexInWordsList = 0; //count of where we are in the words list
    private String currWord = "";
    private LinearLayout wordLayout;
    private TextView[] charViews;
    private GridView letters;
    //body part images
    private ImageView[] bodyParts;
    //number of body parts
    private int numParts = 6;
    //current part - will increment when wrong answers are chosen
    private int currPart;
    //number correctly guessed
    private int numCorr;
    private LetterAdapter ltrAdapt;
    private String winner;
    private Thread thread;
    private String url = MySingleton.url;
    private Context context;
    private boolean run;
    private String host = MySingleton.hangman_host;
    private String user;
    private int numErrors;
    private TextView textView;
    private SharedPreferences sharedPref;
    private boolean waiting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_);
        Resources res = getResources();

        sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        user = sharedPref.getString("user", "");

        wordLayout = (LinearLayout) findViewById(R.id.word);
        letters = (GridView) findViewById(R.id.letters);
        requestQueue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        bodyParts = new ImageView[numParts];
        bodyParts[0] = (ImageView) findViewById(R.id.head);
        bodyParts[1] = (ImageView) findViewById(R.id.body);
        bodyParts[2] = (ImageView) findViewById(R.id.arm1);
        bodyParts[3] = (ImageView) findViewById(R.id.arm2);
        bodyParts[4] = (ImageView) findViewById(R.id.leg1);
        bodyParts[5] = (ImageView) findViewById(R.id.leg2);
        textView = (TextView) findViewById(R.id.hangmanScore);

        for (int p = 0; p < numParts; p++) {
            bodyParts[p].setVisibility(View.INVISIBLE);//use this to show body parts
        }

        context = this;
        if (!multiplayer) {
            playGame();
        } else {
            run = true;
            getWordsList();
        }
    }

    protected void restartState() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String req = url + "/hangman_restart/" + host + "/" + user;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, req,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("State set to RESTART!") ||
                                        response.equals("State removed!")) {
                                    run = true;
                                    winner = null;
                                    indexInWordsList = 0;
                                    numWordsGuessed = 0;
                                    numWrongWordGuesses = 0;
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
                final String req = url + "/hangman/" + host + "/" + user;
                System.err.println(req);
                while (winner == null && run) {
                    JsonObjectRequest jsonObjectRequest =
                            new JsonObjectRequest(Request.Method.GET, req, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    System.err.println("RESPONSE: " + response.toString());
                                    String state;
                                    if (wordsList[0].equals("") && !waiting) {
                                        // need new words
                                        waiting = true;
                                        getWordsList();
                                        return;
                                    }
                                    if (wordsList[0].equals("") && waiting) {
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        return;
                                    }
                                    if (!wordsList[0].equals("") && waiting) {
                                        waiting = false;
                                        run = false;
                                        return;
                                    }
                                    try {
                                        state = response.getString("state");
                                    } catch (JSONException e) {
                                        state = null;
                                    }
                                    if (state != null && state.equals("RESTART")) {
                                        // 1st player is waiting for 2nd player to hit restart
                                        return;
                                    }

                                    try {
                                        winner = response.getString("winner");
                                    } catch (JSONException e) {
                                        winner = null;
                                    }

                                    if (winner != null) {
                                        (Hangman_Activity.this).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                                //display who won
                                                if (winner.equals(user)) {
                                                    // Display Alert Dialog
                                                    builder.setTitle("You Win!");
                                                    builder.setMessage("\nYour score increased to " + ++score + "\nThe answer was: " + currWord + "\n");
                                                    builder.setPositiveButton("Play Again",
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    wordsList[0] = "";
                                                                    restartState();
                                                                }
                                                            });
                                                    String message = "Score: " + score;
                                                    textView.setText(message);
                                                    builder.setNegativeButton("Exit",
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    Hangman_Activity.this.finish();
                                                                }
                                                            });
                                                } else if (winner.equals("tie")) {
                                                    // Display Alert Dialog
                                                    builder.setTitle("You Win!");
                                                    builder.setMessage("\nYour score increased to " + ++score + "\nThe answer was: " + currWord + "\n");
                                                    builder.setPositiveButton("Play Again",
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    wordsList[0] = "";
                                                                    restartState();
                                                                }
                                                            });
                                                    String message = "Score: " + score;
                                                    textView.setText(message);
                                                    builder.setNegativeButton("Exit",
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    Hangman_Activity.this.finish();
                                                                }
                                                            });
                                                } else {
                                                    // Display Alert Dialog
                                                    builder.setTitle("You lose!");
                                                    builder.setMessage("Score decreased to " + --score + "\nThe answer was: " + currWord + "\n");
                                                    builder.setPositiveButton("Play Again",
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    wordsList[0] = "";
                                                                    restartState();
                                                                }
                                                            });
                                                    String message = "Score: " + score;
                                                    textView.setText(message);
                                                    builder.setNegativeButton("Exit",
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    Hangman_Activity.this.finish();
                                                                }
                                                            });
                                                }
                                                builder.show();
                                            }
                                        });
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context,
                                            "(update) " + error.toString(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                    jsonObjectRequest.setTag(this);
                    MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    protected void post(int score, int numErrors) {
        String req = url + "/hangman_done/" + host + "/" + user + "/" + score + "/" + numErrors;
        System.err.println(req);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, req,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("No error")) {
                            update();
                        } else {
                            Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        "(post) " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setTag(this);
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    protected void getWordsList() {
        String req = url + "/hangman/" + host + "/" + user;
        System.err.println(req);
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, req, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.err.println("RESPONSE: " + response.toString());
                            JSONArray words = response.getJSONArray("words");
                            for (int i = 0; i < words.length(); i++) {
                                wordsList[i] = words.getString(i);
                            }
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    (Hangman_Activity.this).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            disableBtns();
                                        }
                                    });
                                    run = true;
                                    post(numWordsGuessed, totalNumWrongGuesses);
                                }
                            }, 30000 * 4);

                            playGame();
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
    }

    public void getRandomWord() throws IOException {
        inputStream = getAssets().open("HangmanWordList.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        int numLinesInFile = 0;

        while (bufferedReader.readLine() != null) {
            numLinesInFile++;
        }

        //start the buffer back out at the top of the file
        inputStream = getAssets().open("HangmanWordList.txt");
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        //extracts a random line as the word
        Random r = new Random();
        int randomLine = r.nextInt(numLinesInFile);
        String trmp = "";
        for (int i = 0; i < randomLine; i++) {
            trmp = bufferedReader.readLine();
        }

        currentWord = trmp.toCharArray();
        String text = String.valueOf(currentWord);
        currWord = text.toLowerCase();
        System.out.println(text);
    }

    public void playGame() {
        if (!multiplayer) {
            try {
                getRandomWord();
            } catch (IOException e) {
                e.printStackTrace();
            }
            currPart = 0;
            numCorr = 0;
            charViews = new TextView[currWord.length()];
            wordLayout.removeAllViews();
            //do currWord = ... here
            for (int c = 0; c < currWord.length(); c++) {
                charViews[c] = new TextView(this);
                charViews[c].setText("" + currWord.charAt(c));

                charViews[c].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                charViews[c].setGravity(Gravity.CENTER);
                charViews[c].setTextColor(Color.WHITE);
                charViews[c].setBackgroundResource(R.drawable.letter_bg);
                //add to layout
                wordLayout.addView(charViews[c]);
            }
            ltrAdapt = new LetterAdapter(this);
            letters.setAdapter(ltrAdapt);
            for (int p = 0; p < numParts; p++) {
                bodyParts[p].setVisibility(View.INVISIBLE);
            }
        } else {
            //multiplayer
            currPart = 0;
            numCorr = 0;
            charViews = new TextView[wordsList[indexInWordsList].length()];
            wordLayout.removeAllViews();
            //do currWord = ... here
            currWord = wordsList[indexInWordsList];
            //noinspection UnusedAssignment
            indexInWordsList++;

            for (int c = 0; c < currWord.length(); c++) {
                charViews[c] = new TextView(this);
                charViews[c].setText("" + currWord.charAt(c));
                charViews[c].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                charViews[c].setGravity(Gravity.CENTER);
                charViews[c].setTextColor(Color.WHITE);
                charViews[c].setBackgroundResource(R.drawable.letter_bg);
                //add to layout
                wordLayout.addView(charViews[c]);
            }
            ltrAdapt = new LetterAdapter(this);
            letters.setAdapter(ltrAdapt);
            for (int p = 0; p < numParts; p++) {
                bodyParts[p].setVisibility(View.INVISIBLE);
            }
        }
    }

    public void letterPressed(final View view) {
        String ltr = (((TextView) view).getText().toString()).toLowerCase();
        char letterChar = ltr.charAt(0);
        view.setEnabled(false);
        boolean guessedRight = false;
        view.setBackgroundResource(R.drawable.letter_down);
        for (int k = 0; k < currWord.length(); k++) {
            if (currWord.charAt(k) == letterChar) {
                numCorr++;
                charViews[k].setTextColor(Color.BLACK);
                guessedRight = true;
            }
        }
        if (guessedRight) {
            if (numCorr == currWord.length()) {
                //user has won
                if (!multiplayer) {
                    // Disable Buttons
                    disableBtns();

                    // Display Alert Dialog
                    AlertDialog.Builder winBuild = new AlertDialog.Builder(this);

                    winBuild.setTitle("You Win!");
                    winBuild.setMessage("\nYour score increased to " + ++score + "\nThe answer was: " + currWord + "\n");
                    winBuild.setPositiveButton("Play Again",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(view.getContext(), Tic_Tac_Toe_Activity.class);
                                    startActivity(intent);
                                    Hangman_Activity.this.playGame();
                                }
                            });
                    String message = "Score: " + score;
                    textView.setText(message);
                    winBuild.setNegativeButton("Exit",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Hangman_Activity.this.finish();
                                }
                            });

                    winBuild.show();
                } else {
                    //multiplayer
                    //user got a word
                    numWordsGuessed++;
                    playGame();
                }
            }
        } else if (currPart < numParts) {
            //some guesses left
            if (currPart != 1) {
                bodyParts[currPart].setVisibility(View.VISIBLE);
            }
            currPart++;
            if (multiplayer) {
                totalNumWrongGuesses++;
            }
        } else {
            if (!multiplayer) {
                //user has lost
                disableBtns();

                // Display Alert Dialog
                AlertDialog.Builder loseBuild = new AlertDialog.Builder(this);
                loseBuild.setTitle("You lose!");
                loseBuild.setMessage("Score decreased to " + --score + "\nThe answer was: " + currWord + "\n");
                loseBuild.setPositiveButton("Play Again",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(view.getContext(), Tic_Tac_Toe_Activity.class);
                                startActivity(intent);
                                Hangman_Activity.this.playGame();
                            }
                        });
                String message = "Score: " + score;
                textView.setText(message);
                loseBuild.setNegativeButton("Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Hangman_Activity.this.finish();
                            }
                        });
                loseBuild.show();
            } else {
                //multiplayer
                //user has lost
                playGame();
            }
        }
    }

    public void disableBtns() {
        int numLetters = letters.getChildCount();
        for (int l = 0; l < numLetters; l++) {
            letters.getChildAt(l).setEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (multiplayer) {
            run = true;
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