package com.example.vmac.myrobot.util;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

/**
 * Created by Administrator on 2017/12/12.
 */

public class MyArrayQueue <T> implements Queue{
    List data ;
    public MyArrayQueue(int  queueSize ){
        data =new ArrayList<T>();
    }



    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return data.contains(o);
    }

    @NonNull
    @Override
    public Iterator iterator() {
        return data.iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return data.toArray();
    }

    @NonNull
    @Override
    public Object[] toArray(@NonNull Object[] objects) {
        return data.toArray(objects);
    }

    @Override
    public boolean add(Object o) {
        return data.add(o);
    }

    @Override
    public boolean remove(Object o) {
        return data.remove(o);
    }

    @Override
    public boolean addAll(@NonNull Collection collection) {
        return data.addAll(collection);
    }

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public boolean retainAll(@NonNull Collection collection) {
        return data.retainAll(collection);
    }

    @Override
    public boolean removeAll(@NonNull Collection collection) {
        return false;
    }

    @Override
    public boolean containsAll(@NonNull Collection collection) {
        return false;
    }

    @Override
    public boolean offer(Object o) {
        return false;
    }

    @Override
    public Object remove() {
        return null;
    }

    @Override
    public Object poll() {
        return null;
    }

    @Override
    public Object element() {
        return null;
    }

    @Override
    public Object peek() {
        return null;
    }
}
