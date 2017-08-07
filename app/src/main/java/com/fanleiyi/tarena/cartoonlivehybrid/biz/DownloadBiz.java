package com.fanleiyi.tarena.cartoonlivehybrid.biz;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.fanleiyi.tarena.cartoonlivehybrid.UrlFactory;
import com.fanleiyi.tarena.cartoonlivehybrid.entity.ImageInfoEntity;
import com.fanleiyi.tarena.cartoonlivehybrid.parser.ImageInfoParser;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.body.StringBody;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by tarena on 2017/7/25.
 */

public class DownloadBiz {

    public static void getImage(final Context context, final String cartoonId, final String chapterId){
        // 1.拼出url
        String url= UrlFactory.getImageInfoUrl();
        // 2.准备发送数据
        url=url+"cartoonId="+cartoonId+"&chapterId="+chapterId;
        // 3.发送
        RequestParams params=new RequestParams(url);
        // 4.回调
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                Log.i("TAG", "onSuccess: "+result);
                ImageInfoEntity imageInfoEntity= ImageInfoParser.parser(result);
                    downloadImage(context,cartoonId,chapterId,imageInfoEntity);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("TAG", "onError: "+ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    public static void downloadImage(final Context context, String cartoonId, String chapterId, final ImageInfoEntity entity){
        // cartoon/1/1/0001.png
        // cartoon/1/1/0002.png
        // cartoon/1/2
        // cartoon/2/1
        // 创建子文件夹
        String sdcardRoot= Environment.getExternalStorageDirectory().getAbsolutePath();
        String directory=sdcardRoot+"/cartoon/"+cartoonId+"/"+chapterId;
        File file=new File(directory);
        if (!file.exists()){
            file.mkdirs();
        }
        final ArrayList<String> downloadFilelist=new ArrayList<>();
        // for
        for (int i = 0; i < entity.getData().size(); i++) {
            String imageSrc=entity.getData().get(i).getImageSrc();
            // 得文件名
            int index =imageSrc.lastIndexOf("/");
            String fileName=imageSrc.substring(index+1);
            // 拼出完整路径（path）
            String path=directory+"/"+fileName;
            // 联网
            String url=UrlFactory.server+imageSrc;
            RequestParams params=new RequestParams(url);
            params.setSaveFilePath(path);
            x.http().get(params, new Callback.CommonCallback<File>() {
                        @Override
                        public void onSuccess(File result) {
                            downloadFilelist.add(result.getAbsolutePath());
                            Log.i("TAG", "onSuccess: "+"ok");
                            // 判断图片是否下载完毕，完成后发广播
                            if (downloadFilelist.size()==entity.getData().size()){
                                Intent intent=new Intent("downloadOver");
                                // 发广播带数据
                                intent.putExtra("list",downloadFilelist);
                                context.sendBroadcast(intent);
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            Log.i("TAG", "onError: "+ex.toString());
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    }
            );

        }



    }

}
