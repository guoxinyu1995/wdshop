package com.example.wdshop.model;



import java.util.Map;

import okhttp3.MultipartBody;

public interface Imodel {
    /**
     * post
     * */
    void requestPost(String url, Map<String, String> map, Class clazz, MyCallBack myCallBack);
    /**
     * get
     * */
    void requestGet(String url,Class clazz, MyCallBack myCallBack);
    /**
     * delete
     * */
    void requestDelete(String url,Class clazz, MyCallBack myCallBack);
    /**
     * put
     * */
    void requestPut(String url, Map<String, String> map, Class clazz, MyCallBack myCallBack);
    /**
     * 上传头像
     * */
    void requestImagePost(String url,Map<String,String> map,Class clazz,MyCallBack myCallBack);
    /**
     * 上传多张图片
     * */
    void imagePost(String url,Map<String,Object> map,Class clazz,MyCallBack myCallBack);
}
