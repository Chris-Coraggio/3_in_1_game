package app.a3_in_1_game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity {
    Button buttonTicTacToe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonTicTacToe = (Button) findViewById(R.id.buttonTicTacToe);

        buttonTicTacToe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), Tic_Tac_Toe_Activity.class);
                startActivity(intent);

            }

        });

    }
}
