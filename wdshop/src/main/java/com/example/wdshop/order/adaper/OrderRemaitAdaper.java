package com.example.wdshop.order.adaper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wdshop.R;
import com.example.wdshop.order.bean.OrderBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 待评价一级Adaper
 * */
public class OrderRemaitAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<OrderBean.OrderListBean> mOrder;
    private Context mContext;

    public OrderRemaitAdaper(Context mContext) {
        this.mContext = mContext;
        mOrder = new ArrayList<>();
    }

    public void setmOrder(List<OrderBean.OrderListBean> orders) {
        mOrder.clear();
        if (orders != null) {
            mOrder.addAll(orders);
        }
        notifyDataSetChanged();
    }

    public void addmOrder(List<OrderBean.OrderListBean> orders) {
        if (orders != null) {
            mOrder.addAll(orders);
        }
        notifyDataSetChanged();
    }
    //删除
    public void setDel(int position){
        mOrder.remove(position);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_remait_fragment_item, viewGroup, false);
        ViewHolderRemait holderRemait = new ViewHolderRemait(view);
        return holderRemait;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolderRemait holderRemait = (ViewHolderRemait) viewHolder;
        holderRemait.mark.setText(mOrder.get(i).getOrderId());
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        holderRemait.recycleTitle.setLayoutManager(layoutManager);
        OrderRemaitItemAdaper remaitItemAdaper = new OrderRemaitItemAdaper(mContext);
        holderRemait.recycleTitle.setAdapter(remaitItemAdaper);
        remaitItemAdaper.setmData(mOrder.get(i).getDetailList());
        //删除
        holderRemait.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBackDel!=null){
                    callBackDel.callBack(mOrder.get(i).getOrderId(),i);
                }
            }
        });
        //回调
        remaitItemAdaper.setCallBackRemaitItem(new OrderRemaitItemAdaper.CallBackRemaitItem() {
            @Override
            public void callBack(OrderBean.OrderListBean.DetailListBean dataBean) {
                if(callBackRemait!=null){
                    callBackRemait.callBackRem(mOrder.get(i).getOrderId(),dataBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrder.size();
    }

    class ViewHolderRemait extends RecyclerView.ViewHolder {
        @BindView(R.id.dingdan)
        TextView dingdan;
        @BindView(R.id.mark)
        TextView mark;
        @BindView(R.id.delete)
        TextView delete;
        @BindView(R.id.recycle_title)
        RecyclerView recycleTitle;
        @BindView(R.id.order_constr)
        ConstraintLayout orderConstr;
        public ViewHolderRemait(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    /**定义接口删除订单*/
    private CallBackDel callBackDel;
    public void setCallBackDel(CallBackDel callBackDel){
        this.callBackDel = callBackDel;
    }
    public interface CallBackDel{
        void callBack(String orderId,int position);
    }
    //定义接口回传至
    private CallBackRemait callBackRemait;
    public void setCallBackRemait(CallBackRemait callBackRemait){
        this.callBackRemait = callBackRemait;
    }
    public interface CallBackRemait{
       void callBackRem(String orderId,OrderBean.OrderListBean.DetailListBean dataBean);
    }
}
