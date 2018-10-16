package com.example.vmac.myrobot.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class StartService extends Service {
    private IBinder iBinder;
    public StartService() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(this.getClass().getSimpleName(),"*** onDestroy 服务");
    }



    private class MyBinder extends Binder {
        StartService getService(){
            return StartService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        return iBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(this.getClass().getSimpleName(),"***空服务，启动服务");
        return super.onStartCommand(intent, flags, startId);
    }

}
