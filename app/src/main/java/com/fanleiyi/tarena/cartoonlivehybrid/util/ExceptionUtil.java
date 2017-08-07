package com.fanleiyi.tarena.cartoonlivehybrid.util;

import android.os.Build;
import android.util.Log;

import com.fanleiyi.tarena.cartoonlivehybrid.app.MyApp;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by tarena on 2017/7/25.
 */

public class ExceptionUtil {
    public static void handleException(Throwable throwable){
        // 把异常信息变成字符串
        if (MyApp.isRelease) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            throwable.printStackTrace(printWriter);
            String string = stringWriter.toString();
            // 得手机厂商
            string+= Build.MANUFACTURER;
            // 得手机型号
            string+=Build.MODEL;
            // 得手机操作系统版本
            string+=Build.VERSION.SDK_INT;
            Log.i("TAG", "handleException: "+string);


        }else {
            throwable.printStackTrace();
        }
    }
}
