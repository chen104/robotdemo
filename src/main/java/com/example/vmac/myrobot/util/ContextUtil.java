package com.example.vmac.myrobot.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.vmac.myrobot.app.WatsonApplication;

/**
 * Created by Administrator on 2017/12/14.
 */

public class ContextUtil {
    /**
     * send action id
     * @param result
     */
    public static  void sentActionID(Context context , String result,String TAG){
        Intent intent = new Intent();
        intent.setAction("com.unisrobot.u05.robotapi.client.action");// com.unisrobot.u05.robotapi.action
        intent.putExtra("data", "{\"msgType\":"+8420+",\"msgData\":\""+result+"\"}");
        context.sendBroadcast(intent);
        Log.i(TAG," ****发送广播 action =com.unisrobot.u05.robotapi.action "+" data = {\"msgType\":8420,\"msgData\":\""+result+"\"}");

    }




    /**
     * 显示界面广播
     * @param result
     * @param msgType
     */
    public static  void sentDataToShow(Context context , String result, int msgType,String TAG){
        Intent intent = new Intent();
        intent.setAction("com.unisrobot.u05.robotapi.client.action");// com.unisrobot.u05.robotapi.action
        intent.putExtra("data", "{\"msgType\":"+msgType+",\"msgData\":\""+result+"\"}");
        context.sendBroadcast(intent);
        Log.i(TAG," ****发送广播 action =com.unisrobot.u05.robotapi.action "+" data = {\"msgType\":9000,\"msgData\":\""+result+"\"}");

    }


    /**
     * 发送语音长度广播，
     * @param context
     * @param length
     * @param TAG
     */
    public static  void sentActionTime(Context context , int length,String TAG){
        Intent intent = new Intent();
        intent.setAction("com.unisrobot.u05.robotapi.client.action");// com.unisrobot.u05.robotapi.action
        intent.putExtra("data", "{\"msgType\":"+9002+",\"msgData\":\""+length+"\"}");
        context.sendBroadcast(intent);
        Log.i(TAG," ****发送广播 action =com.unisrobot.u05.robotapi.action "+" data = {\"msgType\":9002,\"msgData\":\""+length+"\"}");

    }

    /**
     * Check Internet Connection
     * @return
     */
    public static boolean checkInternetConnection(Context context,String TAG) {
        // get Connectivity Manager object to check connection
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        // Check for network connections
        if (isConnected){
            return true;
        }
        else {
            Log.e(TAG,"***网络不可用");
            return false;
        }

    }


    /**
     * 发送滴一下广播
     */
    public static void sendBeginBroadcast(Context context,String TAG){
        Intent intent=new Intent();
        intent.setAction("com.action.receive.msgtype");
        //头部开始录音
        intent.putExtra("msgType", 10000);
        context.sendBroadcast(intent);
        Log.i(WatsonApplication.tag,TAG+"*** 发送滴滴广播");

    }


}
