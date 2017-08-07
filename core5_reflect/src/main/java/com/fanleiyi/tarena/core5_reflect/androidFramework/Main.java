package com.fanleiyi.tarena.core5_reflect.androidFramework;


/**
 * Created by pjy on 2017/8/2.
 */

public class Main {
    public void onCreate()
    {
        //创建对象的两种方式
        //1, new 类名
        //2,通过类名字符串创建对象

       // MyApplication myApplication=new MyApplication();
        //解析manifest.xml,假如得到MyApplication
        String className="com.fanleiyi.tarena.core5_reflect.project.MyApplication";
        try
        {
            Class<Applictation> c= (Class<Applictation>)
                    Class.forName(className);
            Applictation app=c.newInstance();
            app.onCreate();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
