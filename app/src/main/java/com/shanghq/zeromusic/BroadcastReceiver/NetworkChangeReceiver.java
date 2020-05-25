package com.shanghq.zeromusic.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private NetworkChangeListener listener;

    public void setListener(NetworkChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network[] networks=connectivityManager.getAllNetworks();
        for(int i=0;i<networks.length;i++){
            NetworkInfo networkInfo=connectivityManager.getNetworkInfo(networks[i]);
            if(networkInfo.getState()==NetworkInfo.State.CONNECTED){
                listener.onChangeListener();
                return;
            }
        }
    }

    public interface NetworkChangeListener{
        void onChangeListener();
    }
}
