package com.projectattitude.projectattitude.Objects;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Vuk on 3/11/2017.
 * Checks if the user is connected and online. Used in the login activity to log in. no longer used
 * after chris's fix, this won't work on emulator, but works on phones.
 */

public class NetWorkUtil {
    public static int CONNECTED = 1;
    public static int NOT_CONNECTED = 0;

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return CONNECTED;
        }
        return NOT_CONNECTED;
    }
}
