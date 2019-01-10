package com.example.wdshop.mine.activity;

import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.activity.BaseActivity;
import com.example.wdshop.mine.adaper.FootPrintAdaper;
import com.example.wdshop.api.Apis;
import com.example.wdshop.mine.bean.FootPrintBean;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.view.Iview;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的足迹Activity
 */
public class FootPrintActivity extends BaseActivity implements Iview {

    @BindView(R.id.foot_xrecycle)
    XRecyclerView footXrecycle;
    private PresenterImpl presenter;
    private int mPage;
    private int mCount=5;
    private FootPrintAdaper printAdaper;

    /**
     * 加载布局
     */
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_foot_print;
    }

    /**
     * 加载数据
     */
    @Override
    protected void initData() {
        presenter.getRequest(String.format(Apis.URL_BROWSE_LIST_GET,mPage,mCount),FootPrintBean.class);
    }

    /**
     * 初始化视图
     */
    @Override
    protected void initView(Bundle savedInstanceState) {
        mPage = 1;
        presenter = new PresenterImpl(this);
        ButterKnife.bind(this);
        //创建布局管理器
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        footXrecycle.setLayoutManager(layoutManager);
        //创建适配器
        printAdaper = new FootPrintAdaper(this);
        footXrecycle.setAdapter(printAdaper);
        footXrecycle.setLoadingMoreEnabled(true);
        footXrecycle.setPullRefreshEnabled(true);
        footXrecycle.setLoadingListener(new XRecyclerView.LoadingListener() {
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
    /**
     * 请求成功
     * */
    @Override
    public void requestData(Object o) {
        if(o instanceof FootPrintBean){
            FootPrintBean printBean = (FootPrintBean) o;
            if(printBean==null || !printBean.isSuccess()){
                Toast.makeText(FootPrintActivity.this,printBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else {
                if(mPage == 1){
                    printAdaper.setmResult(printBean.getResult());
                }else{
                    printAdaper.addmResult(printBean.getResult());
                }
                mPage++;
                footXrecycle.refreshComplete();
                footXrecycle.loadMoreComplete();
            }
        }
    }
    /**
     * 加载失败
     * */
    @Override
    public void requestFail(Object o) {
        if (o instanceof Exception) {
            Exception e = (Exception) o;
            e.printStackTrace();
        }
        Toast.makeText(FootPrintActivity.this, "请求错误", Toast.LENGTH_SHORT).show();
    }

}
