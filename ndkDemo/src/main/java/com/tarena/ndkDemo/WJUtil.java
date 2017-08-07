package com.tarena.ndkDemo;

/**
 * Created by tarena on 2017/7/27.
 */

public class WJUtil {
    static {
        System.loadLibrary("hello-jni");
    }
    public native void showWJ();
}
