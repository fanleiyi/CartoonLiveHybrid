package com.fanleiyi.tarena.cartoonlivehybrid.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.os.Process;
import android.widget.Toast;

import com.fanleiyi.tarena.cartoonlivehybrid.activitys.MainActivity_;
import com.fanleiyi.tarena.cartoonlivehybrid.app.MyApp;

/**
 * Created by tarena on 2017/7/26.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    /**
     * 出了异常没加try时执行
     * @param thread
     * @param throwable
     */
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        // 把信息发给服务器
        ExceptionUtil.handleException(throwable);
        // 提示用户程序会自动重启
        new Thread(){
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                Toast.makeText(MyApp.getInstance(), "程序即将重启", Toast.LENGTH_LONG).show();
                Looper.loop();// loop后面的代码不执行
            }
        }.start();

        // 自动重启
        Intent intent=new Intent(MyApp.getInstance(), MainActivity_.class);
            PendingIntent pendingintent=PendingIntent.getActivity(MyApp.getInstance(),100,intent,0);

            // 定时器
            AlarmManager alarmManager= (AlarmManager) MyApp.getInstance().getSystemService(Context.ALARM_SERVICE);
            // RTC:锁屏，不执行pendingintent
            alarmManager.set(AlarmManager.RTC,System.currentTimeMillis()+2000,pendingintent);

            try{
                Thread.currentThread().sleep(1500);
        }catch (Exception e){}
        // 结束当前进程
        Process.killProcess(Process.myPid());
    }
}
