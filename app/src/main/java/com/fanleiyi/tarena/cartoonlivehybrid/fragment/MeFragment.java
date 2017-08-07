package com.fanleiyi.tarena.cartoonlivehybrid.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.fanleiyi.tarena.cartoonlivehybrid.R;
import com.fanleiyi.tarena.cartoonlivehybrid.activitys.UserInfoActivity;
import com.fanleiyi.tarena.cartoonlivehybrid.activitys.UserInfoActivity_;
import com.fanleiyi.tarena.cartoonlivehybrid.app.MyApp;
import com.fanleiyi.tarena.cartoonlivehybrid.biz.UpdateBiz;
import com.fanleiyi.tarena.cartoonlivehybrid.entity.VersionEntity;
import com.fanleiyi.tarena.cartoonlivehybrid.util.ExceptionUtil;
import com.fanleiyi.tarena.cartoonlivehybrid.util.Tools;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import net.youmi.android.AdManager;
import net.youmi.android.offers.OffersManager;

import org.androidannotations.annotations.ViewById;

import java.io.File;

/**
 * Created by tarena on 2017/7/25.
 */

public class MeFragment extends Fragment{
    View view;
    Button btn;
    Button btn_update;
    Button btn_recommend;
    Button btn_me;
    public static final int MSG_SHOW_INFO=1;
    public static final int MSG_INSTALL_APK=2;
    public static final int MSG_ERROR=3;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try {
                Bundle bundle=msg.getData();
                int what=msg.what;
                switch (what){
                    case MSG_INSTALL_APK:
                        String apkPath=bundle.getString("apkPath");
                        File file=new File(apkPath);
                        Uri uri=Uri.fromFile(file);
                        Intent intent=new Intent(Intent.ACTION_VIEW);
                        String type="application/vnd.android.package-archive";
                        intent.setDataAndType(uri,type);
                        startActivity(intent);
                        break;

                    case MSG_ERROR:
                        Toast.makeText(getActivity(), "升级失败", Toast.LENGTH_SHORT).show();
                        break;
                    case MSG_SHOW_INFO:
                        String currentVersion= Tools.getVersion(getActivity());
                        final VersionEntity versionEntity= (VersionEntity) bundle.getSerializable("data");
                        if (Double.parseDouble(versionEntity.getVersion())>Double.parseDouble(currentVersion)) {
                            // 当前版本不是最新的
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                            dialog.setMessage(versionEntity.getVersion() + "\n" + versionEntity.getChangeLog())
                                    .setPositiveButton("升级", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            try {
                                                UpdateBiz.downloadApk(handler,versionEntity.getApkUrl());

                                            }catch (Exception e){
                                                ExceptionUtil.handleException(e);
                                            }
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .show();
                            Log.i("TAG", "data: " + versionEntity.toString());
                        }
                        break;
                }
            }catch (Exception e){
                ExceptionUtil.handleException(e);
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_me,container,false);
        btn = (Button) view.findViewById(R.id.btn_me_exit);
        btn_update = (Button) view.findViewById(R.id.btn_me_update);
        btn_recommend= (Button) view.findViewById(R.id.btn_me_recommend);
        btn_me= (Button) view.findViewById(R.id.btn_me);
        addListener();
        return view;
    }

    private void addListener() {
        btn_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserInfoActivity_.class));
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //退出环信
                EMClient.getInstance().logout(true, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        Log.i("main", "onSuccess: 退出登录成功！");
                    }
                    @Override
                    public void onProgress(int progress, String status) {
                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.i("main", "onSuccess: 退出失败");
                    }
                });
                MyApp.getInstance().exit();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 判断是不是移动网络
                if (MyApp.NET_IS_WIFI) {
                    UpdateBiz.getVersionInfo(handler, "admin");
                }else if (MyApp.NET_IS_MOBILE){
                    // 只开了移动网络
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setMessage("当前处于移动网络，继续下载可能会产生流量费用，是否继续？")
                            .setPositiveButton("打开wifi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setNegativeButton("继续升级", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    UpdateBiz.getVersionInfo(handler, "admin");
                                }
                            }).show();
                }
            }
        });

        btn_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // 显示积分墙，
                    //显示在一个新的activity中，
                    //activity在jar
                    // youmi sdk 有一个activity,.xml,联网，解析，显示
                    AdManager.getInstance(getActivity()).init(
                            "4648c5afc044b64e", "d73d40f04826354b", false);
                    // 如果使用积分广告，请务必调用积分广告的初始化接口:
                    OffersManager.getInstance(getActivity())
                            .onAppLaunch();
                    OffersManager.getInstance(getActivity())
                            .showOffersWall();

                } catch (Exception e) {
                    ExceptionUtil.handleException(e);
                }
            }
        });
    }
}
