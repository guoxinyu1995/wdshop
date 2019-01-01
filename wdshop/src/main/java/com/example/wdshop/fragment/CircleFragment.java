package com.example.wdshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.activity.HomeActivity;
import com.example.wdshop.activity.LoginActivity;
import com.example.wdshop.adaper.CircleAdaper;
import com.example.wdshop.api.Apis;
import com.example.wdshop.bean.CircleBean;
import com.example.wdshop.bean.LoginBean;
import com.example.wdshop.bean.RegisterBean;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.view.Iview;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CircleFragment extends BaseFragment implements Iview {

    @BindView(R.id.xrecycleview)
    XRecyclerView xrecycleview;
    Unbinder unbinder;
    private PresenterImpl presenter;
    private CircleAdaper adaper;
    private int mPage ;
    private int count = 5;
    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        Intent intent =getActivity().getIntent();
        LoginBean.ResultBean result = (LoginBean.ResultBean) intent.getSerializableExtra("result");
        int userId = result.getUserId();
        String sessionId = result.getSessionId();
        presenter.getRequest(String.format(Apis.URL_FIND_CIRCLE_LIST_GET,userId,sessionId,mPage,count),CircleBean.class);
    }

    /**
     * 初始化view
     */
    @Override
    protected void initView(View view) {
        mPage = 1;
        presenter = new PresenterImpl(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        xrecycleview.setLayoutManager(layoutManager);
        adaper = new CircleAdaper(getActivity());
        xrecycleview.setAdapter(adaper);
        xrecycleview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                initData();
            }

            @Override
            public void onLoadMore() {
                initData();
            }
        });
        adaper.setClickCallBack(new CircleAdaper.ClickCallBack() {
            @Override
            public void callBack(int i, int position) {
                adaper.setPraise(i,position);
            }
        });
    }

    /**
     * 初始化视图
     */
    @Override
    protected int getLayoutResId() {
        return R.layout.circle_fragment;
    }

    @Override
    public void requestData(Object o) {
        if(o instanceof CircleBean){
            CircleBean bean = (CircleBean) o;
            if(bean==null || !bean.isSuccess()){
                Toast.makeText(getActivity(),bean.getMessage(),Toast.LENGTH_SHORT).show();
            }else{
                adaper.setmResult(bean.getResult());
                if(mPage == 1){
                    adaper.setmResult(bean.getResult());
                }else{
                    adaper.addmResult(bean.getResult());
                }
                mPage++;
                xrecycleview.refreshComplete();
                xrecycleview.loadMoreComplete();

            }
        }
    }

    @Override
    public void requestFail(Object o) {
        if (o instanceof Exception) {
            Exception e = (Exception) o;
            e.printStackTrace();
        }
        Toast.makeText(getActivity(), "网络请求错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
