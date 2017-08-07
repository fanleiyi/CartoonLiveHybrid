package com.fanleiyi.tarena.cartoonlivehybrid.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

import com.fanleiyi.tarena.cartoonlivehybrid.app.MyApp;

/**
 * Created by tarena on 2017/7/26.
 */

public class NetworkUtil {

        public static  void checkNetworkState(final Context context)
        {
            try{
                //判断有没有网络
                ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo=manager.getActiveNetworkInfo();
                if (networkInfo==null) {
                    //没网打开网络设置界面
                    AlertDialog.Builder dialog=new AlertDialog.Builder(context);
                    dialog.setMessage("无网络连接，是否开启网络。");
                    dialog.setPositiveButton("打开网络", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent=new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                            context.startActivity(intent);
                        }
                    });
                    dialog.setNegativeButton("取消",null);
                    dialog.show();
                }

            }catch (Exception e)
            {
                ExceptionUtil.handleException(e);
            }

        }

        public static void getNetworkType(Context context){
            // wifi | mobile
            try {
                ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo==null){
                    Log.i("TAG", "getNetworkType：无网络连接");
                    MyApp.NET_IS_CLOSE=true;
                    MyApp.NET_IS_MOBILE=false;
                    MyApp.NET_IS_WIFI=false;
                }else {
                    NetworkInfo wifiNetworkInfo=manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    if (wifiNetworkInfo!=null&&wifiNetworkInfo.isConnected()){
                        Log.i("TAG", "getNetworkType: wifi已连接");
                        MyApp.NET_IS_CLOSE=false;
                        MyApp.NET_IS_MOBILE=false;
                        MyApp.NET_IS_WIFI=true;
                    }
                    NetworkInfo mobileNetworkInfo=manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    if (mobileNetworkInfo!=null&&mobileNetworkInfo.isConnected()){
                        Log.i("TAG", "getNetworkType: mobile已连接");
                        MyApp.NET_IS_CLOSE=false;
                        MyApp.NET_IS_MOBILE=true;
                        MyApp.NET_IS_WIFI=false;
                    }
                }
            }catch (Exception e){
                ExceptionUtil.handleException(e);
            }
        }


}
