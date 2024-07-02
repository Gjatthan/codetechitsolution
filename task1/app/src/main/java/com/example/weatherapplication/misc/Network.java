package com.example.weatherapplication.misc;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Network {
    static public boolean haveNetworkConnection(ConnectivityManager cm) {
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI")||ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    return true;
        }
        return false;
    }
}
