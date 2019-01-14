package com.example.wdshop.shoppingcart.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.activity.BaseActivity;
import com.example.wdshop.api.Apis;
import com.example.wdshop.custom.CustomScrollView;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.shoppingcart.bean.ParticularsBean;
import com.example.wdshop.view.Iview;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DepailActivity extends BaseActivity implements Iview {
    @BindView(R.id.details_viewpager_show)
    Banner detailsViewpagerShow;
    @BindView(R.id.details_textview_sprice)
    TextView detailsTextviewSprice;
    @BindView(R.id.details_textview_sold)
    TextView detailsTextviewSold;
    @BindView(R.id.details_textview_title)
    TextView detailsTextviewTitle;
    @BindView(R.id.details_textview_Weight)
    TextView detailsTextviewWeight;
    @BindView(R.id.details_Image_details)
    WebView detailsImageDetails;
    @BindView(R.id.details_textview_describe)
    TextView detailsTextviewDescribe;
   /* @BindView(R.id.details_Image_describe)
    SimpleDraweeView detailsImageDescribe;*/
    @BindView(R.id.details_recview_comments)
    RecyclerView detailsRecviewComments;
    @BindView(R.id.details_textview_comments)
    TextView detailsTextviewComments;
    @BindView(R.id.details_scroll_changecolor)
    CustomScrollView detailsScrollChangecolor;
    @BindView(R.id.details_text_goodsT)
    TextView detailsTextGoodsT;
    @BindView(R.id.details_text_detailsT)
    TextView detailsTextDetailsT;
    @BindView(R.id.details_text_commentsT)
    TextView detailsTextCommentsT;
    @BindView(R.id.details_text_goods)
    TextView detailsTextGoods;
    @BindView(R.id.details_text_details)
    TextView detailsTextDetails;
    @BindView(R.id.details_text_comments)
    TextView detailsTextComments;
    @BindView(R.id.details_relative_changer)
    RelativeLayout detailsRelativeChanger;
    @BindView(R.id.details_relat_changecolor)
    RelativeLayout detailsRelatChangecolor;
    @BindView(R.id.details_addshopcar)
    ImageView detailsAddshopcar;
    @BindView(R.id.details_relative_addshoppingcar)
    RelativeLayout detailsRelativeAddshoppingcar;
    @BindView(R.id.details_buy)
    ImageView detailsBuy;
    @BindView(R.id.details_relative_pay)
    RelativeLayout detailsRelativePay;
    private PresenterImpl presenter;
    private int commodityId;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_depail2;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        commodityId = intent.getIntExtra("commodityId", 0);
        presenter.getRequest(String.format(Apis.URL_FIND_COMMODITY_GET, commodityId), ParticularsBean.class);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new PresenterImpl(this);
        ButterKnife.bind(this);
        detailsViewpagerShow.setBannerStyle(BannerConfig.NUM_INDICATOR);
        detailsViewpagerShow.setImageLoader(new ImageLoaderInterface<SimpleDraweeView>() {

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
    }

    @Override
    public void requestData(Object o) {
        if (o instanceof ParticularsBean) {
            ParticularsBean particularsBean = (ParticularsBean) o;
            ParticularsBean.ResultBean result = particularsBean.getResult();
            if (particularsBean == null || !particularsBean.isSuccess()) {
                Toast.makeText(DepailActivity.this, particularsBean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                String[] split = particularsBean.getResult().getPicture().split("\\,");
                List<String> list = new ArrayList<>();
                for (int i = 0; i < split.length; i++) {
                    list.add(split[i]);
                }
                detailsViewpagerShow.setImages(list);
                detailsViewpagerShow.start();
                detailsTextviewSprice.setText("￥" + particularsBean.getResult().getPrice());
                detailsTextviewSold.setText("已售" + particularsBean.getResult().getSaleNum() + "件");
                detailsTextviewTitle.setText(particularsBean.getResult().getCommodityName());
                detailsTextviewWeight.setText(particularsBean.getResult().getWeight() + "kg");
                //detailsTextviewDescribe.setText(particularsBean.getResult().getDescribe());
                detailsImageDetails.loadDataWithBaseURL(null, particularsBean.getResult().getDetails(), "text/html", "utf-8", null);
            }
        }
    }

    @Override
    public void requestFail(Object o) {

    }
}
