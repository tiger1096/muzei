package com.nice.storage;

import com.nice.data.DataCenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DataBuffer {
    private List<Object> datas = new ArrayList<>();
    private Stack<Object> dataStack = new Stack<>();

    public Object pop() {
        synchronized (DataCenter.class) {
            return dataStack.pop();
        }
    }

    public void push(List<Object> newData) {
        if (newData == null || newData.size() == 0) {
            return;
        }
        synchronized (DataCenter.class) {
            for (Object obj : newData) {
                dataStack.push(obj);
            }
        }
    }
}
