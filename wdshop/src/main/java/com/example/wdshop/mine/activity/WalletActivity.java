package com.example.wdshop.mine.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.activity.BaseActivity;
import com.example.wdshop.mine.adaper.WalletAdaper;
import com.example.wdshop.api.Apis;
import com.example.wdshop.mine.bean.WalletBean;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.view.Iview;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 我的钱包的Activity
 * */
public class WalletActivity extends BaseActivity implements Iview {
    @BindView(R.id.wallet_back)
    ImageView walletBack;
    @BindView(R.id.balance)
    TextView balance;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.sum)
    TextView sum;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.view_linev)
    View viewLinev;
    @BindView(R.id.wallet_recycle)
    RecyclerView walletRecycle;
    private PresenterImpl presenter;
    private WalletAdaper walletAdaper;
    private int mPage = 1;
    private int mCount = 1;
    /**
     * 加载布局
     */
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_wallet;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        presenter.getRequest(String.format(Apis.URL_FIND_WALLET_GET,mPage,mCount),WalletBean.class);
    }

    /**
     * 初始化view
     */
    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new PresenterImpl(this);
        ButterKnife.bind(this);
        //创建布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        walletRecycle.addItemDecoration(decoration);
        walletRecycle.setLayoutManager(layoutManager);
        //创建适配器
        walletAdaper = new WalletAdaper(this);
        walletRecycle.setAdapter(walletAdaper);
    }

    /**
     * 请求成功
     */
    @Override
    public void requestData(Object o) {
        if(o instanceof WalletBean){
            WalletBean walletBean = (WalletBean) o;
            WalletBean.ResultBean result = walletBean.getResult();

            if(walletBean == null || !walletBean.isSuccess()){
                Toast.makeText(WalletActivity.this,walletBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else{
                money.setText(result.getBalance()+"");
                walletAdaper.setmBean(walletBean.getResult().getDetailList());
            }
        }
    }
    /**
     * 请求失败
     */
    @Override
    public void requestFail(String error) {
        Toast.makeText(WalletActivity.this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
