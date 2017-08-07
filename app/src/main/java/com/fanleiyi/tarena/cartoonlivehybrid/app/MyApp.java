package com.fanleiyi.tarena.cartoonlivehybrid.app;

import android.app.Activity;
import android.app.Application;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Process;
import android.util.Xml;

import com.fanleiyi.tarena.cartoonlivehybrid.R;
import com.fanleiyi.tarena.cartoonlivehybrid.UrlFactory;
import com.fanleiyi.tarena.cartoonlivehybrid.bean.MyUser;
import com.fanleiyi.tarena.cartoonlivehybrid.util.CrashHandler;
import com.fanleiyi.tarena.cartoonlivehybrid.util.NetworkUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

/**
 * Created by tarena on 2017/7/25.
 */

public class MyApp extends Application {
    public static boolean NET_IS_CLOSE=false;
    public static boolean NET_IS_WIFI=false;
    public static boolean NET_IS_MOBILE=false;
    //true 发布了 false:开发中
    public  static  boolean isRelease=true;
    private static MyApp instance;
    private static ArrayList<Activity> activityList=new ArrayList<>();
    public static MyUser user;

    public static void addActivity(Activity activity)
    {
        activityList.add(activity);
    }
    public void removeActivity(Activity activity)
    {
        activityList.remove(activity);
    }
    public  void exit()
    {
        for(Activity activity:activityList)
        {
            activity.finish();
        }

        Process.killProcess(Process.myPid());
    }

    public  static MyApp getInstance()
    {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        readConfig();
        //初始化xutils
        x.Ext.init(this);
        //CrashHandler crashHandler=new CrashHandler();
        //Thread.setDefaultUncaughtExceptionHandler(crashHandler);
        NetworkUtil.getNetworkType(this);

        Bmob.initialize(this, "ca5251b1f36598fad1d753137bf4970a");

        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);

//初始化
        EMClient.getInstance().init(this, options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

    }

    private void readConfig() {
        try{
            Resources res=getResources();
            XmlResourceParser xmlResourceParser= res.getXml(R.xml.config);
            int eventType=xmlResourceParser.getEventType();
            while(eventType!=XmlResourceParser.END_DOCUMENT)
            {
                if (eventType==XmlResourceParser.START_TAG)
                {
                    String tagName=xmlResourceParser.getName();
                    if ("webServer".equals(tagName))
                    {
                        UrlFactory.server=xmlResourceParser.nextText();
                        return;
                    }
                }
                eventType=xmlResourceParser.next();
            }
        }catch (Exception e)
        {e.printStackTrace();}
    }
}
