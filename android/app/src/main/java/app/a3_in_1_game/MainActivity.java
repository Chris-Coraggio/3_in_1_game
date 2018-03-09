package app.a3_in_1_game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static app.a3_in_1_game.Tic_Tac_Toe_Activity.textView;

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
            public void onClick(final View view) {
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
            public void onClick(final View view) {
                SharedPreferences.Editor editor = sharedPref.edit();
                String user = nameText.getText().toString();
                editor.putString("user", user);
                editor.commit();
                // Display Alert Dialog
                final AlertDialog.Builder winBuild = new AlertDialog.Builder(MainActivity.this);
                winBuild.setTitle("Singleplayer or Multiplayer?");
                winBuild.setPositiveButton("Multiplayer",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Intent intent = new Intent(view.getContext(), Tic_Tac_Toe_Activity.class);
                                //startActivity(intent);
                                winBuild.setNegativeButton("Host",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent intent = new Intent(view.getContext(), Connect_4_Acitivity.class);
                                                startActivity(intent);
                                            }
                                        });
                                winBuild.setPositiveButton("Join",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                final EditText input = new EditText(MainActivity.this);

                                                new AlertDialog.Builder(MainActivity.this)
                                                        .setTitle("Enter Friends's Username")
                                                        .setView(input)
                                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                                // Do nothing.
                                                            }
                                                        })
                                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                                // Do nothing.
                                                            }
                                                        }).show();                                          }
                                        });

                                winBuild.show();

                            }
                        });
                winBuild.setNegativeButton("Singplayer",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(view.getContext(), Connect_4_Acitivity.class);
                                startActivity(intent);
                            }
                        });


                winBuild.show();

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
