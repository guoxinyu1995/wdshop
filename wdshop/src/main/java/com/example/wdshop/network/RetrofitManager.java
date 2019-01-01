package com.example.wdshop.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager<E> {
    private final String BASE_URL = "http://172.17.8.100/small/";
    private static RetrofitManager instance;
    private final BaseApis baseApis;
    //单例
    public static RetrofitManager getInstance() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                instance = new RetrofitManager();
            }
        }
        return instance;
    }
    //无参构造
    public RetrofitManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.addInterceptor(interceptor);
        builder.retryOnConnectionFailure(true);
        OkHttpClient build = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(build)
                .build();
        baseApis = retrofit.create(BaseApis.class);
    }
    /**
     * 可以这样生成Map<String, RequestBody> requestBodyMap
     * Map<String, String> requestDataMap这里面放置上传数据的键值对。
     */
    public Map<String, RequestBody> generateRequestBody(Map<String, String> requesrDataMap) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String key : requesrDataMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    requesrDataMap.get(key) == null ? "" : requesrDataMap.get(key));
            requestBodyMap.put(key, requestBody);
        }
        return requestBodyMap;
    }
    /**
     * get
     */
    public RetrofitManager get(String url,HttpListener listener) {
        baseApis.get(url)
                //后台执行在哪个线程
                .subscribeOn(Schedulers.io())
                //最终完成后执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())
                //设置rxjava
                .subscribe(getObserver(listener));
        return instance;
    }
    /**
     * 观察者
     */

    private Observer getObserver(final HttpListener listener) {
         Observer observer = new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                if (listener != null) {
                    listener.onFail(e.getMessage());
                }
            }
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String data = responseBody.string();
                    if (null != listener) {
                        listener.onSuccess(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onFail(e.getMessage());
                    }
                }
            }
        };
        return observer;
    }

    /**
     * 表单post请求
     */
    public RetrofitManager postFormBody(String url, Map<String, RequestBody> map,HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }
        baseApis.postFormBody(url,map)
                //后台执行在哪个线程
                .subscribeOn(Schedulers.io())
                //最终完成后执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())
                //设置rxjava
                .subscribe(getObserver(listener));
        return instance;
    }
    /**
     * 普通post
     * */
    public RetrofitManager post(String url,Map<String,String> map,HttpListener listener){
        if(map == null){
            map = new HashMap<>();
        }
        baseApis.post(url,map)
                 // 后台执行在哪个线程
                .subscribeOn(Schedulers.io())
                //最终完成后执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())
                //设置rxjava
                .subscribe(getObserver(listener));
        return instance;
    }

    //创建观察者



    public interface HttpListener {
        void onSuccess(String data);

        void onFail(String error);
    }
}
