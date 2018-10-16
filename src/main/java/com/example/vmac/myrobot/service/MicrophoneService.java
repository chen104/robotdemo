package com.example.vmac.myrobot.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import com.example.vmac.myrobot.app.MsgType;
import com.example.vmac.myrobot.app.WatsonApplication;
import com.example.vmac.myrobot.util.ContextUtil;
import com.example.vmac.myrobot.watson.WatsonClient;
import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneInputStream;
import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.Transcript;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.RecognizeCallback;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import javax.net.ssl.SSLException;

/**
 *
 */
public class MicrophoneService extends Service   implements RecognizeCallback {
    private IBinder iBinder;

    /**
     * 麦克风输入流
     */
    MicrophoneInputStream microphoneInputStream;
    WatsonClient watsonClient;
    WatsonApplication watsonApplication;
    private String TAG=MicrophoneService.class.getSimpleName();
    RecognizeOptions  options;
    private boolean isfeeBack=false;
    private  long entTime;
    private long startTime;
    RecognizeOptions   optionsOgg;
    private boolean isStop=false;
    private Handler handler;
    public void setHandler(Handler handler){
        this.handler =handler;
    }
    public MicrophoneService() {
    }
    private class MyBinder extends Binder {
        MicrophoneService getService(){
            return  MicrophoneService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        return iBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        iBinder =new MyBinder();
        watsonApplication =(WatsonApplication) getApplication();
        watsonClient=watsonApplication.getWatsonClient();
                Log.i(TAG,"***创建服务");
        /**
         * stt 参数初始化
         */
        options = new RecognizeOptions.Builder()
                .interimResults(true)
                .inactivityTimeout(5) // use this to stop listening when the speaker pauses, i.e. for 5s
                .contentType(HttpMediaType.AUDIO_PCM+ "; rate=" + 16000)
                //.interimResults(true)
                //.model("")
                .build();

           optionsOgg=new RecognizeOptions.Builder()
                  .interimResults(true)
                .contentType("audio/ogg;codec=opus")
                .build();

        Intent intentService=new Intent(this, StartService.class);
        stopService(intentService);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int re =   super.onStartCommand(intent, flags, startId);
        Log.i(TAG,"***开始服务");
        boolean isconnectNetwork = ContextUtil.checkInternetConnection(this,TAG);
        while(!isconnectNetwork){

            ContextUtil.sentDataToShow(this,"网络不可用",9000,TAG);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isconnectNetwork = ContextUtil.checkInternetConnection(this,TAG);
        }

        if(isfeeBack){
            Log.i(TAG,"***正在运行,isFeeBack 不在重启录音");
        }else {
            Log.i(TAG,"开始录音服务服务");
           startInputsteam();
        }
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                int i=0;
//                while(i<100){
//                    Log.i(TAG,"***打印服务 "+i);
//                    i++;
//                    try {
//                        Thread.sleep(5000);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.start();
        return re;

    }

    @Override
    public void onDestroy() {
        isStop=true;
        super.onDestroy();
        closeInputstream();
        Log.i(TAG,"***销毁服务");
    }

    /**
     * 开始录音
     */
    private void startInputsteam(){
       closeInputstream();
       if(isStop){
           return;
       }

        Log.i(TAG,"***开始录音");
       sendStatBoracast("录音中…………");
        microphoneInputStream =new MicrophoneInputStream(true);
        startTime =System.currentTimeMillis();
        watsonClient.speechToText(microphoneInputStream,optionsOgg,this);
    }

    private void closeInputstream(){

        if(microphoneInputStream!=null){
            try {
                microphoneInputStream.close();
                Log.i(TAG,"****停止录音");
                sendStatBoracast("停止录音");
            } catch (IOException e) {
                Log.e(TAG,"关闭录音报错");
                e.printStackTrace();
            }
        }
    }


