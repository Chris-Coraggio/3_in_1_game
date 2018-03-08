package app.a3_in_1_game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
        final EditText nameText = (EditText) findViewById(R.id.nickname);
        final SharedPreferences sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        String user = sharedPref.getString("user", "");
        nameText.setText(user);

        buttonTicTacToe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPref.edit();
                String user = nameText.getText().toString();
                editor.putString("user", user);
                editor.commit();

                Intent intent = new Intent(view.getContext(), Tic_Tac_Toe_Activity.class);
                startActivity(intent);
            }
        });

        Button connect_4_launch = (Button) findViewById(R.id.connect_4_launch);
        connect_4_launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPref.edit();
                String user = nameText.getText().toString();
                editor.putString("user", user);
                editor.commit();

                Intent intent = new Intent(view.getContext(), Connect_4_Acitivity.class);
                startActivity(intent);
            }
        });
        Button hangman_launch = (Button) findViewById(R.id.hangman_launch);
        hangman_launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPref.edit();
                String user = nameText.getText().toString();
                editor.putString("user", user);
                editor.commit();

                Intent intent = new Intent(view.getContext(), Hangman_Activity.class);
                startActivity(intent);
            }
        });


    }
}
