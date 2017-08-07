package com.fanleiyi.tarena.cartoonlivehybrid.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.fanleiyi.tarena.cartoonlivehybrid.app.MyApp;
import com.fanleiyi.tarena.cartoonlivehybrid.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarena on 2017/7/26.
 */

public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //多态：父类对象在运行时指向不同的子类的对象
        MyApp.getInstance().addActivity(this);
        //第一个界面不做检测网络
        if (!(this instanceof MainActivity_)) {
            NetworkUtil.checkNetworkState(this);
        }
    }

    @Override
    protected void onDestroy() {
        MyApp.getInstance().removeActivity(this);
        super.onDestroy();
    }
}
