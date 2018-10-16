package com.example.vmac.myrobot.service;



import android.app.Service;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.example.vmac.myrobot.app.MsgType;
import com.example.vmac.myrobot.app.WatsonApplication;
import com.example.vmac.myrobot.util.ContextUtil;
import com.example.vmac.myrobot.util.NumberUtil;
import com.example.vmac.myrobot.watson.WatsonClient;
import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.Transcript;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.RecognizeCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.net.ssl.SSLException;

/**
 * Created by Administrator on 2017/12/8.
 */

public class VoiceUDPService extends Service implements RecognizeCallback {
    private static  String actionId="actionID";
    private static final String TAG = VoiceUDPService.class.getSimpleName();
    private Binder iBinder;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
    WatsonClient watsonClient;
    WatsonApplication watsonApplication;
    File cachFile;
    DatagramSocket datagramSocket;
    private Boolean isRunReciveThread;
    private Boolean readyReciveData;
    RecognizeOptions options;
    PipedOutputStream pipedOutputStream;
    PipedInputStream pipedInputStream;
    InetAddress inetAddress;
    boolean isFishRead = false;
    long startTime;
    long entTime;
    int port = 3299;
    private boolean isListening = false;

    boolean registerSpeechToText = false;
    int silentTikip=0;

    //总是接收的线程
    /**
     * 控制接收线程
     */
    boolean isRunThread = true;
    ByteArrayOutputStream byteout = new ByteArrayOutputStream();


    private AllWayRun reciveThread;

    private class MyBinder extends Binder {
        VoiceUDPService getService() {
            return VoiceUDPService.this;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        iBinder = new MyBinder();
        Log.i(TAG, "***创建服务");
        watsonApplication = (WatsonApplication) getApplication();
        cachFile = watsonApplication.getCacheDir();
        watsonClient = watsonApplication.getWatsonClient();


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int re = super.onStartCommand(intent, flags, startId);
//       startReciveThread();
        Log.i(TAG, "***开始服务");
        startUDPThread();
        return re;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "***停止服务");
        super.onDestroy();
        stopUDPThread();
//        stopReciveThread();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }


    /**
     * c处理识别结果
     *
     * @param speechResults
     */
    @Override
    public void onTranscription(SpeechResults speechResults) {
        StringBuilder stringBuilder = new StringBuilder();
        String item = "";
        if (speechResults.getResults() != null && !speechResults.getResults().isEmpty()) {
            for (Transcript t : speechResults.getResults()) {
                item = t.getAlternatives().get(0).getTranscript();
                Log.i(TAG, "***speechTotext识别结果 "+t.isFinal()+"  " +   item);
                if(t.isFinal()){
                    stringBuilder.append(item);
                }
            }
//

        }
        entTime = System.currentTimeMillis();
        Log.i(TAG, "***speechTotext识别结果" + stringBuilder.toString());
        Log.i(TAG, "***speechTotext 所用时间 " + (entTime - startTime));
        sendMesage(stringBuilder.toString());


    }

    public void sendMesage(String text) {
        sentDataToShow(text, MsgType.CUSTOM);

        if (TextUtils.isEmpty(text)) {
            watsonClient.textToSpeech("No question Specified");
            silentTikip+=1;
            Log.i(TAG, "*** 没有识别到内容 " );
        } else {
            silentTikip=0;
            startTime = System.currentTimeMillis();
            List<String> list = watsonClient.sendMessage(text);

            entTime = System.currentTimeMillis();
            Log.i(TAG, "*** conversation 所用时间 " + (entTime - startTime));
            if (list.isEmpty()) {
                watsonClient.textToSpeech("No question Specified");
            } else {
                startTime = System.currentTimeMillis();
                for (String str : list) {

                    sentDataToShow(str, MsgType.MACHINE);
                    watsonClient.textToSpeech(str);
                    entTime = System.currentTimeMillis();
                    Log.i(TAG, "*** textToSpeech 所用时间 " + (entTime - startTime) + " " + str);
                }
            }
        }
        if(silentTikip<3){
            sendBeginBroadcast();

        }else{
            Log.i(TAG, "*** 没有说话，累积3次，不发滴滴" );
        }
        sendStartPacket();
    }

    /**
     * 连接
     */
    @Override
    public void onConnected() {
        Log.i(TAG, "*** onConnected");
        isListening = false;

    }

