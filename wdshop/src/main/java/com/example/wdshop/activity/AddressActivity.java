package com.example.wdshop.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.adaper.AddressAdaper;
import com.example.wdshop.api.Apis;
import com.example.wdshop.bean.AddressBean;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.view.Iview;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressActivity extends BaseActivity implements Iview {
    @BindView(R.id.text_myaddre)
    TextView textMyaddre;
    @BindView(R.id.accomplish)
    TextView accomplish;
    @BindView(R.id.btu_newaddress)
    Button btuNewaddress;
    @BindView(R.id.address_recycle)
    RecyclerView addressRecycle;
    private PresenterImpl presenter;
    private AddressAdaper addressAdaper;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_address;
    }

    @Override
    protected void initData() {
        presenter.getRequest(Apis.URL_RECEIVE_ADDRESS_LIST_GET,AddressBean.class);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new PresenterImpl(this);
        ButterKnife.bind(this);
        //创建布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        addressRecycle.setLayoutManager(layoutManager);
        //创建适配器
        addressAdaper = new AddressAdaper(this);
        addressRecycle.setAdapter(addressAdaper);
    }


    @OnClick({R.id.accomplish, R.id.btu_newaddress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.accomplish:
                break;
            case R.id.btu_newaddress:
                break;
                default:
                    break;
        }
    }

    @Override
    public void requestData(Object o) {
        if(o instanceof AddressBean){
            AddressBean addressBean = (AddressBean) o;
            if(addressBean==null || !addressBean.isSuccess()){
                Toast.makeText(AddressActivity.this,addressBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else{
                addressAdaper.setmResult(addressBean.getResult());
            }
        }
    }

    @Override
    public void requestFail(Object o) {
        if (o instanceof Exception) {
            Exception e = (Exception) o;
            e.printStackTrace();
        }
        Toast.makeText(AddressActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
