package com.example.vmac.myrobot.oog;

import android.util.Log;

import com.ibm.watson.developer_cloud.android.library.audio.AudioConsumer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/12/9.
 */

public class InputStreamToFileOgg implements AudioConsumer {
    public final static  String TAG=InputStreamToFileOgg.class.getSimpleName();
    File toFile;
    FileOutputStream fileOutputStream;

    public  InputStreamToFileOgg(InputStream inputStream,int lenth,int bufferSize,File parent){
        byte[] buffer=new byte[bufferSize];
        int r=0;
        int seek=0;
        try {
            toFile=new File(parent,System.currentTimeMillis()+".ogg");
            toFile.createNewFile();
            fileOutputStream=new FileOutputStream(toFile);
            while((r = inputStream.read(buffer,0,bufferSize))>0&&(seek<lenth)){
                seek+=r;//记录读取的长度
            }



        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG,"****读取报错"+e.getMessage());
        }

    }
    @Override
    public void consume(byte[] data, double amplitude, double volume) {

    }

    @Override
    public void consume(byte[] data) {

    }
}
