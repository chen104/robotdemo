package com.example.vmac.myrobot.oog;

import android.util.Log;
import com.example.vmac.myrobot.util.NumberUtil;
import com.ibm.watson.developer_cloud.android.library.audio.AmplitudeListener;
import com.ibm.watson.developer_cloud.android.library.audio.AudioConsumer;
import com.ibm.watson.developer_cloud.android.library.audio.opus.OggOpusEnc;
import com.ibm.watson.developer_cloud.android.library.audio.utils.ContentType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
/**
 * Created by Administrator on 2017/12/7.
 */

public class AudioInputStream extends InputStream implements AudioConsumer {
    private static final String TAG = AudioInputStream.class.getName();

    /**
     * The content type.
     */
    public  ContentType CONTENT_TYPE;

//    private final MicrophoneCaptureThread captureThread;
    private  PipedOutputStream os;
    private  PipedInputStream is;
    private ByteArrayOutputStream byteArrayOutputStream;
    private OggOpusEnc encoder=null;
    private AmplitudeListener amplitudeListener;
    private byte[] bytedata;

    /**
     * Instantiates a new microphone input stream.
     *
     *
     */
    public AudioInputStream (int bufferSize,InputStream inputStream) {

        CONTENT_TYPE = ContentType.OPUS;
        byteArrayOutputStream=new ByteArrayOutputStream();
        os = new PipedOutputStream();
        is = new PipedInputStream();
        try {
            is.connect(os);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        try {
            encoder = new OggOpusEnc(this);
            short[] shortBuffer =new short[bufferSize/2];
            int r = 0;
            byte[]  buffer =new byte[bufferSize];
            short item=0;
            int num=0;
            while(( r =  inputStream.read(buffer,0,bufferSize))>0){
                Log.i(TAG,"bytes lengh "+ buffer.length+" 0="+buffer[0]+" 1="+buffer[1]+" 1="+buffer[2]);
                //
                long v = 0;
                int j=0;
                for(int i=0;i<r;i+=2){
                    item=NumberUtil.byteToShort(buffer,i);
                    shortBuffer[j]=item;
                    j++;
                    v+= item*item;
                }
                double amplitude = v / (double) (r/2);
                double volume = 0;
                if (amplitude > 0) {
                    volume = 10 * Math.log10(amplitude);
                }

                num++;
                Log.i("tikip","第"+num+"贞数据"+"分贝数"+volume);

//                calculate amplitude and volume
//                long v = 0;
//                for (int i = 0; i < r; i++) {
//                    v += buffer[i] * buffer[i];
//                }
//
//                double amplitude = v / (double) r;
//                double volume = 0;
//                if (amplitude > 0) {
//                    volume = 10 * Math.log10(amplitude);
//                }

                // convert short buffer to bytes
//                ByteBuffer bufferBytes = ByteBuffer.allocate(r * 2); // 2 bytes per short
//                bufferBytes.order(ByteOrder.LITTLE_ENDIAN); // save little-endian byte from short buffer
//                bufferBytes.asShortBuffer().put(buffer, 0, r);
//                byte[] bytes = bufferBytes.array();

//                ByteBuffer bufferBytes = ByteBuffer.allocate(r); // 2 bytes per short
//                bufferBytes.order(ByteOrder.LITTLE_ENDIAN); // save little-endian byte from short buffer
//                bufferBytes.asShortBuffer().put(shortBuffer, 0, r/2);

//                byte[] bytes = bufferBytes.array();
//                if(bytes!=null){
//                    Log.i(TAG,"bytes lengh "+ bytes.length+" 0="+bytes[0]+" 1="+bytes[1]+" 1="+bytes[2]);
//                }

                try {
                    encoder.onStart(); // must be called before writing
                    encoder.encodeAndWrite(buffer);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }//while

            if (encoder != null) {
                encoder.close();
            }
            bytedata=byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Read.
     *
     * @return the int
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public int read() throws IOException {
        throw new UnsupportedOperationException("Call read(byte[]) or read(byte[], int, int)");
    }

    /**
     * Read.
     *
     * @param buffer the buffer
     * @return the int
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public int read(byte[] buffer) throws IOException {
        return read(buffer, 0, buffer.length);
    }

    /**
     * Read.
     *
     * @param buffer the buffer
     * @param offset the offset
     * @param length the length
     * @return the int
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException {
        return is.read(buffer, offset, length);
    }

    /**
     * Close.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void close() throws IOException {
//        captureThread.end();
        os.close();
        is.close();
    }

    /**
     * Consume.
     *
     * @param data      the data
     * @param amplitude the amplitude
     * @param volume    the volume
     */
    @Override
    public void consume(byte[] data, double amplitude, double volume) {
        if (amplitudeListener != null) {
            amplitudeListener.onSample(amplitude, volume);
        }

        try {
            byteArrayOutputStream.write(data);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * Consume.
     *
     * @param data the data
     */
    @Override
    public void consume(byte[] data) {
        try {
            byteArrayOutputStream.write(data);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * Receive amplitude (and volume) data per sample from the {@code MicrophoneInputStream}.
     *
     * @param listener Notified per sample with amplitude and volume data.
     */
    public void setOnAmplitudeListener(AmplitudeListener listener) {
        amplitudeListener = listener;
    }

    /**
     * Get the audio format from the {@code MicrophoneInputStream}.
     *
     * @return audio/l16;rate=16000
     */
    public String getContentType() {
        return CONTENT_TYPE.toString();
    }

    public byte[] getData(){
        return bytedata;
    }
}
