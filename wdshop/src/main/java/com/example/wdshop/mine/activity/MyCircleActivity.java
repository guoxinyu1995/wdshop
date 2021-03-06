package com.example.wdshop.mine.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.activity.BaseActivity;
import com.example.wdshop.api.Apis;
import com.example.wdshop.mine.bean.DeleteCircleBean;
import com.example.wdshop.mine.adaper.MyCircleAdaper;
import com.example.wdshop.mine.bean.MyCircleBean;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.view.Iview;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCircleActivity extends BaseActivity implements Iview {
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.clrcle_recycle)
    XRecyclerView clrcleRecycle;
    private PresenterImpl presenter;
    private int mPage=1;
    private boolean falg=true;
    private MyCircleAdaper circleAdapter;
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
        circleAdapter = new MyCircleAdaper(this);
        clrcleRecycle.setAdapter(circleAdapter);
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
                Toast.makeText(MyCircleActivity.this,circleBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else{
                if(mPage == 1){
                    circleAdapter.setList(circleBean.getResult());
                }else{
                    circleAdapter.addList(circleBean.getResult());
                }
                mPage++;
                clrcleRecycle.loadMoreComplete();
                clrcleRecycle.refreshComplete();
            }
        }else if(o instanceof DeleteCircleBean){
            DeleteCircleBean deleteCircleBean = (DeleteCircleBean) o;
            if(deleteCircleBean == null || !deleteCircleBean.isSuccess()){
                Toast.makeText(MyCircleActivity.this,deleteCircleBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else{
                mPage=1;
                initData();
            }
        }
    }

    @Override
    public void requestFail(String error) {
        Toast.makeText(MyCircleActivity.this,error,Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.delete)
    public void onViewClicked() {
        if (falg){
            circleAdapter.setCheckbox(true);
        }else {
            circleAdapter.setCheckbox(false);
            List<MyCircleBean.ResultBean> list = circleAdapter.getList();
            String string="";
            for (int i =0;i<list.size();i++){
                if (list.get(i).isCheck()){
                    string+=list.get(i).getId()+",";
                }
            }

            //请求删除圈子数据
            if (!string.equals("")){
                String substring = string.substring(0, string.length() - 1);
                Log.i("TAG",substring);
                presenter.deleteRequest(String.format(Apis.URL_DELETE_CIRCLE_DELETE,substring),DeleteCircleBean.class);
            }

        }
        falg=!falg;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
