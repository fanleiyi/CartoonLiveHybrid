package com.fanleiyi.tarena.core5_reflect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.fanleiyi.tarena.core5_reflect.androidFramework.Main;
import com.fanleiyi.tarena.core5_reflect.test.Test1;
import com.fanleiyi.tarena.core5_reflect.test.Test2;
import com.fanleiyi.tarena.core5_reflect.test.Test3;
import com.fanleiyi.tarena.core5_reflect.test.Test4;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Main main=new Main();
//        main.onCreate();
        //runTest3();
        setField("111");
    }

    public void runTest1()
    {
        try {
            String className="com.fanleiyi.tarena.core5_reflect.test.Test1";
            Class<?> c=Class.forName(className);
            Object object=c.newInstance();
            Method[] methods=c.getMethods();
            for (int i=0;i<methods.length;i++)
            {
                Method method=methods[i];
                String methodName=method.getName();
                if (methodName.contains("test")){
                    method.invoke(object);
                }
            }


        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void runTest2()
    {
        try {
            String[] classNames={"com.fanleiyi.tarena.core5_reflect.test.Test1",
                    "com.fanleiyi.tarena.core5_reflect.test.Test2","com.fanleiyi.tarena.core5_reflect.test.Test3"};
            for (int k=0;k<classNames.length;k++)
            {
                Class<?> c=Class.forName(classNames[k]);
                Object object=c.newInstance();
                Method[] methods=c.getMethods();
                for (int i=0;i<methods.length;i++)
                {
                    Method method=methods[i];
                    String methodName=method.getName();
                    if (methodName.contains("test")){
                        method.invoke(object);
                    }
                }
            }



        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void runTest3()
    {
        try {
            //startActivity(Login.class)
            Class[] classNames={Test1.class, Test2.class, Test3.class};
            for (int k=0;k<classNames.length;k++)
            {
                //Class类的对象创建对象，调用方法
                //Class类的对象放的是类的包名，类名，方法，属性名
                Class<?> c=classNames[k];
                Object object=c.newInstance();
                Method[] methods=c.getMethods();
                for (int i=0;i<methods.length;i++)
                {
                    Method method=methods[i];
                    String methodName=method.getName();
                    if (methodName.contains("test")){
                        method.invoke(object);
                    }
                }
            }



        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void getField()
    {
        try{
            Class<Test1> c= (Class<Test1>) Class.forName("com.fanleiyi.tarena.core5_reflect.test.Test1");
            Field[] fields=c.getFields();
            for (Field field:fields)
            {
                String fileName=field.getName();
                Log.i("","");
            }

        }catch (Exception e)
        {}

    }
    public void setField(String fidle){
        try {
            Test4 test4=new Test4();
            Class<?> c = test4.getClass();
            Field filed = c.getDeclaredField("username");
            filed.setAccessible(true);
            filed.set(test4, fidle);
            String username = test4.getUsername();

            Log.i("", "");
        } catch (Exception e)
        {}
    }

}
