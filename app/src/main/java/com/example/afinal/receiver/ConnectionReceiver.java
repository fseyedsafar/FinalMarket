package com.example.afinal.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

public class ConnectionReceiver extends BroadcastReceiver {

    public static final String BROAD_INTERNET = "broadInternet";

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isAvalable = manager.getActiveNetworkInfo() != null;
        boolean isConnected = isAvalable && manager.getActiveNetworkInfo().isConnected();
//
        if (isConnected){
            Toast.makeText(context, "internet", Toast.LENGTH_LONG).show();
//            context.startActivity(LoadActivity.newIntent(context, isConnected));
        }

//        Log.d(BROAD_INTERNET, "internet");
    }
}
