package com.example.wdshop.shoppingcart.adaper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wdshop.R;
import com.example.wdshop.mine.bean.AddressBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 提交订单地址的Adaper
 * */
public class CloseAddressAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AddressBean.ResultBean> mResult;
    private Context mContext;

    public CloseAddressAdaper(Context mContext) {
        this.mContext = mContext;
        mResult = new ArrayList<>();
    }
    public void setmResult(List<AddressBean.ResultBean> result){
        mResult.clear();
        if(result!=null){
            mResult.addAll(result);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.close_shopping_address_item, viewGroup, false);
        ViewHolderCloseAddress holderCloseAddress = new ViewHolderCloseAddress(view);
        return holderCloseAddress;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolderCloseAddress holderCloseAddress = (ViewHolderCloseAddress) viewHolder;
        holderCloseAddress.address.setText(mResult.get(i).getAddress());
        holderCloseAddress.phone.setText(mResult.get(i).getPhone());
        holderCloseAddress.realName.setText(mResult.get(i).getRealName());
        holderCloseAddress.butDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBackCloseAddress!=null){
                    callBackCloseAddress.callBack(mResult.get(i));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResult.size();
    }

    class ViewHolderCloseAddress extends RecyclerView.ViewHolder {
        @BindView(R.id.realName)
        TextView realName;
        @BindView(R.id.phone)
        TextView phone;
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.but_delete)
        TextView butDelete;
        public ViewHolderCloseAddress(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    //定义接口
    private CallBackCloseAddress callBackCloseAddress;
    public void setCallBackCloseAddress(CallBackCloseAddress callBackCloseAddress){
        this.callBackCloseAddress = callBackCloseAddress;
    }
    public interface CallBackCloseAddress{
        void callBack(AddressBean.ResultBean resultBean);
    }
}
