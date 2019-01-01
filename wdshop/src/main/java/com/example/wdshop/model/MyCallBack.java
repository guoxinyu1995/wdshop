package com.example.wdshop.model;

public interface MyCallBack<E> {
    void onSuccess(E data);
    void onFail(String error);
}
