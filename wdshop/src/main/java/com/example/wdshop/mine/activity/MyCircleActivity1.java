package com.example.wdshop.mine.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.activity.BaseActivity;
import com.example.wdshop.api.Apis;
import com.example.wdshop.mine.adaper.MyCircleAdaper;
import com.example.wdshop.mine.adaper.MyCircleAdaper1;
import com.example.wdshop.mine.bean.MyCircleBean;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.view.Iview;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCircleActivity1 extends BaseActivity implements Iview {
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.clrcle_recycle)
    XRecyclerView clrcleRecycle;
    private PresenterImpl presenter;
    private int mPage=1;
    private boolean flag=false;
    private MyCircleAdaper1 circleAdapter1;
    private int mCount = 10;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_circle1;
    }

    @Override
    protected void initData() {
        presenter.getRequest(String.format(Apis.URL_FIND_CIRCLE_BY_ID_GET,mPage,mCount),MyCircleBean.class);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mPage = 1;
        presenter = new PresenterImpl(this);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        clrcleRecycle.setLayoutManager(linearLayoutManager);
        circleAdapter1 = new MyCircleAdaper1(this);
        clrcleRecycle.setAdapter(circleAdapter1);
        clrcleRecycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                initData();
            }

            @Override
            public void onLoadMore() {
                initData();
            }
        });
    }

    @Override
    public void requestData(Object o) {
        if(o instanceof MyCircleBean){
            MyCircleBean circleBean = (MyCircleBean) o;
            if(circleBean == null || !circleBean.isSuccess()){
                Toast.makeText(MyCircleActivity1.this,circleBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else{
                if(mPage == 1){
                    circleAdapter1.setList(circleBean.getResult());
                }else{
                    circleAdapter1.addList(circleBean.getResult());
                }
                mPage++;
                clrcleRecycle.loadMoreComplete();
                clrcleRecycle.refreshComplete();
            }
        }
    }

    @Override
    public void requestFail(String error) {

    }


    @OnClick(R.id.delete)
    public void onViewClicked() {

    }
}
