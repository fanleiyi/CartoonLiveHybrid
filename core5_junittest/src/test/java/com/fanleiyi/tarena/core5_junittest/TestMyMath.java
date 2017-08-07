package com.fanleiyi.tarena.core5_junittest;

import junit.framework.TestCase;

import org.junit.Assert;

/**
 * Created by tarena on 2017/8/2.
 */

public class TestMyMath extends TestCase {

    public void testAdd()
    {
        MyMath myMath=new MyMath();
        int result=myMath.add(1,1);
        //assert断言 判断
        Assert.assertEquals(2,result);
    }

}