    /**
     * 正在出错了
     *
     * @param e
     */
    @Override
    public void onError(Exception e) {
        Log.i(TAG, "*** onError " + e.getMessage());
        if (e instanceof RuntimeException) {//408 Request Time-out: Session timed out.
           // sentDataToShow("会话超时，408 Request Time-out: Session timed out.", 9000);

        }
        if (e instanceof UnknownHostException) {
           // sentDataToShow("网络不通，检查网络", 9000);
            if(reciveThread!=null){
                stopUDPThread();
                startUDPThread();
            }

        }
        if (e instanceof SSLException) { // javax.net.ssl.SSLException: Write error: ssl=0x7fa8e57bc0: I/O error during system call, Broken pipe
           // sentDataToShow("管道异常", 9000);
        }
        // watsonClient.speechToText(pipedInputStream,options,this);

        isListening = false;
        registerSpeechToText = false;

        sendStartPacket();
    }


    /**
     * 断开连接
     */
    @Override
    public void onDisconnected() {
        Log.i(TAG, "*** onDisconnected ");
//        isListening=false;
//        registerSpeechToText=false;

        /**
         * 处理结束，开始下一轮
         */
        isFishRead = false;
        readyReciveData = true;
        registerSpeechToText = false;
        /**
         * 已结束处理并发送滴滴广播，开始录音
         */
        sendStartPacket();
    }

    /**
     * 运行错误
     *
     * @param runtimeException
     */
    @Override
    public void onInactivityTimeout(RuntimeException runtimeException) {
        Log.i(TAG, "*** runtimeException " + runtimeException.getMessage());
        isListening = false;
        registerSpeechToText = false;
        readyReciveData = true;
    }

    /**
     * 监听中
     */
    @Override
    public void onListening() {
        Log.i(TAG, "*** onListening ");
        isListening = true;
        readyReciveData = true;
        sendStartPacket();
//        registerSpeechToText=false;
    }

    @Override
    public void onTranscriptionComplete() {
        Log.i(TAG, "*** 识别传输完成 ");
//        isListening=false;
//        registerSpeechToText=false;
    }


    private void startUDPThread() {

        stopUDPThread();//避免重复开启线程
        reciveThread = new AllWayRun(this);
        reciveThread.start();
//        reciveThread.setPriority(18);

    }

    private void stopUDPThread() {
        if (reciveThread != null) {
            isRunThread = false;
            readyReciveData = false;
            isListening = false;
            reciveThread.interrupt();
        }
        if (datagramSocket != null) {
            if (!datagramSocket.isClosed()) {
                datagramSocket.close();
            }
        }
    }


    /**
     *
     */
    private class AllWayRun extends Thread {
        RecognizeCallback callback;

        public AllWayRun(RecognizeCallback RecognizeCallback) {
            callback = RecognizeCallback;
        }

        @Override
        public void run() {
            Log.e(TAG, "***开始线程");
            /**
             * 初始化线程网络参数
             */
            try {
                options = new RecognizeOptions.Builder()
                        .inactivityTimeout(5) // use this to stop listening when the speaker pauses, i.e. for 5s
                        .contentType(HttpMediaType.AUDIO_PCM + "; rate=" + 16000)
                        .build();
                //UDP socket
                datagramSocket = new DatagramSocket();
                inetAddress = InetAddress.getLocalHost();
//                byte[] ip=new byte[]{(byte)192,(byte)168,31,53};

//                byte[] ip=new byte[]{9,(byte)234,64,56};

                //     byte[] ip=new byte[]{(byte)192,(byte)168,5,(byte)122};
                // inetAddress=  InetAddress.getByAddress(ip);
                boolean isConnect  =ContextUtil.checkInternetConnection(VoiceUDPService.this, TAG);
                while(isRunThread&&(!isConnect)){
                    sentDataToShow("网络不可用", 9000);
                    isConnect  =ContextUtil.checkInternetConnection(VoiceUDPService.this, TAG);
                    try {
                        Thread.sleep(5000);
                    }catch ( Exception e){
                      Log.e(TAG,"线程网络不通 报错"+e.getMessage());
                    }

                }




            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "***ReciveThread 初始化 DatagramSocket 报错" + e.getMessage() + "重新启用服务");
                return;
            }
            /**
             *  初始化线程控制开始状态
             */

            isRunThread = true;
            isListening = false;
            readyReciveData = false;

//            sendBeginBroadcast();//发送滴滴广播
            sendStartPacket();//发送开始接收数据请求

