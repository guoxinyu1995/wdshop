package com.example.wdshop.adaper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wdshop.R;
import com.example.wdshop.bean.CatatgralOneBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CatagralOneAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CatatgralOneBean.ResultBean> mResult;
    private Context mContext;

    public CatagralOneAdaper(Context mContext) {
        this.mContext = mContext;
        mResult = new ArrayList<>();
    }
    public void setmResult(List<CatatgralOneBean.ResultBean> results){
        mResult.clear();
        if(results!=null){
            mResult.addAll(results);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.catagraone_item, viewGroup, false);
        ViewHolderCatagralOne catagralOne = new ViewHolderCatagralOne(view);
        return catagralOne;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolderCatagralOne catagralOne = (ViewHolderCatagralOne) viewHolder;
        catagralOne.name.setText(mResult.get(i).getName());
        catagralOne.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(catagralCallBack!=null){
                    catagralCallBack.callBack(mResult.get(i).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResult.size();
    }

    class ViewHolderCatagralOne extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        public ViewHolderCatagralOne(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    //定义接口
    private CatagralCallBack catagralCallBack;
    public void setCatagralCallBack(CatagralCallBack catagralCallBack){
        this.catagralCallBack = catagralCallBack;
    }
    public interface CatagralCallBack{
        void callBack(String id);
    }
}
