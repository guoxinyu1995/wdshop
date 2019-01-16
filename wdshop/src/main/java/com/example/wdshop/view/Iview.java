package com.example.wdshop.view;

public interface Iview<E> {
    void requestData(E e);
    void requestFail(String error);
}
