package com.fanleiyi.tarena.cartoonlivehybrid.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by tarena on 2017/7/26.
 */

public class Tools {

    public static String getVersion(Context context) throws Exception {
        PackageManager packageManager=context.getPackageManager();
        String packageName=context.getPackageName();
        PackageInfo packageInfo=packageManager.getPackageInfo(packageName,0);
        return packageInfo.versionName;
    }

}
