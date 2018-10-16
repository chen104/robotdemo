package com.example.vmac.myrobot.watson;


import android.util.Log;

import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.RecognizeCallback;

/**
 * Created by Administrator on 2017/12/8.
 */

public class DefaultRecognizeCallBack  implements RecognizeCallback {
    public static String TAG=DefaultRecognizeCallBack.class.getSimpleName();

    @Override
    public void onTranscription(SpeechResults speechResults) {

    }

    @Override
    public void onConnected() {
        Log.i(TAG,"连接");
    }

    @Override
    public void onError(Exception e) {
        Log.i(TAG,TAG+"***发生异常"+e.getMessage());
    }

    @Override
    public void onDisconnected() {
        Log.i(TAG,TAG+"***断开连接");
    }

    @Override
    public void onInactivityTimeout(RuntimeException runtimeException) {
        Log.i(TAG,TAG+"***发生异常 "+runtimeException.getMessage());
    }

    @Override
    public void onListening() {
        Log.i(TAG,TAG+"***监听中");
    }

    @Override
    public void onTranscriptionComplete() {
        Log.i(TAG,"解析完成");
    }
}
