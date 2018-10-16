package com.example.vmac.myrobot;

import com.example.vmac.myrobot.util.NumberUtil;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        int lenth=Integer.MAX_VALUE;
        byte[] bytes=new byte[4];
        bytes[0]=(byte)(lenth&0xFF);
        bytes[1]=(byte)((lenth>>8)&0xFF);
        bytes[2]=(byte)((lenth>>16)&0xFF);
        bytes[3]=(byte)((lenth>>24)&0xFF);
        System.out.println(lenth);
        System.out.println(bytes);
        System.out.println(bytes);
        int re=NumberUtil.byte4ToInt2(bytes,0);
        System.out.println(re);
        int m=lenth/(1024*8*1024);
        System.out.println(m+" M");

        int G=lenth/(1024*1024*8*1024);
        System.out.println(G+" G");
    }
}