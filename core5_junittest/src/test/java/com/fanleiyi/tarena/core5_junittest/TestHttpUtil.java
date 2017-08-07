package com.fanleiyi.tarena.core5_junittest;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by tarena on 2017/8/2.
 */

public class TestHttpUtil extends TestCase {

    public void testGet(){
        HttpUtil httpUtil=new HttpUtil();
        byte[] result=httpUtil.get("www.tedu.cn");
        boolean isSuccess=false;
        if (result!=null&&result.length>=4){
            isSuccess=true;
        }
        Assert.assertEquals(true,isSuccess);
    }
}
