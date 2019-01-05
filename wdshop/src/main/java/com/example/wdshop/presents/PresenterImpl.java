package com.example.wdshop.presents;

import com.example.wdshop.model.ModelImpl;
import com.example.wdshop.model.MyCallBack;
import com.example.wdshop.view.Iview;

import java.util.Map;

public class PresenterImpl implements Ipresenter {
    private Iview mIview;
    private ModelImpl model;

    public PresenterImpl(Iview iview) {
        mIview = iview;
        model = new ModelImpl();
    }
    /**
     * post
     * */
    @Override
    public void postRequest(String url, Map<String, String> map, Class clazz) {
        model.requestPost(url, map, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                mIview.requestData(data);
            }
            @Override
            public void onFail(String error) {
                mIview.requestFail(error);
            }
        });
    }
    /**
     * get
     * */
    @Override
    public void getRequest(String url, Class clazz) {
        model.requestGet(url, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                mIview.requestData(data);
            }

            @Override
            public void onFail(String error) {
                mIview.requestFail(error);
            }
        });
    }
    /**
     * delete
     * */
    @Override
    public void deleteRequest(String url, Class clazz) {
        model.requestDelete(url, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                mIview.requestData(data);
            }

            @Override
            public void onFail(String error) {
                mIview.requestFail(error);
            }
        });
    }

    public void onDetach(){
        if (model!=null){
            model = null;
        }
        if(mIview!=null){
            mIview = null;
        }
    }
}
