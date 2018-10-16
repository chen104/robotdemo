package com.example.vmac.myrobot.oog;

import android.util.Log;

import com.ibm.watson.developer_cloud.android.library.audio.AmplitudeListener;
import com.ibm.watson.developer_cloud.android.library.audio.AudioConsumer;
import com.ibm.watson.developer_cloud.android.library.audio.opus.OggOpusEnc;
import com.ibm.watson.developer_cloud.android.library.audio.utils.ContentType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Administrator on 2017/12/9.
 */

public class FileAudioToOggUtil implements AudioConsumer {
    public static String TAG= FileAudioToOggUtil.class.getSimpleName();
    private ByteArrayOutputStream byteArrayOutputStream ;
    public  ContentType CONTENT_TYPE;
    private OggOpusEnc encoder=null;
    private AmplitudeListener amplitudeListener;
    public FileInputStream fileInputStream;
    private FileOutputStream fileOutputStream;
    File toFile;
    public FileAudioToOggUtil(File file,int bufferSize){
        byteArrayOutputStream =new ByteArrayOutputStream();
        CONTENT_TYPE = ContentType.OPUS;
        if(file!=null&&file.exists()){
            try {
                encoder = new OggOpusEnc(this);
                fileInputStream = new FileInputStream(file);
                byte[]  buffer=new byte[bufferSize];
                int r=0;
                toFile = new File(file.getParent(),System.currentTimeMillis()+".ogg");
                toFile.createNewFile();
                fileOutputStream =new FileOutputStream(toFile);
                while((r=fileInputStream.read(buffer))>0){
                    write(buffer);
                }
                fileOutputStream.close();
                file.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(encoder!=null){
                encoder.close();

            }

        }else {
            Log.i(TAG,"***文件存在");
        }


    }

    public File getFile(){
        return  toFile;
    }

    public void write(byte[] data){

        try {
            encoder.onStart(); // must be called before writing
            encoder.encodeAndWrite(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void consume(byte[] data, double amplitude, double volume) {
        try {
//            byteArrayOutputStream.write(data);
            fileOutputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void consume(byte[] data) {
        try {
//            byteArrayOutputStream.write(data);
            fileOutputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