            byte[] buffer = new byte[2000];
            try {
                Log.i(TAG,"***准备循环接收数据包 ");
                while (isRunThread) {
//                Log.i(TAG,"***循环接收数据包 ");
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    try {
                        datagramSocket.receive(packet);
                        Log.i(TAG, "***接收数据包 " + buffer[0] + " " + simpleDateFormat.format(new Date()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "***接收数据包错误");
                        continue;
                    }
                    /**
                     * 解析报数据，是否为语音数据，或结束数据
                     */
                    if (buffer[0] == 0) {//为语音数据，第一个字节为0 是数据
                        if (!registerSpeechToText) {//是否启动了websocket
                            try {
                                registerSpeechToText();
                            } catch (IOException e) {
                                Log.e(TAG, "***注册失败" + e.getMessage());
                                continue;

                            }
                        }

                        byte[] head = new byte[4];
                        System.arraycopy(buffer, 1, head, 0, 4);
                        int length = NumberUtil.byte4ToInt2(head, 0);
                        byte[] data = new byte[2000];
                        System.arraycopy(buffer, 5, data, 0, length);

                        try {
                            pipedOutputStream.write(data, 0, length);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e(TAG, "写入数据报错" + simpleDateFormat.format(new Date()) + " " + e.getMessage());
                        }


                    } else if (buffer[0] == 1) {
                        if (isListening) {//是否在监听
                            Log.e(TAG, "***收到停止包" + simpleDateFormat.format(new Date()));
                            try {

                                pipedOutputStream.flush();
                                pipedOutputStream.close();//关闭流，解析语音
                                readyReciveData = false;//已经结束，不在接收数据
                                isListening = false;//不在监听
                                sendStopPacket();//发送指令，不在接收数据
                                entTime = System.currentTimeMillis();
                                Log.e(TAG, "***收到停止包" + simpleDateFormat.format(new Date()) + " 录音时间:" + (entTime - startTime) + " ms ");
                                startTime = entTime;
//                            byteout.reset();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e(TAG, "写入数据报错" + simpleDateFormat.format(new Date()) + " " + e.getMessage());
                            }
                        }
                    }//else if


                }//while 循环 接收数据
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "运行异常 " + e.getMessage());
            }

        }
    }

    /**
     * 注册语音解析
     */
    private void registerSpeechToText() throws IOException {
        Log.i(TAG, "*** 开始注册websocket");
        pipedInputStream = new PipedInputStream();
        pipedOutputStream = new PipedOutputStream();
        pipedInputStream.connect(pipedOutputStream);
        watsonClient.speechToText(pipedInputStream, options, this);//注册了
        registerSpeechToText = true;
        Log.i(TAG, "***注册了websocket");

    }


    private void sendStartPacket() {
        try {

            datagramSocket.send(new DatagramPacket(new byte[]{1, 1}, 2, inetAddress, port));//发开始接收信号两个字节的数据：11
            Log.e(TAG, "发送了额请求数据包");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "发送了额请求数据包 报错" + e.getMessage());
        }
    }

    private void sendStopPacket() {
        try {
            datagramSocket.send(new DatagramPacket(new byte[]{1, 0}, 2, inetAddress, port));//发结束接收信号两个字节的数据：10
            Log.e(TAG, "发送了 结束 数据包");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "发送了结束 数据包 报错" + e.getMessage());
        }

    }


    /**
     * 发送滴一下广播
     */
    private void sendBeginBroadcast() {
        Intent intent = new Intent();
        intent.setAction("com.action.receive.msgtype");
        //头部开始录音
        intent.putExtra("msgType", 10000);
        sendBroadcast(intent);
        Log.i(WatsonApplication.tag, TAG + "*** 发送滴滴广播");

    }

    /**
     * 显示界面广播
     *
     * @param result
     * @param msgType
     */
    private void sentDataToShow(String result, int msgType) {
        Intent intent = new Intent();
        intent.setAction("com.unisrobot.u05.robotapi.client.action");// com.unisrobot.u05.robotapi.action
        intent.putExtra("data", "{\"msgType\":" + msgType + ",\"msgData\":\"" + result + "\"}");
        sendBroadcast(intent);
        Log.i(WatsonApplication.tag, TAG + " ****发送广播 action =com.unisrobot.u05.robotapi.action " + " data = {\"msgType\":9000,\"msgData\":\"" + result + "\"}");

    }


}

