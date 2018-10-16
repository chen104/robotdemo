package com.example.vmac.myrobot.app;

import android.app.Application;
import android.widget.Toast;


import com.example.vmac.myrobot.R;
import com.example.vmac.myrobot.watson.WatsonClient;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


/**
 * Created by Administrator on 2017/8/17.
 */

public class WatsonApplication extends Application {
    public static String tag=WatsonApplication.class.getSimpleName();

    private WatsonClient watsonClient;
    private boolean isLisnter=true;

    public synchronized void setLisnter(boolean lisnter) {
        isLisnter = lisnter;
    }
    public boolean hasAction=false;

    String analytics_APIKEY ;
    @Override
    public void onCreate() {
        super.onCreate();
        analytics_APIKEY = this.getString(R.string.mobileanalytics_apikey);

//        DataThread dataThread =new DataThread();
//        dataThread.start();
    }

    public void initParam(){
        watsonClient =new WatsonClient(this);
    }
    private class DataThread extends  Thread{
        @Override
        public void run() {
            receviceData( );
        }
    }

    public synchronized WatsonClient getWatsonClient() {
        if(watsonClient==null){
            watsonClient=new WatsonClient(this.getApplicationContext());
        }
        return watsonClient;
    }

    private void receviceData( ){
        /*
         * 接收客户端发送的数据
         */
        // 1.创建服务器端DatagramSocket，指定端口
        try {
        DatagramSocket socket = null;

          socket = new DatagramSocket(8800);

          String ip=  InetAddress.getLocalHost().getHostAddress();
          String hostname =InetAddress.getLocalHost().getHostName();
            byte[] bytes =InetAddress.getLocalHost().getAddress();
          System.out.println(ip+" "+hostname+"  "+bytes);
        // 2.创建数据报，用于接收客户端发送的数据
        byte[] data = new byte[1024];// 创建字节数组，指定接收的数据包的大小
        DatagramPacket packet = new DatagramPacket(data, data.length);
        // 3.接收客户端发送的数据
        System.out.println("****服务器端已经启动，等待客户端发送数据");
        while(true) {
            socket.receive(packet);// 此方法在接收到数据报之前会一直阻塞
            // 4.读取数据
            String info = new String(data, 0, packet.getLength());
            System.out.println("我是服务器，客户端说：" + info);
            //  Toast.makeText(this,"收到信息："+info,Toast.LENGTH_LONG);
        /*
         * 向客户端响应数据
         */
            // 1.定义客户端的地址、端口号、数据
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            byte[] data2 = "欢迎您!".getBytes();
            // 2.创建数据报，包含响应的数据信息
            DatagramPacket packet2 = new DatagramPacket(data2, data2.length, address, port);
            // 3.响应客户端
            socket.send(packet2);
        }
        // 4.关闭资源

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
