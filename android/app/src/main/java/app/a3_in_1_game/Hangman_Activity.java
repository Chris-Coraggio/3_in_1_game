package app.a3_in_1_game;

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

import java.util.Random;

public class Hangman_Activity extends AppCompatActivity {
    private String[] words;
    private Random rand;
    private String currWord = "";
    private LinearLayout wordLayout;
    private TextView[] charViews;
    private GridView letters;
    // private LetterAdapter ltrAdapt;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_);
        Resources res = getResources();
        words = res.getStringArray(R.array.words);
        rand = new Random();
        currWord = "hello";//change this later on!
        wordLayout = (LinearLayout) findViewById(R.id.word);
        letters = (GridView) findViewById(R.id.letters);

        bodyParts = new ImageView[numParts];
        bodyParts[0] = (ImageView) findViewById(R.id.head);
        bodyParts[1] = (ImageView) findViewById(R.id.body);
        bodyParts[2] = (ImageView) findViewById(R.id.arm1);
        bodyParts[3] = (ImageView) findViewById(R.id.arm2);
        bodyParts[4] = (ImageView) findViewById(R.id.leg1);
        bodyParts[5] = (ImageView) findViewById(R.id.leg2);
        for (int p = 0; p < numParts; p++) {
            bodyParts[p].setVisibility(View.INVISIBLE);//use this to show body parts
        }
        playGame();
    }

    public void playGame() {
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
    }

    public void letterPressed(View view) {

        String ltr=(((TextView)view).getText().toString()).toLowerCase();
        char letterChar = ltr.charAt(0);
        System.out.println("letter: " + letterChar + currWord);
        view.setEnabled(false);
        boolean guessedRight = false;
        view.setBackgroundResource(R.drawable.letter_down);
        for(int k = 0; k < currWord.length(); k++) {
            if (currWord.charAt(k)==letterChar){
                numCorr++;
                charViews[k].setTextColor(Color.BLACK);
                guessedRight = true;
            }
        }
        if (guessedRight == false){
            bodyParts[currPart].setVisibility(View.VISIBLE);
            currPart++;
        }
    }
}