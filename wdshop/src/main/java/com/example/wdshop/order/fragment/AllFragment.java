package com.example.wdshop.order.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.api.Apis;
import com.example.wdshop.fragment.BaseFragment;
import com.example.wdshop.order.activity.PayMentActivity;
import com.example.wdshop.order.activity.RemaitActivity;
import com.example.wdshop.order.adaper.OrdelAllAdaper;
import com.example.wdshop.order.adaper.OrderWaitAdaper;
import com.example.wdshop.order.bean.DeleteOrderBean;
import com.example.wdshop.order.bean.OrderBean;
import com.example.wdshop.order.bean.TakeBean;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.view.Iview;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 全部订单的Fragment
 */
public class AllFragment extends BaseFragment implements Iview {
    @BindView(R.id.recycleview)
    XRecyclerView recycleview;
    Unbinder unbinder;
    private int status = 0;
    private int page;
    private int count = 5;
    private PresenterImpl presenter;
    private OrdelAllAdaper allAdaper;
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
        allAdaper = new OrdelAllAdaper(getActivity());
        recycleview.setAdapter(allAdaper);
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
        //取消订单
        allAdaper.setCallBackDel(new OrdelAllAdaper.CallBackDel() {
            @Override
            public void callBack(String orderId, int position) {
                allAdaper.setDel(position);
                presenter.deleteRequest(String.format(Apis.URL_DELETE_ORDER_DELETE,orderId),DeleteOrderBean.class);
            }
        });
        //去支付
        allAdaper.setCallBackPay(new OrdelAllAdaper.CallBackPay() {
            @Override
            public void callBack(String orderId, Double payAmount) {
                Intent intent = new Intent(getActivity(),PayMentActivity.class);
                intent.putExtra("orderId",orderId);
                intent.putExtra("payAmount",payAmount);
                startActivity(intent);
            }
        });
        //确认收货
       allAdaper.setCallBackWait(new OrdelAllAdaper.CallBackWait() {
           @Override
           public void callBack(String orderId) {
               Map<String,String> map = new HashMap<>();
               map.put("orderId",orderId);
               presenter.putRequest(Apis.URL_CONFIRM_RECEIPT_PUT,map,TakeBean.class);
           }
       });
    }

    /**
     * 加载布局
     */
    @Override
    protected int getLayoutResId() {
        return R.layout.order_all_fragment;
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
                    allAdaper.setmOrder(orderBean.getOrderList());
                }else{
                    allAdaper.addmOrder(orderBean.getOrderList());
                }
                page++;
                recycleview.loadMoreComplete();
                recycleview.refreshComplete();
            }
        }else if(o instanceof TakeBean){
            TakeBean takeBean = (TakeBean) o;
            Toast.makeText(getActivity(),takeBean.getMessage(),Toast.LENGTH_SHORT).show();
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
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        presenter.onDetach();
    }
}
