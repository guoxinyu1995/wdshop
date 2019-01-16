package com.example.wdshop.order.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.activity.BaseActivity;
import com.example.wdshop.api.Apis;
import com.example.wdshop.order.bean.OrderBean;
import com.example.wdshop.order.bean.RemaitBean;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.view.Iview;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.http.Url;

/**
 * 写平价的Activity
 */
public class RemaitActivity extends BaseActivity implements Iview {
    @BindView(R.id.item_image)
    SimpleDraweeView itemImage;
    @BindView(R.id.item_name)
    TextView itemName;
    @BindView(R.id.item_price)
    TextView itemPrice;
    @BindView(R.id.write_remate)
    EditText writeRemate;
    @BindView(R.id.camera)
    ImageView camera;
    @BindView(R.id.radio)
    RadioButton radio;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.publish)
    Button publish;
    private PresenterImpl presenter;
    private String orderId;
    private int commodityId;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_remait;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        OrderBean.OrderListBean.DetailListBean dataBean = (OrderBean.OrderListBean.DetailListBean) intent.getSerializableExtra("dataBean");
        commodityId = dataBean.getCommodityId();
        double commodityPrice = dataBean.getCommodityPrice();
        String commodityName = dataBean.getCommodityName();
        String img = dataBean.getCommodityPic().split("\\,")[0].replace("https","http");
        itemImage.setImageURI(Uri.parse(img));
        itemName.setText(commodityName);
        itemPrice.setText("￥"+commodityPrice);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new PresenterImpl(this);
        ButterKnife.bind(this);
    }

    @Override
    public void requestData(Object o) {
        if(o instanceof RemaitBean){
            RemaitBean remaitBean = (RemaitBean) o;
                Toast.makeText(RemaitActivity.this,remaitBean.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void requestFail(String erroe) {
            Toast.makeText(RemaitActivity.this, erroe, Toast.LENGTH_SHORT).show();
    }


    @OnClick({R.id.radio, R.id.publish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio:
                break;
            case R.id.publish:
                String content = writeRemate.getText().toString().trim();
                Map<String,String> map = new HashMap<>();
                map.put("commodityId",String.valueOf(commodityId));
                map.put("orderId",orderId);
                map.put("content",content);
                presenter.postRequest(Apis.URL_ADD_COMMODITY_COMMENT_POST,map,RemaitBean.class);
                break;
                default:
                    break;
        }
    }
}
