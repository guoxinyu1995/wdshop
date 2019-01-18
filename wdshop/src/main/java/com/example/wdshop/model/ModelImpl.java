package com.example.wdshop.model;

import com.example.wdshop.network.RetrofitManager;
import com.example.wdshop.util.NetUtil;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.MultipartBody;

public class ModelImpl implements Imodel {
    private MyCallBack myCallBack;
    /**
     * post
     * */

    @Override
    public void requestPost(String url, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        this.myCallBack = myCallBack;
        if(!NetUtil.hasNetWork()){
            myCallBack.onFail("网络不可用，请检查网络状态");
        }else {
            RetrofitManager.getInstance().post(url, map, new RetrofitManager.HttpListener() {
                @Override
                public void onSuccess(String data) {
                    try {
                        Object o = new Gson().fromJson(data, clazz);
                        if (myCallBack != null) {
                            myCallBack.onSuccess(o);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (myCallBack != null) {
                            myCallBack.onFail(e.getMessage());
                        }
                    }

                }
                @Override
                public void onFail(String error) {
                    if (myCallBack != null) {
                        myCallBack.onFail(error);
                    }
                }
            });
        }
    }

    /**
     * get
     * */
    @Override
    public void requestGet(String url, final Class clazz, final MyCallBack myCallBack) {
        if(!NetUtil.hasNetWork()){
            myCallBack.onFail("网络不可用，请检查网络状态");
        }else {
            RetrofitManager.getInstance().get(url, new RetrofitManager.HttpListener() {
                @Override
                public void onSuccess(String data) {
                    try {
                        Object o = new Gson().fromJson(data, clazz);
                        if (myCallBack != null) {
                            myCallBack.onSuccess(o);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (myCallBack != null) {
                            myCallBack.onFail(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFail(String error) {
                    if (myCallBack != null) {
                        myCallBack.onFail(error);
                    }
                }
            });
        }
    }
    /**
     * delete
     * */
    @Override
    public void requestDelete(String url, final Class clazz, final MyCallBack myCallBack) {
        if(!NetUtil.hasNetWork()){
            myCallBack.onFail("网络不可用，请检查网络状态");
        }else {
            RetrofitManager.getInstance().delete(url, new RetrofitManager.HttpListener() {
                @Override
                public void onSuccess(String data) {
                    try {
                        Object o = new Gson().fromJson(data, clazz);
                        if (myCallBack != null) {
                            myCallBack.onSuccess(o);
                        }
                    } catch (Exception e) {
                        if (myCallBack != null) {
                            myCallBack.onFail(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFail(String error) {
                    if (myCallBack != null) {
                        myCallBack.onFail(error);
                    }
                }
            });
        }
    }
    /**
     * put
     * */
    @Override
    public void requestPut(String url, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        if(!NetUtil.hasNetWork()){
            myCallBack.onFail("网络不可用，请检查网络状态");
        }else {
            RetrofitManager.getInstance().put(url, map, new RetrofitManager.HttpListener() {
                @Override
                public void onSuccess(String data) {
                    try {
                        Object o = new Gson().fromJson(data, clazz);
                        if (myCallBack != null) {
                            myCallBack.onSuccess(o);
                        }
                    } catch (Exception e) {
                        if (myCallBack != null) {
                            myCallBack.onFail(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFail(String error) {
                    if (myCallBack != null) {
                        myCallBack.onFail(error);
                    }
                }
            });
        }
    }
    /**
     * 上传头像
     * */
    @Override
    public void requestImagePost(String url, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().imagePost(url, map, new RetrofitManager.HttpListener() {
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

    @Override
    public void imagePost(String url, Map<String, Object> map, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().postFileMore(url, map, new RetrofitManager.HttpListener() {
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
