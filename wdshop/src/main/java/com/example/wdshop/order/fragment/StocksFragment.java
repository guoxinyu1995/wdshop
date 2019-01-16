package com.example.wdshop.order.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.api.Apis;
import com.example.wdshop.fragment.BaseFragment;
import com.example.wdshop.order.activity.RemaitActivity;
import com.example.wdshop.order.adaper.OrderStockAdaper;
import com.example.wdshop.order.bean.OrderBean;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.view.Iview;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 已完成Fragment
 */
public class StocksFragment extends BaseFragment implements Iview {
    @BindView(R.id.recycleview)
    XRecyclerView recycleview;
    Unbinder unbinder;
    private int status = 9;
    private int page;
    private int count = 5;
    private PresenterImpl presenter;
    private OrderStockAdaper stockAdaper;

    /**
     * 加载数据
     */
    @Override
    protected void initData() {
        presenter.getRequest(String.format(Apis.URL_FIND_ORDER_LIST_BY_STATUS_GET,status,page,count),OrderBean.class);
    }

    /**
     * 初始化view
     */
    @Override
    protected void initView(View view) {
        page = 1;
        presenter = new PresenterImpl(this);
        unbinder = ButterKnife.bind(this, view);
        //创建布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycleview.setLayoutManager(layoutManager);
        //创建适配器
        stockAdaper = new OrderStockAdaper(getActivity());
        recycleview.setAdapter(stockAdaper);
        recycleview.setPullRefreshEnabled(true);
        recycleview.setPullRefreshEnabled(true);
        recycleview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                initData();
            }

            @Override
            public void onLoadMore() {
                initData();
            }
        });
    }

    /**
     * 加载布局
     */
    @Override
    protected int getLayoutResId() {
        return R.layout.order_stocks_fragment;
    }

    /**
     * 请求成功
     */
    @Override
    public void requestData(Object o) {
        if(o instanceof OrderBean){
            OrderBean orderBean = (OrderBean) o;
            if(orderBean==null || !orderBean.isSuccess()){
                Toast.makeText(getActivity(),orderBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else{
                if(page == 1){
                    stockAdaper.setmOrder(orderBean.getOrderList());
                }else{
                    stockAdaper.addmOrder(orderBean.getOrderList());
                }
                page++;
                recycleview.loadMoreComplete();
                recycleview.refreshComplete();
            }
        }
    }

    /**
     * 请求失败
     */
    @Override
    public void requestFail(String erroe) {
        Toast.makeText(getActivity(), erroe, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
