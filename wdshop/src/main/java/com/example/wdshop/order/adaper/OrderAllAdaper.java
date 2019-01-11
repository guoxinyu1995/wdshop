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
import android.widget.Button;
import android.widget.TextView;

import com.example.wdshop.R;
import com.example.wdshop.order.bean.OrderBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 全部订单的一级recycleview的适配器
 * */
public class OrderAllAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<OrderBean.OrderListBean> mOrder;
    private Context mContext;

    public OrderAllAdaper(Context mContext) {
        this.mContext = mContext;
        mOrder = new ArrayList<>();
    }
    public void setmOrder(List<OrderBean.OrderListBean> orders){
        mOrder.clear();
        if(orders!=null){
            mOrder.addAll(orders);
        }
        notifyDataSetChanged();
    }
    public void addmOrder(List<OrderBean.OrderListBean> orders){
        if(orders!=null){
            mOrder.addAll(orders);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_all_fragment_item, viewGroup, false);
        ViewHolderAll holderAll = new ViewHolderAll(view);
        return holderAll;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolderAll holderAll = (ViewHolderAll) viewHolder;
        holderAll.mark.setText(mOrder.get(i).getOrderId());
        //设置时间类型
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new java.util.Date(mOrder.get(i).getOrderTime()));
        holderAll.time.setText(date);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        holderAll.recycleTitle.setLayoutManager(layoutManager);
        OrderAllItemAdaper allItemAdaper = new OrderAllItemAdaper(mContext);
        holderAll.recycleTitle.setAdapter(allItemAdaper);
        allItemAdaper.setmData(mOrder.get(i).getDetailList());
        int num = 0;
        List<OrderBean.OrderListBean.DetailListBean> detailList = mOrder.get(i).getDetailList();
        for(OrderBean.OrderListBean.DetailListBean list:detailList){
                num += list.getCommodityCount();
        }
        holderAll.numPrice.setText("共"+num+"件商品，需付款"+mOrder.get(i).getPayAmount());
        //删除订单
        holderAll.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBackAll!=null){
                    callBackAll.callBack(mOrder.get(i).getOrderId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrder.size();
    }

    class ViewHolderAll extends RecyclerView.ViewHolder {
        @BindView(R.id.dingdan)
        TextView dingdan;
        @BindView(R.id.mark)
        TextView mark;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.recycle_title)
        RecyclerView recycleTitle;
        @BindView(R.id.num_price)
        TextView numPrice;
        @BindView(R.id.cancel_button)
        Button cancelButton;
        @BindView(R.id.payment_button)
        Button paymentButton;
        @BindView(R.id.order_constr)
        ConstraintLayout orderConstr;

        public ViewHolderAll(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    //定义接口
    private CallBackAll callBackAll;
    public void setCallBackAll(CallBackAll callBackAll){
        this.callBackAll = callBackAll;
    }
    public interface CallBackAll{
        void callBack(String orderId);
    }
}