    /**
     * c处理识别结果
     * @param speechResults
     */
    @Override
    public void onTranscription(SpeechResults speechResults) {
        if(isStop){
            return;
        }
//        StringBuilder stringBuilder=new StringBuilder();
        String item="";
        if(speechResults.getResults() != null && !speechResults.getResults().isEmpty()) {
            for(Transcript t: speechResults.getResults()) {

                item=  t.getAlternatives().get(0).getTranscript();
                final String str=item;
                Log.i(TAG,"****接收到数据 Final is "+t.isFinal() +" "+item);
                if(t.isFinal()&&!isfeeBack){
                    isfeeBack=true;
                    entTime=System.currentTimeMillis();
                    Log.i(TAG,"**** speechToText 计算时间 "+(entTime-startTime)+" ms ");
                    closeInputstream();
                    sendMesage(str);

                    return;

                }
                //ContextUtil.sentDataToShow(this,item, MsgType.CUSTOM,this.getClass());
//                stringBuilder.append(item);
            }
//
            entTime=System.currentTimeMillis();
//            Log.i(TAG,"***speechTotext识别结果"+stringBuilder.toString());
//            Log.i(TAG,"***speechTotext 所用时间 "+(entTime-startTime));
//            sendMesage(stringBuilder.toString());
        }


    }
    public void sendMesage(String text){
        ContextUtil.sentDataToShow(this,text, MsgType.CUSTOM,TAG);

        if(TextUtils.isEmpty(text)){
            watsonClient.textToSpeech("No question Specified");

        }else{
            startTime=System.currentTimeMillis();
            List<String> list=  watsonClient.sendMessage(text);
            entTime=System.currentTimeMillis();
            Log.i(TAG,"*** conversation 所用时间 "+(entTime-startTime));
            if(list.isEmpty()){
                watsonClient.textToSpeech("No question Specified");
            }else{
                for(String str:list){
                    startTime=System.currentTimeMillis();
                    ContextUtil.sentDataToShow(this,str, MsgType.MACHINE,TAG);
                    final  String play=str;

                    watsonClient.textToSpeech(play);


                    entTime=System.currentTimeMillis();
                    Log.i(TAG,"*** textToSpeech 所用时间 "+(entTime-startTime)+" "+str);
                }//for playStream
//                isfeeBack=false;
            }//if paly
        }//is emty

        isfeeBack=false;
    }

    /**
     * 连接
     */
    @Override
    public void onConnected() {
        Log.i(TAG,"*** onConnected");


    }

    /**
     * 正在出错了
     * @param e
     */
    @Override
    public void onError(Exception e) {
        Log.i(TAG,"*** onError "+e.getMessage());
        closeInputstream();
        if(e instanceof  RuntimeException){//408 Request Time-out: Session timed out.
            ContextUtil.sentDataToShow(this,"会话超时，408 Request Time-out: Session timed out.",9000,TAG);
//            startInputsteam();
        }if(e instanceof UnknownHostException){
            ContextUtil.sentDataToShow(this,"网络不通，检查网络",9000,TAG);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }if(e instanceof SSLException){ // javax.net.ssl.SSLException: Write error: ssl=0x7fa8e57bc0: I/O error during system call, Broken pipe
            ContextUtil.sentDataToShow(this,"管道异常",9000,TAG);
        }

    }


    /**
     * 断开连接
     */
    @Override
    public void onDisconnected() {
        Log.i(TAG,"*** onDisconnected ");
       startInputsteam();
    }

    /**
     * 运行错误
     * @param runtimeException
     */
    @Override
    public void onInactivityTimeout(RuntimeException runtimeException) {
        Log.i(TAG,"*** runtimeException "+runtimeException.getMessage());

    }

    /**
     * 监听中
     */
    @Override
    public void onListening() {
        Log.i(TAG,"*** onListening ");

    }

    @Override
    public void onTranscriptionComplete() {
        Log.i(TAG,"*** 识别传输完成 ");

    }

    private void sendStatBoracast(String stat){
        Intent intent = new Intent();
        intent.setAction("com.unisrobot.u05.robotapi.client.action");// com.unisrobot.u05.robotapi.action
        intent.putExtra("msgtype",3);
        intent.putExtra("data", stat);
         this.sendBroadcast(intent);
        Log.i(TAG," ****发送广播  状态 "+stat);

    }

}
