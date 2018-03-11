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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    protected void join(final View view, final String game, final String user) {
        String req;
        switch (game) {
            case "Tic Tac Toe":
                req = MySingleton.url + "/tic_tac_toe_join/" + MySingleton.tic_tac_toe_host + "/" + user;
                break;
            case "Connect 4":
                req = MySingleton.url + "/connect_4_join/" + MySingleton.connect_4_host + "/" + user;
                break;
//            TODO: HANGMAN JOIN
//            case "Hangman":
//                req = MySingleton.url + "/hangman_join/" + MySingleton.hangman_host + "/" + user;
//                break;
            default:
                Toast.makeText(getApplicationContext(), "Wrong game entered in join", Toast.LENGTH_SHORT).show();
                return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, req, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Game joined!")) {
                    Intent intent;
                    switch (game) {
                        case "Tic Tac Toe":
                            MySingleton.tic_tac_toe_multiplayer = true;
                            intent = new Intent(view.getContext(), Tic_Tac_Toe_Activity.class);
                            break;
                        case "Connect 4":
                            MySingleton.connect_4_multiplayer = true;
                            intent = new Intent(view.getContext(), Connect_4_Acitivity.class);
                            break;
                        case "Hangman":
//                                    TODO: HANGMAN
//                                    MySingleton.hangman_multiplayer = true;
                            intent = new Intent(view.getContext(), Hangman_Activity.class);
                            break;
                        default:
                            Toast.makeText(getApplicationContext(),
                                    "Wrong game entered in createDialog - Singleplayer",
                                    Toast.LENGTH_SHORT).show();
                            return;
                    }
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "(join) " + error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setTag(this);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    protected void waitForJoin(final View view, final String game, final String user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final boolean[] run = {true};
                while (run[0]) {

                    String req;
                    switch (game) {
                        case "Tic Tac Toe":
                            req = MySingleton.url + "/tic_tac_toe/" + user + "/" + user;
                            break;
                        case "Connect 4":
                            req = MySingleton.url + "/connect_4/" + user + "/" + user;
                            break;
                        case "Hangman":
                            req = MySingleton.url + "/hangman/" + user + "/" + user;
                            break;
                        default:
                            Toast.makeText(getApplicationContext(),
                                    "Wrong game entered in host", Toast.LENGTH_SHORT).show();
                            return;
                    }

                    JsonObjectRequest jsonObjectRequest =
                            new JsonObjectRequest(Request.Method.GET, req,
                                    null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        if (response.getString("client") != null) {
                                            Intent intent;
                                            switch (game) {
                                                case "Tic Tac Toe":
                                                    MySingleton.tic_tac_toe_multiplayer = true;
                                                    intent = new Intent(view.getContext(),
                                                            Tic_Tac_Toe_Activity.class);
                                                    break;
                                                case "Connect 4":
                                                    MySingleton.connect_4_multiplayer = true;
                                                    intent = new Intent(view.getContext(),
                                                            Connect_4_Acitivity.class);
                                                    break;
                                                case "Hangman":
//                                    TODO: HANGMAN
//                                    MySingleton.hangman_multiplayer = true;
                                                    intent = new Intent(view.getContext(),
                                                            Hangman_Activity.class);
                                                    break;
                                                default:
                                                    Toast.makeText(getApplicationContext(),
                                                            "Wrong game entered in waitForJoin",
                                                            Toast.LENGTH_SHORT).show();
                                                    return;
                                            }
                                            startActivity(intent);
                                            run[0] = false;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(),
                                            "(waitForJoin) " + error.toString(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                    jsonObjectRequest.setTag(this);
                    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    protected void host(final View view, final String game, final String user) {
        String req;
        switch (game) {
            case "Tic Tac Toe":
                MySingleton.tic_tac_toe_host = user;
                req = MySingleton.url + "/tic_tac_toe_host/" + user;
                break;
            case "Connect 4":
                MySingleton.connect_4_host = user;
                req = MySingleton.url + "/connect_4_host/" + user;
                break;
            case "Hangman":
                MySingleton.tic_tac_toe_host = user;
                req = MySingleton.url + "/hangman_host/" + user;
                break;
            default:
                Toast.makeText(getApplicationContext(), "Wrong game entered in host", Toast.LENGTH_SHORT).show();
                return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, req, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                waitForJoin(view, game, user);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "(Host) " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setTag(this);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    protected void createDialog(final View view, final String game, final String user) {
        // Display Alert Dialog
        final AlertDialog.Builder winBuild = new AlertDialog.Builder(MainActivity.this);
        winBuild.setTitle("Singleplayer or Multiplayer?");

        // Singleplayer
        winBuild.setNegativeButton("Singleplayer",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent;
                        switch (game) {
                            case "Tic Tac Toe":
                                intent = new Intent(view.getContext(), Tic_Tac_Toe_Activity.class);
                                break;
                            case "Connect 4":
                                intent = new Intent(view.getContext(), Connect_4_Acitivity.class);
                                break;
                            case "Hangman":
                                intent = new Intent(view.getContext(), Hangman_Activity.class);
                                break;
                            default:
                                Toast.makeText(getApplicationContext(),
                                        "Wrong game entered in createDialog - Singleplayer",
                                        Toast.LENGTH_SHORT).show();
                                return;
                        }
                        startActivity(intent);
                    }
                });

        // Multiplayer
        winBuild.setPositiveButton("Multiplayer",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        winBuild.setNegativeButton("Host",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Host game
                                        host(view, game, user);
                                    }
                                });
                        winBuild.setPositiveButton("Join", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final EditText input = new EditText(MainActivity.this);
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("Enter Friends's Username")
                                        .setView(input)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                // Connect to host
                                                switch (game) {
                                                    case "Tic Tac Toe":
                                                        MySingleton.tic_tac_toe_host = input.getText().toString();
                                                        join(view, "Tic Tac Toe", user);
                                                        break;
                                                    case "Connect 4":
                                                        MySingleton.connect_4_host = input.getText().toString();
                                                        join(view, "Connect 4", user);
                                                        break;
//                                                    TODO: HANGMAN
//                                                    case "Hangman":
//                                                        MySingleton.hangman_host = input.getText().toString();
//                                                        join(view, "Hangman", user);
//                                                        break;
                                                    default:
                                                        Toast.makeText(getApplicationContext(),
                                                                "Wrong game entered in createDialog - Host"
                                                                , Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        })
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                // Do nothing.
                                                MySingleton.tic_tac_toe_multiplayer = false;
                                            }
                                        }).show();
                            }
                        });
                        winBuild.show();
                    }
                });
        winBuild.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText nameText = (EditText) findViewById(R.id.nickname);
        final SharedPreferences sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        String user = sharedPref.getString("user", "");
        nameText.setText(user);


        // Tic Tac Toe button
        Button tic_tac_toe_launch = (Button) findViewById(R.id.buttonTicTacToe);
        tic_tac_toe_launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                SharedPreferences.Editor editor = sharedPref.edit();
                final String user = nameText.getText().toString();
                editor.putString("user", user);
                editor.commit();

                createDialog(view, "Tic Tac Toe", user);
            }
        });

        //Connect 4 button
        Button connect_4_launch = (Button) findViewById(R.id.connect_4_launch);
        connect_4_launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                SharedPreferences.Editor editor = sharedPref.edit();
                final String user = nameText.getText().toString();
                editor.putString("user", user);
                editor.commit();

                createDialog(view, "Connect 4", user);
            }
        });

        // Hangman button
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

    @Override
    protected void onResume() {
        super.onResume();
        MySingleton.connect_4_multiplayer = false;
        MySingleton.tic_tac_toe_multiplayer = false;
//        TODO: HANGMAN
//        MySingleton.hangman_multiplayer = false;
    }
}
