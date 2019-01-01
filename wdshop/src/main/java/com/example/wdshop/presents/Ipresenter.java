package com.example.wdshop.presents;



import java.util.Map;

public interface Ipresenter {
    //post请求
    void startRequest(String url, Map<String, String> map, Class clazz);
    //get请求
    void getRequest(String url,Class clazz);
}
