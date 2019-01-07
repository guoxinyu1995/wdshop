package com.example.wdshop.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.adaper.CircleAdaper;
import com.example.wdshop.api.Apis;
import com.example.wdshop.bean.CircleCanclePraiseBean;
import com.example.wdshop.bean.CircleGivePraiseBean;
import com.example.wdshop.bean.CircleBean;
import com.example.wdshop.login.bean.LoginBean;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.view.Iview;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CircleFragment extends BaseFragment implements Iview {

    @BindView(R.id.xrecycleview)
    XRecyclerView xrecycleview;
    Unbinder unbinder;
    private PresenterImpl presenter;
    private CircleAdaper adaper;
    private int mPage;
    private int count = 5;
    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        Intent intent = getActivity().getIntent();
        LoginBean.ResultBean result = (LoginBean.ResultBean) intent.getSerializableExtra("result");
        presenter.getRequest(String.format(Apis.URL_FIND_CIRCLE_LIST_GET,mPage, count), CircleBean.class);
    }

    /**
     * 初始化view
     */
    @Override
    protected void initView(View view) {
        mPage = 1;
        unbinder = ButterKnife.bind(this, view);
        presenter = new PresenterImpl(this);
        //创建布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        xrecycleview.setLayoutManager(layoutManager);
        //创建适配器
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
            public void callBack(int i, int position, int id) {
                if (i == 1) {
                    //取消点赞
                    presenter.deleteRequest(String.format(Apis.URL_CIRCLE_CANCLE_DELETE, id), CircleCanclePraiseBean.class);
                    adaper.getCancelPraise(position);
                } else if (i == 2) {
                    //点赞
                    Map<String, String> addMap = new HashMap<>();
                    addMap.put("circleId", String.valueOf(id));
                    presenter.postRequest(Apis.URL_CIRCLE_ADD_POST, addMap, CircleGivePraiseBean.class);
                    adaper.getGivePraise(position);
                }
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

    /**
     * 请求成功
     */
    @Override
    public void requestData(Object o) {
        //圈子列表
        if (o instanceof CircleBean) {
            CircleBean bean = (CircleBean) o;
            if (bean == null || !bean.isSuccess()) {
                Toast.makeText(getActivity(), bean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                adaper.setmResult(bean.getResult());
                if (mPage == 1) {
                    adaper.setmResult(bean.getResult());
                } else {
                    adaper.addmResult(bean.getResult());
                }
                mPage++;
                xrecycleview.refreshComplete();
                xrecycleview.loadMoreComplete();

            }
            //点赞
        } else if (o instanceof CircleGivePraiseBean) {
            CircleGivePraiseBean givePraiseBean = (CircleGivePraiseBean) o;
            Toast.makeText(getActivity(), givePraiseBean.getMessage(), Toast.LENGTH_SHORT).show();
            //取消点赞
        } else if (o instanceof CircleCanclePraiseBean) {
            CircleCanclePraiseBean canclePraiseBean = (CircleCanclePraiseBean) o;
            Toast.makeText(getActivity(), canclePraiseBean.getMessage(), Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getActivity(), "请求错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        presenter.onDetach();
    }
}
