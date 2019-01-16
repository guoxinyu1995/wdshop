package com.example.wdshop.order.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.api.Apis;
import com.example.wdshop.fragment.BaseFragment;
import com.example.wdshop.order.activity.RemaitActivity;
import com.example.wdshop.order.adaper.OrderRemaitAdaper;
import com.example.wdshop.order.bean.DeleteOrderBean;
import com.example.wdshop.order.bean.OrderBean;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.view.Iview;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 待评价的fragment
 */
public class RemaitFragment extends BaseFragment implements Iview {
    @BindView(R.id.recycleview)
    XRecyclerView recycleview;
    Unbinder unbinder;
    private PresenterImpl presenter;
    private int status = 3;
    private int page;
    private int count = 5;
    private OrderRemaitAdaper remaitAdaper;
    private List<OrderBean.OrderListBean> orderList;

    /**
     * 加载数据
     */
    @Override
    protected void initData() {
        presenter.getRequest(String.format(Apis.URL_FIND_ORDER_LIST_BY_STATUS_GET, status, page, count), OrderBean.class);
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
        remaitAdaper = new OrderRemaitAdaper(getActivity());
        recycleview.setAdapter(remaitAdaper);
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
        //删除
        remaitAdaper.setCallBackDel(new OrderRemaitAdaper.CallBackDel() {
            @Override
            public void callBack(String orderId, int position) {
                remaitAdaper.setDel(position);
                presenter.deleteRequest(String.format(Apis.URL_DELETE_ORDER_DELETE, orderId), DeleteOrderBean.class);
            }
        });
        //去平价
        remaitAdaper.setCallBackRemait(new OrderRemaitAdaper.CallBackRemait() {
            @Override
            public void callBackRem(String orderId, OrderBean.OrderListBean.DetailListBean dataBean) {
                Intent intent = new Intent(getActivity(), RemaitActivity.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("dataBean", dataBean);
                startActivity(intent);
            }
        });
    }

    /**
     * 加载布局
     */
    @Override
    protected int getLayoutResId() {
        return R.layout.order_remait_fragment;
    }

    /**
     * 请求成功
     */
    @Override
    public void requestData(Object o) {
        if (o instanceof OrderBean) {
            OrderBean orderBean = (OrderBean) o;
            orderList = orderBean.getOrderList();
            if (orderBean == null || !orderBean.isSuccess()) {
                Toast.makeText(getActivity(), orderBean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                if (page == 1) {
                    remaitAdaper.setmOrder(orderBean.getOrderList());
                } else {
                    remaitAdaper.addmOrder(orderBean.getOrderList());
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
        presenter.onDetach();
    }
}
