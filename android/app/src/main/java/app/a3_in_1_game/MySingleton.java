package app.a3_in_1_game;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by hamza on 5/29/2017.
 */

public class MySingleton {
//    static final String url = "http://10.0.2.2:8080";
    static final String url = "http://192.168.0.20:8080";
//    static final String url = "https://server-3in1.herokuapp.com";

    static String tic_tac_toe_host;
    static boolean tic_tac_toe_multiplayer;
    static String connect_4_host;
    static boolean connect_4_multiplayer;
    static String hangman_host;
    static boolean hangman_multiplayer;
    private static MySingleton mInstance;
    private static Context mCtx;
    private RequestQueue mRequestQueue;


    private MySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
