package com.example.wdshop.adaper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wdshop.R;
import com.example.wdshop.bean.CatagralTwoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CatagralTwoAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CatagralTwoBean.ResultBean> mResult;
    private Context mContext;

    public CatagralTwoAdaper(Context mContext) {
        this.mContext = mContext;
        mResult = new ArrayList<>();
    }

    public void setmResult(List<CatagralTwoBean.ResultBean> results) {
        mResult.clear();
        if (results != null) {
            mResult.addAll(results);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.catagratwo_item, viewGroup, false);
        ViewHolderCatagralTwo catagralTwo = new ViewHolderCatagralTwo(view);
        return catagralTwo;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolderCatagralTwo catagralTwo = (ViewHolderCatagralTwo) viewHolder;
        catagralTwo.name.setText(mResult.get(i).getName());
        catagralTwo.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(catagralTwoCallBack!=null){
                    catagralTwoCallBack.callBack(mResult.get(i).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResult.size();
    }

    class ViewHolderCatagralTwo extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        public ViewHolderCatagralTwo(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    //定义接口
    private CatagralTwoCallBack catagralTwoCallBack;
    public void setCatagralTwoCallBack(CatagralTwoCallBack catagralTwoCallBack){
        this.catagralTwoCallBack = catagralTwoCallBack;
    }
    public interface CatagralTwoCallBack{
        void callBack(String id);
    }
}
