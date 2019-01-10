package com.example.wdshop.mine.adaper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.wdshop.R;
import com.example.wdshop.mine.bean.AddressBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 我的收获地址的Adaper
 * */
public class AddressAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AddressBean.ResultBean> mResult;
    private Context mContext;
    private int position;
    public AddressAdaper(Context mContext) {
        this.mContext = mContext;
        mResult = new ArrayList<>();
    }

    public void setmResult(List<AddressBean.ResultBean> results) {
        mResult.clear();
        if (results != null) {
            mResult.addAll(results);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.address_item, viewGroup, false);
        ViewHolderAddress holderAddress = new ViewHolderAddress(view);
        return holderAddress;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final ViewHolderAddress holderAddress = (ViewHolderAddress) viewHolder;
        holderAddress.name.setText(mResult.get(i).getRealName());
        holderAddress.telephone.setText(mResult.get(i).getPhone());
        holderAddress.address.setText(mResult.get(i).getAddress());
        holderAddress.updateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBackUpdata!=null){
                    callBackUpdata.callBack(mResult.get(i));
                }
            }
        });
        if(mResult.get(i).getWhetherDefault() == 1){
            holderAddress.radio.setChecked(true);
        }else{
            holderAddress.radio.setChecked(false);
        }
       holderAddress.radio.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //设置默认值
               position=i;
               setAllunCheck();
               if(callBackDefault!=null){
                   callBackDefault.callBack(mResult.get(i).getId());
               }
           }
       });
    }
    //改变默认值
    private void setAllunCheck() {
        int size = mResult.size();
        for (int i=0;i<size;i++){
            if(i==position){
                mResult.get(i).setWhetherDefault(1);
            }else{
                mResult.get(i).setWhetherDefault(2);
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mResult.size();
    }

    class ViewHolderAddress extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.telephone)
        TextView telephone;
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.radio)
        RadioButton radio;
        @BindView(R.id.default_address)
        TextView defaultAddress;
        @BindView(R.id.update_address)
        Button updateAddress;
        @BindView(R.id.del_address)
        Button delAddress;
        public ViewHolderAddress(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    //定义接口
    private CallBackUpdata callBackUpdata;
    public void setCallBackUpdata(CallBackUpdata callBackUpdata){
        this.callBackUpdata = callBackUpdata;
    }
    public interface CallBackUpdata{
        void callBack(AddressBean.ResultBean resultBean);
    }
    //定义默认选中的接口
    private CallBackDefault callBackDefault;
    public void setCallBackDefault(CallBackDefault callBackDefault){
        this.callBackDefault = callBackDefault;
    }
    public interface CallBackDefault{
        void callBack(int id);
    }
}
