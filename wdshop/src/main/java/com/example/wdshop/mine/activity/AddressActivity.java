package com.example.wdshop.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.activity.BaseActivity;
import com.example.wdshop.mine.adaper.AddressAdaper;
import com.example.wdshop.api.Apis;
import com.example.wdshop.mine.bean.AddressBean;
import com.example.wdshop.mine.bean.DefaultAddressBean;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.view.Iview;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 我的地址的Activity
 * */
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
        addressAdaper.setCallBackUpdata(new AddressAdaper.CallBackUpdata() {
            @Override
            public void callBack(AddressBean.ResultBean resultBean) {
                Intent intent = new Intent(AddressActivity.this,UpdateAddressActivity.class);
                intent.putExtra("resultBean",resultBean);
                startActivityForResult(intent,100);
            }
        });
        //设置默认选中
      addressAdaper.setCallBackDefault(new AddressAdaper.CallBackDefault() {
          @Override
          public void callBack(int id) {
              Map<String,String> map = new HashMap<>();
              map.put("id",String.valueOf(id));
              presenter.postRequest(Apis.URL_DEFAULT_RECEIVE_ADDRESS,map,DefaultAddressBean.class);
          }
      });

    }


    @OnClick({R.id.accomplish, R.id.btu_newaddress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.accomplish:
                break;
            case R.id.btu_newaddress:
                Intent intent = new Intent(AddressActivity.this,NewAddressActivity.class);
                startActivityForResult(intent,100);
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
        }else if(o instanceof DefaultAddressBean){
            DefaultAddressBean defaultAddressBean = (DefaultAddressBean) o;
            Toast.makeText(AddressActivity.this,defaultAddressBean.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 请求失败
     */
    @Override
    public void requestFail(String error) {
        Toast.makeText(AddressActivity.this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==200){
            presenter.getRequest(Apis.URL_RECEIVE_ADDRESS_LIST_GET,AddressBean.class);
        }
    }
}
