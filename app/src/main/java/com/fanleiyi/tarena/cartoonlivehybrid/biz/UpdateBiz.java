package com.fanleiyi.tarena.cartoonlivehybrid.biz;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.fanleiyi.tarena.cartoonlivehybrid.UrlFactory;
import com.fanleiyi.tarena.cartoonlivehybrid.entity.VersionEntity;
import com.fanleiyi.tarena.cartoonlivehybrid.fragment.MeFragment;
import com.fanleiyi.tarena.cartoonlivehybrid.parser.UpdateParser;
import com.fanleiyi.tarena.cartoonlivehybrid.util.ExceptionUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * Created by tarena on 2017/7/26.
 */

public class UpdateBiz {

    public static void downloadApk(final Handler handler,final String url){
        RequestParams requestParams=new RequestParams(url);
        // 创建文件夹
        String root= Environment.getExternalStorageDirectory().getAbsolutePath();
        String directory=root+"/cartoon/apk";
        File file=new File(directory);
        if (!file.exists()){
            file.mkdir();
        }
        // 拼出文件名
        int index=url.lastIndexOf("/");
        String fileName=url.substring(index+1);
        final String apkPath=directory+"/"+fileName;
        requestParams.setSaveFilePath(apkPath);
        x.http().get(requestParams, new Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                Message msg=handler.obtainMessage();
                msg.what=MeFragment.MSG_INSTALL_APK;
                Bundle bundle=new Bundle();
                bundle.putString("apkPath",apkPath);
                msg.setData(bundle);
                handler.sendMessage(msg);
                Log.i("TAG", "onSuccess: "+"下载成功");

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Message msg=handler.obtainMessage();
                msg.what=MeFragment.MSG_ERROR;
                handler.sendMessage(msg);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    public static void getVersionInfo(final Handler handler,String username){
        String url = "http://172.60.25.97:8080/allRunServer/apkUpdate.jsp?"+"username="+username;
        RequestParams params=new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {



            @Override
            public void onSuccess(String result) {
                VersionEntity versionEntity;
                try {
                    versionEntity = UpdateParser.parser(result);
                    Message message=handler.obtainMessage();
                    message.what= MeFragment.MSG_SHOW_INFO;
                    // 带数据有两种方式
                    // 1.obj只能带1个
                    // 2.推荐第二种：bundle可以带多个
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("data",versionEntity);
                    message.setData(bundle);
                    handler.sendMessage(message);
                }catch (Exception e){
                    ExceptionUtil.handleException(e);
                    Message message=handler.obtainMessage();
                    message.what= MeFragment.MSG_ERROR;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Message message=handler.obtainMessage();
                message.what= MeFragment.MSG_ERROR;
                handler.sendMessage(message);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}
