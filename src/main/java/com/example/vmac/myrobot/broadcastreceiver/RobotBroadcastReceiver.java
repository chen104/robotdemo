package com.example.vmac.myrobot.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.util.Log;

import com.example.vmac.myrobot.service.MicrophoneService;
import com.example.vmac.myrobot.service.SocketRecognizeWebSocketService;


/**
 * Created by Administrator on 2017/11/24.
 */

public class RobotBroadcastReceiver extends BroadcastReceiver {
    public static String actionKey="serviceAction";
    public static int startService=1;
    public static int stopService=2;
    private String tag=RobotBroadcastReceiver.class.getSimpleName();


    @Override
    public void onReceive(Context context, Intent intent) {
        int action=intent.getIntExtra(actionKey,0);
        String msgType=intent.getStringExtra("msgType");
//        Intent intentService=new Intent(context, SocketRecognizeWebSocketService.class);
//        Intent intentService=new Intent(context, RecordService.class);
        Intent intentService=new Intent(context, MicrophoneService.class);
        if(action==startService){
            context.startService(intentService);
            Log.i(tag,"收到广播,开始服务");
        }else if(action==stopService){
            context.stopService(intentService);
            Log.i(tag,"收到广播,停止服务");
        }
//        if("start".equals(msgType)){
//            context.startService(intentService);
//            Log.i(tag,"***收到广播,调用服务");
//        }

    }

}
