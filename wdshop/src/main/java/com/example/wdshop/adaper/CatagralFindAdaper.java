package com.example.wdshop.adaper;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wdshop.R;
import com.example.wdshop.bean.CatagralFindBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CatagralFindAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CatagralFindBean.ResultBean> mResult;
    private Context mContext;

    public CatagralFindAdaper(Context mContext) {
        this.mContext = mContext;
        mResult = new ArrayList<>();
    }

    public void setmResult(List<CatagralFindBean.ResultBean> results) {
        mResult.clear();
        if (results != null) {
            mResult.addAll(results);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.category_find_item, viewGroup, false);
        ViewHolderCatagralFind catagralFind = new ViewHolderCatagralFind(view);
        return catagralFind;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolderCatagralFind catagralFind = (ViewHolderCatagralFind) viewHolder;
        catagralFind.findTitle.setText(mResult.get(i).getCommodityName());
        catagralFind.findPrice.setText("￥:"+mResult.get(i).getPrice());
        catagralFind.findSimple.setImageURI(Uri.parse(mResult.get(i).getMasterPic()));

        catagralFind.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(findCallBack!=null){
                    findCallBack.callBack(mResult.get(i).getCommodityId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResult.size();
    }

    class ViewHolderCatagralFind extends RecyclerView.ViewHolder {
        @BindView(R.id.find_simple)
        SimpleDraweeView findSimple;
        @BindView(R.id.find_title)
        TextView findTitle;
        @BindView(R.id.find_price)
        TextView findPrice;
        @BindView(R.id.find_relate_click)
        RelativeLayout layout;
        public ViewHolderCatagralFind(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    //定义接口
    private FindCallBack findCallBack;
    public void setFindCallBack(FindCallBack findCallBack){
        this.findCallBack = findCallBack;
    }
    public interface FindCallBack{
        void callBack(int commodityId);
    }
}
