package com.example.vmac.myrobot.util;

import java.util.Objects;

/**
 * Created by Administrator on 2017/12/13.
 */

public class SimpeQueue {
    double[] data;
    private  int  seek=0;
    private  int size;
    private  int head = 0 ;
    public SimpeQueue(int size){
        this.size=size;
        data=new double[size];
    }

    public  double add( double d){
        ++seek;
        if(seek == size){
            seek=0;
        }
        data[seek]=d;
        return  d;
    }




    public void clear() {
        data=new double[size];
    }


    public double peek(){
        return data[seek];
    }

    public double poll() {

        return data[head];
    }


//    public double element() {
//        return null;
//    }



    public int seiz(){
        return  size;
    }

}
