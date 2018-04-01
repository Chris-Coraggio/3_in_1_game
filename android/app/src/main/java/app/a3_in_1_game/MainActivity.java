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

    protected boolean unique;
    protected boolean[] run;

    protected void join(final View view, final String game, final String user) {
        String req;
        switch (game) {
            case "Tic Tac Toe":
                req = MySingleton.url + "/tic_tac_toe_join/" + MySingleton.tic_tac_toe_host + "/" + user;
                break;
            case "Connect 4":
                req = MySingleton.url + "/connect_4_join/" + MySingleton.connect_4_host + "/" + user;
                break;
            case "Hangman":
                req = MySingleton.url + "/hangman_join/" + MySingleton.hangman_host + "/" + user;
                break;
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
                            MySingleton.hangman_multiplayer = true;
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
                run[0] = true;
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
                                                    MySingleton.hangman_multiplayer = true;
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
                MySingleton.hangman_host = user;
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
        String text = "Singleplayer";
        if (game.equals("Hangman")) {
            text = "Multiplayer";
        }
        winBuild.setNegativeButton(text,
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
        text = "Multiplayer";
        if (game.equals("Hangman")) {
            text = "Singleplayer";
        }
        winBuild.setPositiveButton(text,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String text = "Host";
                        if (game.equals("Hangman")) {
                            text = "Join";
                        }
                        winBuild.setNegativeButton(text,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Host game
                                        host(view, game, user);
                                    }
                                });
                        text = "Join";
                        if (game.equals("Hangman")) {
                            text = "Host";
                        }
                        winBuild.setPositiveButton(text, new DialogInterface.OnClickListener() {
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
                                                        MySingleton.tic_tac_toe_host = "deleteMe";
                                                        join(view, "Tic Tac Toe", user);
                                                        break;
                                                    case "Connect 4":
                                                        MySingleton.connect_4_host = input.getText().toString();
                                                        join(view, "Connect 4", user);
                                                        break;
                                                    case "Hangman":
                                                        MySingleton.hangman_host = input.getText().toString();
                                                        join(view, "Hangman", user);
                                                        break;
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

    protected void setUsername(final View view, final String game, final String user) {
        String req = MySingleton.url + "/set_username/" + user;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, req, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Username added!")) {
                    unique = true;
                    createDialog(view, game, user);
                } else {
                    unique = false;
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "(setUsername) " + error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setTag(this);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    protected void setAndDeleteUsername(final View view, final String game,
                                        final String user, final String old_user) {
        String req = MySingleton.url + "/set_and_delete_username/" + old_user + "-" + user;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, req, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Username added!")) {
                    unique = true;
                    createDialog(view, game, user);
                } else {
                    unique = false;
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "(setAndDeleteUsername) " + error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setTag(this);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText nameText = (EditText) findViewById(R.id.nickname);
        final SharedPreferences sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        final String[] user = {sharedPref.getString("user", "")};
        nameText.setText(user[0]);
        final String[] old_user = new String[1];
        unique = true;
        run = new boolean[1];

        // Tic Tac Toe button
        Button tic_tac_toe_launch = (Button) findViewById(R.id.hangman_launch);
        tic_tac_toe_launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                old_user[0] = user[0];
                user[0] = nameText.getText().toString();
                if (user[0].length() < 4 || user[0].length() > 14) {
                    Toast.makeText(getApplicationContext(), "Username must be between 4 and 15 characters long!", Toast.LENGTH_SHORT).show();
                    nameText.setText(old_user[0]);
                    user[0] = old_user[0];
                    return;
                } else if (!isValid(user[0].toUpperCase()) && !isAlpha(user[0])) {
                    Toast.makeText(getApplicationContext(), "Username must be only letters and numbers!", Toast.LENGTH_SHORT).show();
                    nameText.setText(old_user[0]);
                    user[0] = old_user[0];
                    return;
                }

                run[0] = false;
                if (old_user[0].equals(user[0])) {
                    if (!unique) {
                        //check again from server
                        setUsername(view, "Tic Tac Toe", user[0]);
                    } else {
                        createDialog(view, "Tic Tac Toe", user[0]);
                    }
                } else {
                    setAndDeleteUsername(view, "Tic Tac Toe", user[0], old_user[0]);
                }
            }
        });

        //Connect 4 button
        Button connect_4_launch = (Button) findViewById(R.id.connect_4_launch);
        connect_4_launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                old_user[0] = user[0];
                user[0] = nameText.getText().toString();
                if (user[0].length() < 4 || user[0].length() > 14) {
                    Toast.makeText(getApplicationContext(), "Username must be between 4 and 15 characters long!", Toast.LENGTH_SHORT).show();
                    nameText.setText(old_user[0]);
                    user[0] = old_user[0];
                    return;
                } else if (!isValid(user[0].toUpperCase()) && !isAlpha(user[0])) {
                    Toast.makeText(getApplicationContext(), "Username must be only letters and numbers!", Toast.LENGTH_SHORT).show();
                    nameText.setText(old_user[0]);
                    user[0] = old_user[0];
                    return;
                }

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("user", user[0]);
                editor.commit();

                run[0] = false;
                if (old_user[0].equals(user[0])) {
                    if (!unique) {
                        //check again from server
                        setUsername(view, "Connect 4", user[0]);
                    } else {
                        createDialog(view, "Connect 4", user[0]);
                    }
                } else {
                    setAndDeleteUsername(view, "Connect 4", user[0], old_user[0]);
                }
            }
        });

        // Hangman button
        Button hangman_launch = (Button) findViewById(R.id.buttonTicTacToe);
        hangman_launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                old_user[0] = user[0];
                user[0] = nameText.getText().toString();
                if (user[0].length() < 4 || user[0].length() > 14) {
                    Toast.makeText(getApplicationContext(), "Username must be between 4 and 15 characters long!", Toast.LENGTH_SHORT).show();
                    nameText.setText(old_user[0]);
                    user[0] = old_user[0];
                    return;
                } else if (!isValid(user[0].toUpperCase()) && !isAlpha(user[0])) {
                    Toast.makeText(getApplicationContext(), "Username must be only letters and numbers!", Toast.LENGTH_SHORT).show();
                    nameText.setText(old_user[0]);
                    user[0] = old_user[0];
                    return;
                }

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("user", user[0]);
                editor.commit();

                run[0] = false;
                if (old_user[0].equals(user[0])) {
                    if (!unique) {
                        //check again from server
                        setUsername(view, "Hangman", user[0]);
                    } else {
                        createDialog(view, "Hangman", user[0]);
                    }
                } else {
                    setAndDeleteUsername(view, "Hangman", user[0], old_user[0]);
                }
            }
        });
    }

    public boolean isValid(String s) {
        return s.matches("[a-zA-Z0-9]*");
    }

    public boolean isAlpha(String s) {
        return s.matches("[a-zA-Z]+");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MySingleton.connect_4_multiplayer = false;
        MySingleton.tic_tac_toe_multiplayer = false;
        MySingleton.hangman_multiplayer = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        run[0] = false;
    }
}
