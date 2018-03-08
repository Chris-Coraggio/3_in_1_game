package app.a3_in_1_game;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonTicTacToe = (Button) findViewById(R.id.buttonTicTacToe);
        SharedPreferences sharedPref= getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        EditText nameText = (EditText) findViewById(R.id.nickname);
        String userName =sharedPref.getString("user_id","");
        nameText.setText(userName);

        buttonTicTacToe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Tic_Tac_Toe_Activity.class);
                startActivity(intent);

                SharedPreferences sharedPref= getSharedPreferences("myPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor;
                EditText nameText = (EditText) findViewById(R.id.nickname);
                String name = nameText.getText().toString();
                editor = sharedPref.edit();
                editor.putString("user_id", name);
                editor.commit();
                nameText.setText(name);
            }
        });

        Button connect_4_launch = (Button) findViewById(R.id.connect_4_launch);
        connect_4_launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Connect_4_Acitivity.class);
                startActivity(intent);

                SharedPreferences sharedPref= getSharedPreferences("myPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor;
                EditText nameText = (EditText) findViewById(R.id.nickname);
                String name = nameText.getText().toString();
                editor = sharedPref.edit();
                editor.putString("user_id", name);
                editor.commit();
                nameText.setText(name);
            }
        });
        Button hangman_launch = (Button) findViewById(R.id.hangman_launch);
        hangman_launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Hangman_Activity.class);
                startActivity(intent);

                SharedPreferences sharedPref= getSharedPreferences("myPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor;
                EditText nameText = (EditText) findViewById(R.id.nickname);
                 String name = nameText.getText().toString();
                    editor = sharedPref.edit();
                    editor.putString("user_id", name);
                    editor.commit();
                    nameText.setText(name);
            }
        });



    }
}
