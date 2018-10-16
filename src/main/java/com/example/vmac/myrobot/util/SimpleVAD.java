package com.example.vmac.myrobot.util;

import android.view.View;

import java.util.Map;

/**
 * Created by Administrator on 2017/12/12.
 */

public class SimpleVAD extends Thread{

    public interface onExecuteListener {
        //切出一句话的数据
        void onFinsh(byte[] data);

    }
}
