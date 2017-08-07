package com.fanleiyi.tarena.cartoonlivehybrid.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fanleiyi.tarena.cartoonlivehybrid.util.ExceptionUtil;
import com.fanleiyi.tarena.cartoonlivehybrid.util.NetworkUtil;

public class NetworkStateChangedReceiver extends BroadcastReceiver {
    public NetworkStateChangedReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
      try{
          NetworkUtil.getNetworkType(context);
      }catch (Exception e){
          ExceptionUtil.handleException(e);
      }
    }
}
