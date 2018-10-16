package com.example.vmac.myrobot.view;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vmac.myrobot.R;
import com.example.vmac.myrobot.broadcastreceiver.RobotBroadcastReceiver;
import com.example.vmac.myrobot.oog.AudioInputStream;

import com.example.vmac.myrobot.service.MicrophoneService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class StartActivity extends AppCompatActivity {
    private Boolean isreceive=false;
    private Boolean send = true;
    private Thread service;
    private DatagramSocket socket;
    AudioTrack  audioTrack;
    private TextView textView;
    ServiceConnection conn;
    MessageReceiver messageReceiver;
    JsonParser jsonParser =new JsonParser();
    private TextView tx_stat;
    Gson gson ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_start);
        Button bt_startService =findViewById(R.id.bt_startService);
        Button bt_stopService =findViewById(R.id.bt_stopService);
        gson =new Gson();
    //注册
        //动态注册广播接收器
        messageReceiver = new  MessageReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.unisrobot.u05.robotapi.client.action");
        registerReceiver(messageReceiver, intentFilter);

        textView=findViewById(R.id.tx_view);
        tx_stat=findViewById(R.id.tx_stat);

        bt_startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent =new Intent(StartActivity.this, LaunchService.class);
////                intent.setAction("android.intent.action.myservice");
////                intent.setPackage("com.example.vmac.myrobot.service");
//                startService(intent);
                startService();
                Log.i(StartActivity.class.getSimpleName(),"启动测试服务");
            }
        });

//        bt_startService.setVisibility(View.GONE);

        bt_stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent =new Intent(StartActivity.this, LaunchService.class);
////                intent.setAction("android.intent.action.myservice");
////                intent.setPackage("com.example.vmac.myrobot.service");
//                stopService(intent);
               stopService();
                Log.i(StartActivity.class.getSimpleName(),"关闭测试服务");
            }
        });

        Button bt_sendBroadcast=findViewById(R.id.bt_sentBroadcast);
        Button bt_sendBroadcastStop=findViewById(R.id.bt_sentBroadcastStop);


        //开始播放
        bt_sendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent();
                intent.setAction("android.intent.action.ibm.Service");
                intent.putExtra("data","data");
                intent.putExtra(RobotBroadcastReceiver.actionKey,RobotBroadcastReceiver.startService);
                sendBroadcast(intent);
                Log.i(StartActivity.class.getSimpleName(),"发送开始服务广播");


//
//

               // Intent intent =new Intent(StartActivity.this, MicrophoneService.class);
              //  startService(intent);


            }
        });

        //停止播放
        bt_sendBroadcastStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent =new Intent(StartActivity.this, MicrophoneService.class);
                stopService(intent);

//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.ibm.Service");
//                intent.putExtra("data","data");
//                intent.putExtra(RobotBroadcastReceiver.actionKey,RobotBroadcastReceiver.stopService);
//                sendBroadcast(intent);
                Log.i(StartActivity.class.getSimpleName(),"发送停止服务广播");
//


            }
        });

        Button bt_sendFileData=findViewById(R.id.bt_sendFileData);
        bt_sendFileData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });

        Button bt_playStream=findViewById(R.id.bt_playStream);
        bt_playStream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int buffer=    AudioRecord.getMinBufferSize(16000, 1, AudioFormat.ENCODING_PCM_16BIT);
            System.out.println(buffer);
            }
        });

        Button bt_stransfor=findViewById(R.id.bt_stranstor);
        bt_stransfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            File file =new File(StartActivity.this.getCacheDir(),"udp2017-12-08.pcm");
                            FileInputStream inputStream =new FileInputStream(file);
                            AudioInputStream audioInputStream =new AudioInputStream(1536,inputStream);
                            File oog =new File(StartActivity.this.getCacheDir(),"oog.oog");
                            if(!oog.exists()){
                                oog.createNewFile();
                            }
                            FileOutputStream outputStream =new FileOutputStream(oog);
//                            byte[] buffer=new byte[1024];
//                            int r=0;
//                            while((r=audioInputStream.read(buffer,0,buffer.length))>0){
//                                outputStream.write(buffer,0,r);
//                            }
                            if(audioInputStream.getData()!=null){
                                outputStream.write(audioInputStream.getData());
                            }

                            outputStream.close();
                            inputStream.close();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();


            }
        });
        requestRecordingPermission();

    }


    private void requestRecordingPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    5000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageReceiver);
    }

    private class ServiceSocket extends  Thread{
        @Override
        public void run() {
            super.run();

            try {
                //创建DatagramSocket对象
                socket = new DatagramSocket(3288);
                byte[] addr = {(byte) 192, (byte) 168, 5, 123};
                File file = new File(StartActivity.this.getCacheDir(),"english2.pcm");
               FileInputStream input = new FileInputStream(file);
               byte[] data=new byte[input.available()];
                input.read(data, 0, input.available());

                byte[] buffer = new byte[65535];

                int length = buffer.length;
//              InetAddress address = InetAddress.getByAddress(addr);
//                InetAddress address = InetAddress.getByName("localhost");
                // 2.创建数据报

                DatagramPacket receivepacket = new DatagramPacket(buffer,buffer.length);
                socket.receive(receivepacket);
                Log.i("","收到数据包"+new String(receivepacket.getData()));
                while(isreceive){

                    if(send) {
                        DatagramPacket packet = new DatagramPacket(data, data.length, receivepacket.getAddress(), receivepacket.getPort());
                        socket.send(packet);
                        send=false;
                    }else {
                        Thread.sleep(2000);
                        Thread.yield();
                    }
                }

                // 3.

                // 4.向服务器端发送数据报

                socket.close();
                Log.i("","发送语音数据包");
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    private void sendData(){
        send=true;
    }

    private void stopService(){
        if(service!=null) {
            isreceive=false;
            service.interrupt();
            if(socket!=null&&!socket.isClosed()){
                socket.close();
            }

        }
    }

    public void startService(){
        stopService();
        service=new ServiceSocket();
        isreceive=true;
        service.start();
    }
    private class MessageReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int msgType = intent.getIntExtra("msgtype",0);
            if(msgType==3){
               String string    = intent.getStringExtra("data");
                tx_stat.setText(string );

            }else{
                String str = intent.getStringExtra("data");
                Log.i("StartActivity",str);
                JsonElement jsonElement =  jsonParser.parse(str);

//           JsonElement jsonElement =  gson.toJsonTree(str);
                JsonObject result =  jsonElement.getAsJsonObject();
                String msg= result.get("msgData").getAsString();
                textView.setText(msg);
            }


        }
    }

}
