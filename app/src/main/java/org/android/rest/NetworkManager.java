package org.android.rest;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class NetworkManager {
    private static final String TAG = "NetworkManager";
    private static NetworkManager instance = null;
    public static final String prefixURL = RestUrl.BASE;
    public static int MOCK_SERVER = 1;
    public static int REAL_SERVER = 2;


    //for Volley API
    public RequestQueue requestQueue;

    private NetworkManager(Context context, int ServerType) {
        if (ServerType == REAL_SERVER) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        } else {
            requestQueue = new FakeRequestQueue(context.getApplicationContext());
            requestQueue.start();
        }
    }

    public static synchronized NetworkManager getInstance(Context context, int ServerType) {
        if (null == instance)
            instance = new NetworkManager(context, ServerType);
        return instance;
    }

    //this is so you don't need to pass context each time
    public static synchronized NetworkManager getInstance() {
        if (null == instance) {
            throw new IllegalStateException(NetworkManager.class.getSimpleName() + " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

}