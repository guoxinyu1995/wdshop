package com.example.wdshop.model;

import com.example.wdshop.network.RetrofitManager;
import com.google.gson.Gson;

import java.util.Map;

public class ModelImpl implements Imodel {
    private MyCallBack myCallBack;
    /**
     * post
     * */
    @Override
    public void requestData(String url, final Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        this.myCallBack = myCallBack;
        RetrofitManager.getInstance().post(url,map,new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    Object o = new Gson().fromJson(data, clazz);
                    if(myCallBack!=null){
                        myCallBack.onSuccess(o);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(myCallBack!=null){
                        myCallBack.onFail(e.getMessage());
                    }
                }

            }

            @Override
            public void onFail(String error) {
                if(myCallBack!=null){
                    myCallBack.onFail(error);
                }
            }
        });
    }
    /**
     * get
     * */
    @Override
    public void requestGet(String url, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().get(url,new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    Object o = new Gson().fromJson(data, clazz);
                    if(myCallBack!=null){
                        myCallBack.onSuccess(o);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(myCallBack!=null){
                        myCallBack.onFail(e.getMessage());
                    }
                }
            }

            @Override
            public void onFail(String error) {
                if(myCallBack!=null){
                    myCallBack.onFail(error);
                }
            }
        });
    }
}
