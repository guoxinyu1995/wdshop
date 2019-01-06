package com.example.wdshop.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.api.Apis;
import com.example.wdshop.bean.ParticularsBean;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.view.Iview;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 展示详情的Activity
 */
public class ParticularsActivity extends BaseActivity implements Iview {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.webview)
    WebView webview;
    private PresenterImpl presenter;

    /**
     * 加载布局
     */
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_particulars;
    }

    /**
     * 加载数据
     */
    @Override
    protected void initData() {
        Intent intent = getIntent();
        int commodityId = intent.getIntExtra("commodityId", 0);
        presenter.getRequest(String.format(Apis.URL_FIND_COMMODITY_GET, commodityId), ParticularsBean.class);
    }

    /**
     * 初始化视图
     */
    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new PresenterImpl(this);
        ButterKnife.bind(this);
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        banner.setImageLoader(new ImageLoaderInterface<SimpleDraweeView>() {

            @Override
            public void displayImage(Context context, Object path, SimpleDraweeView imageView) {
                Uri uri=Uri.parse((String) path);
                imageView.setImageURI(uri);
            }

            @Override
            public SimpleDraweeView createImageView(Context context) {
                SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
                return simpleDraweeView;
            }
        });
    }

    /**
     * 请求成功
     */
    @Override
    public void requestData(Object o) {
        if(o instanceof ParticularsBean){
            ParticularsBean particularsBean = (ParticularsBean) o;
            if(particularsBean == null || !particularsBean.isSuccess()){
                Toast.makeText(ParticularsActivity.this,particularsBean.getMessage(),Toast.LENGTH_SHORT ).show();
            }else{
                String[] split = particularsBean.getResult().getPicture().split("\\,");
                List<String> list=new ArrayList<>();
                for (int i=0;i<split.length;i++){
                    list.add(split[i]);
                }
                banner.setImages(list);
                banner.start();
                title.setText(particularsBean.getResult().getCategoryName());
                price.setText("￥"+particularsBean.getResult().getPrice());
                webview.loadDataWithBaseURL(null, particularsBean.getResult().getDetails(), "text/html", "utf-8", null);
            }
        }
    }

    /**
     * 请求失败
     */
    @Override
    public void requestFail(Object o) {
        if (o instanceof Exception) {
            Exception e = (Exception) o;
            e.printStackTrace();
        }
        Toast.makeText(ParticularsActivity.this, "请求错误", Toast.LENGTH_SHORT).show();
    }

}