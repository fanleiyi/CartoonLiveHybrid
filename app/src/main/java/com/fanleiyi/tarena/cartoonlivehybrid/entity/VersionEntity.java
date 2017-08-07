package com.fanleiyi.tarena.cartoonlivehybrid.entity;

import java.io.Serializable;

/**
 * Created by tarena on 2017/7/26.
 */

public class VersionEntity implements Serializable{

    public static float serialVersionUID=1;

    @Override
    public String toString() {
        return "VersionEntity{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", version='" + version + '\'' +
                ", changeLog='" + changeLog + '\'' +
                ", apkUrl='" + apkUrl + '\'' +
                '}';
    }

    /**
     * status : 0
     * msg : 成功
     * version : 9.0
     * changeLog : 增加了二维码扫描功能 修改了某些机型登录失败问题
     * apkUrl : http://172.60.50.156:8080/allRunServer/app-debug.apk
     */

    private String status;
    private String msg;
    private String version;
    private String changeLog;
    private String apkUrl;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }
}
