package com.example.wdshop.shoppingcart.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.activity.BaseActivity;
import com.example.wdshop.api.Apis;
import com.example.wdshop.shoppingcart.bean.AddCartBean;
import com.example.wdshop.shoppingcart.bean.FindShoppingCartBean;
import com.example.wdshop.shoppingcart.bean.ParticularsBean;
import com.example.wdshop.shoppingcart.bean.ShoppCartBean;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.view.Iview;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.add)
    ImageView add;
    @BindView(R.id.buy)
    ImageView buy;
    private PresenterImpl presenter;
    private int commodityId;

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
        commodityId = intent.getIntExtra("commodityId", 0);
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
                Uri uri = Uri.parse((String) path);
                imageView.setImageURI(uri);
            }

            @Override
            public SimpleDraweeView createImageView(Context context) {
                SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
                return simpleDraweeView;
            }
        });
        //支持缩放，默认为true。是下面那个的前提。
        webview.getSettings().setSupportZoom(true);
        //设置内置的缩放控件。
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.getSettings().setLoadWithOverviewMode(true);
    }

    /**
     * 请求成功
     */
    @Override
    public void requestData(Object o) {
        //展示详情
        if (o instanceof ParticularsBean) {
            ParticularsBean particularsBean = (ParticularsBean) o;
            ParticularsBean.ResultBean result = particularsBean.getResult();
            if (particularsBean == null || !particularsBean.isSuccess()) {
                Toast.makeText(ParticularsActivity.this, particularsBean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                String[] split = particularsBean.getResult().getPicture().split("\\,");
                List<String> list = new ArrayList<>();
                for (int i = 0; i < split.length; i++) {
                    list.add(split[i]);
                }
                banner.setImages(list);
                banner.start();
                title.setText(particularsBean.getResult().getCategoryName());
                price.setText("￥" + particularsBean.getResult().getPrice());
                webview.loadDataWithBaseURL(null, particularsBean.getResult().getDetails(), "text/html", "utf-8", null);
            }
            //先查询购物车，在添加到购物车
        } else if (o instanceof FindShoppingCartBean) {
            FindShoppingCartBean findCartBean = (FindShoppingCartBean) o;
            if (findCartBean == null || !findCartBean.isSuccess()) {
                Toast.makeText(ParticularsActivity.this, findCartBean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                //实例化ShoppCartBean
                List<ShoppCartBean> list = new ArrayList<>();
                //得到查询购物车的集合
                List<FindShoppingCartBean.ResultBean> result = findCartBean.getResult();
                //遍历集合添加到ShoppCartBean集合中
                for (FindShoppingCartBean.ResultBean re : result) {
                    list.add(new ShoppCartBean(re.getCommodityId(), re.getCount()));
                }
                //添加购物车的方法
                getAddCart(list);
            }
            //添加购物车
        } else if (o instanceof AddCartBean) {
            AddCartBean cartBean = (AddCartBean) o;
            Toast.makeText(ParticularsActivity.this, cartBean.getMessage(), Toast.LENGTH_SHORT).show();
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

    @OnClick({R.id.add, R.id.buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //加入购物车
            case R.id.add:
                //点击是先查询购物车，判断购物车中是否有相同的商品如果有数量加一，
                // 如果没有相同的商品将商品加入购物车
                presenter.getRequest(Apis.URL_FIND_CART_GET, FindShoppingCartBean.class);
                break;
            case R.id.buy:
                Toast.makeText(ParticularsActivity.this, "敬请期待", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    /**
     * 同步购物车
     */
    private void getAddCart(List<ShoppCartBean> list) {
        //String str = "[";
        if (list.size() == 0) {
            list.add(new ShoppCartBean(Integer.valueOf(commodityId), 1));
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (Integer.valueOf(commodityId) == list.get(i).getCommodityId()) {
                    int count = list.get(i).getCount();
                    count++;
                    list.get(i).setCount(count);
                    break;
                } else if (i == list.size() - 1) {
                    list.add(new ShoppCartBean(Integer.valueOf(commodityId), 1));
                    break;
                }
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(list);
        Map<String, String> map = new HashMap<>();
        map.put("data", json);
        presenter.putRequest(Apis.URL_SHOPPING_CART_PUT, map, AddCartBean.class);
    }
}
