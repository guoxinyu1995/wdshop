package com.example.wdshop.adaper;

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
import com.example.wdshop.bean.AddressBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AddressBean.ResultBean> mResult;
    private Context mContext;

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolderAddress holderAddress = (ViewHolderAddress) viewHolder;
        holderAddress.name.setText(mResult.get(i).getRealName());
        holderAddress.telephone.setText(mResult.get(i).getPhone());
        holderAddress.address.setText(mResult.get(i).getAddress());
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
}
