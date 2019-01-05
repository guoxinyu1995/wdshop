package com.example.wdshop.presents;



import java.util.Map;

public interface Ipresenter {
    //post请求
    void postRequest(String url, Map<String, String> map, Class clazz);
    //get请求
    void getRequest(String url,Class clazz);
    //delete请求
    void deleteRequest(String url,Class clazz);
    //put请求
    void putRequest(String url, Map<String, String> map, Class clazz);
}
