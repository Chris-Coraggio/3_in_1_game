package app.a3_in_1_game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import static app.a3_in_1_game.Hangman.getRandomWord;
import static app.a3_in_1_game.Hangman.currentWord;
import static app.a3_in_1_game.Tic_Tac_Toe_Activity.textView;

public class Hangman_Activity extends AppCompatActivity {
    public InputStream inputStream;
    private String[] words;
    private Random rand;
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
    //number of characters in current word
    private int numChars;
    //number correctly guessed
    private int numCorr;
    private LetterAdapter ltrAdapt;
    int score = 0;

    private String winner;
    private Thread thread;
    private String url = MySingleton.url;
    private Context context;
    private boolean run;
    private String host = MySingleton.connect_4_host;
    private String user;
    private int numErrors;


    public Hangman_Activity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_);
        Resources res = getResources();
        words = res.getStringArray(R.array.words);


        wordLayout = (LinearLayout) findViewById(R.id.word);
        letters = (GridView) findViewById(R.id.letters);

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

        playGame();

    }


    protected void update() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String req = url + "/hangman/" + host + "/" + user;
                System.err.println(req);
                while (winner == null && run) {
                    JsonObjectRequest jsonObjectRequest =
                            new JsonObjectRequest(Request.Method.GET, req, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        System.err.println("RESPONSE: " + response.toString());
                                        winner = response.getString("winner");
                                        if (winner != null) {
                                            ((Hangman_Activity) context).runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    //display who won
                                                }
                                            });
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
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    protected void post(int col) {
        String req = url + "/hangman/" + host + "/" + user + "/" + score + "/" + numErrors;
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
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setTag(this);
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


    public void getRandomWord() throws IOException {
        inputStream = getAssets().open("HangmanWordList.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        int numLinesInFile = 0;
        String line;


        while ((line = bufferedReader.readLine()) != null) {
            numLinesInFile++;
        }


        //start the buffer back out at the top of the file

        inputStream = getAssets().open("HangmanWordList.txt");
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


        //extracts a random line as the word
        Random r = new Random();
        int randomLine = r.nextInt(numLinesInFile - 0) + 0;
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


    }

    public void letterPressed(View view) {

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
        if (guessedRight == true) {
            if (numCorr == currWord.length()) {
                //user has won
                // Disable Buttons
                disableBtns();

                // Display Alert Dialog
                AlertDialog.Builder winBuild = new AlertDialog.Builder(this);

                winBuild.setTitle("You Win!");
                winBuild.setMessage("\nYour score increased to " + ++score + "\nThe answer was: " + currWord + "\n");
                winBuild.setPositiveButton("Play Again",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
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
            }
        } else if (currPart < numParts) {
            //some guesses left
            bodyParts[currPart].setVisibility(View.VISIBLE);
            currPart++;
        } else {
            //user has lost
            disableBtns();

            // Display Alert Dialog
            AlertDialog.Builder loseBuild = new AlertDialog.Builder(this);
            loseBuild.setTitle("You lose!");
            loseBuild.setMessage("Score decreased to " + --score + "\nThe answer was: " + currWord + "\n");
            loseBuild.setPositiveButton("Play Again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
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

        }

    }

    public void disableBtns() {
        int numLetters = letters.getChildCount();
        for (int l = 0; l < numLetters; l++) {
            letters.getChildAt(l).setEnabled(false);
        }
    }
}