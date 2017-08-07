package com.fanleiyi.tarena.core5_reflect.test;

/**
 * Created by pjy on 2017/8/2.
 */

public class Test4 {
    public String getUsername() {
        return username;
    }

    //用反射能访问private
    private  String username;

}
