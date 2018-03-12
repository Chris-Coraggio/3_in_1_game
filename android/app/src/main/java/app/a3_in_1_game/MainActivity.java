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
                final String user = nameText.getText().toString();
                editor.putString("user", user);
                editor.commit();
                // Display Alert Dialog
                final AlertDialog.Builder winBuild = new AlertDialog.Builder(MainActivity.this);
                winBuild.setTitle("Singleplayer or Multiplayer?");
                winBuild.setPositiveButton("Multiplayer",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                winBuild.setNegativeButton("Host",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                MySingleton.connect_4_host = user;
                                                String req = MySingleton.url + "/connect_4_host/" + user;
                                                StringRequest stringRequest = new StringRequest(Request.Method.GET, req,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                new Thread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        final boolean[] run = {true};
                                                                        while (run[0]) {
                                                                            String req = MySingleton.url + "/connect_4/" + user + "/" + user;
                                                                            JsonObjectRequest jsonObjectRequest =
                                                                                    new JsonObjectRequest(Request.Method.GET, req, null, new Response.Listener<JSONObject>() {
                                                                                        @Override
                                                                                        public void onResponse(JSONObject response) {
                                                                                            try {
                                                                                                if (response.getString("client") != null) {
                                                                                                    MySingleton.connect_4_multiplayer = true;
                                                                                                    Intent intent = new Intent(view.getContext(), Connect_4_Acitivity.class);
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
                                                                                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
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
                                                        }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                stringRequest.setTag(this);
                                                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
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
                                                        // Do nothing.
                                                        MySingleton.connect_4_host = input.getText().toString();
                                                        String req = MySingleton.url + "/connect_4_join/" + MySingleton.connect_4_host + "/" + user;
                                                        StringRequest stringRequest = new StringRequest(Request.Method.GET, req,
                                                                new Response.Listener<String>() {
                                                                    @Override
                                                                    public void onResponse(String response) {
                                                                        if (response.equals("Game joined!")) {
                                                                            MySingleton.connect_4_multiplayer = true;
                                                                            Intent intent = new Intent(view.getContext(), Connect_4_Acitivity.class);
                                                                            startActivity(intent);
                                                                        } else {
                                                                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                }, new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {
                                                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                        stringRequest.setTag(this);
                                                        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                                                    }
                                                })
                                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                        // Do nothing.
                                                        MySingleton.connect_4_multiplayer = false;
                                                    }
                                                }).show();
                                    }
                                });

                                winBuild.show();

                            }
                        });
                winBuild.setNegativeButton("Singleplayer",
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
            public void onClick(final View view) {
                //Intent intent = new Intent(view.getContext(), Hangman_Activity.class);
                //startActivity(intent);
                SharedPreferences.Editor editor = sharedPref.edit();
                final String user = nameText.getText().toString();
                editor.putString("user", user);
                editor.commit();
                // Display Alert Dialog
                final AlertDialog.Builder winBuild = new AlertDialog.Builder(MainActivity.this);
                winBuild.setTitle("Singleplayer or Multiplayer?");
                winBuild.setPositiveButton("Multiplayer",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                winBuild.setTitle("Host or Join?");
                                winBuild.setNegativeButton("Host",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                MySingleton.hangman_host = user;
                                                String req = MySingleton.url + "/hangman_host/" + user;
                                                StringRequest stringRequest = new StringRequest(Request.Method.GET, req,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                new Thread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        final boolean[] run = {true};
                                                                        while (run[0]) {
                                                                            String req = MySingleton.url + "/hangman/" + user + "/" + user; //since user is host
                                                                            JsonObjectRequest jsonObjectRequest =
                                                                                    new JsonObjectRequest(Request.Method.GET, req, null, new Response.Listener<JSONObject>() {
                                                                                        @Override
                                                                                        public void onResponse(JSONObject response) {
                                                                                            try {
                                                                                                if (response.getString("client") != null) {
                                                                                                    MySingleton.hangman_multiplayer = true;
                                                                                                    Intent intent = new Intent(view.getContext(), Hangman_Activity.class);
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
                                                                                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
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
                                                        }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                stringRequest.setTag(this);
                                                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
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
                                                        // Do nothing.
                                                        MySingleton.hangman_host = input.getText().toString();
                                                        String req = MySingleton.url + "/hangman_join/" + MySingleton.hangman_host + "/" + user;
                                                        StringRequest stringRequest = new StringRequest(Request.Method.GET, req,
                                                                new Response.Listener<String>() {
                                                                    @Override
                                                                    public void onResponse(String response) {
                                                                        if (response.equals("Game joined!")) {
                                                                            MySingleton.hangman_multiplayer = true;
                                                                            Intent intent = new Intent(view.getContext(), Hangman_Activity.class);
                                                                            startActivity(intent);
                                                                        } else {
                                                                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                }, new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {
                                                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                        stringRequest.setTag(this);
                                                        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                                                    }
                                                })
                                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                        // Do nothing.
                                                        MySingleton.hangman_multiplayer = false;
                                                    }
                                                }).show();
                                    }
                                });

                                winBuild.show();

                            }
                        });
                winBuild.setNegativeButton("Singleplayer",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(view.getContext(), Hangman_Activity.class);
                                startActivity(intent);
                            }
                        });


                winBuild.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MySingleton.connect_4_multiplayer = false;
    }
}
