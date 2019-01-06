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
import com.example.wdshop.bean.HomeFragmentBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommodityAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HomeFragmentBean.ResultBean.RxxpBean.CommodityListBean> mRxxp;
    private Context mContext;

    public CommodityAdaper(Context mContext) {
        this.mContext = mContext;
        mRxxp = new ArrayList<>();
    }
    public void setmRxxp(List<HomeFragmentBean.ResultBean.RxxpBean.CommodityListBean> rxxps){
        mRxxp.addAll(rxxps);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rxxp_item, viewGroup, false);
        ViewHolderRxxp holderRxxp = new ViewHolderRxxp(view);
        return holderRxxp;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolderRxxp holderRxxp = (ViewHolderRxxp) viewHolder;
        holderRxxp.rxxpPrice.setText("￥"+mRxxp.get(i).getPrice());
        holderRxxp.rxxpTitle.setText(mRxxp.get(i).getCommodityName());
        holderRxxp.rxxpImage.setImageURI(Uri.parse(mRxxp.get(i).getMasterPic()));

        holderRxxp.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rxxpCallBack!=null){
                    rxxpCallBack.callBaack(mRxxp.get(i).getCommodityId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRxxp.size();
    }

    class ViewHolderRxxp extends RecyclerView.ViewHolder {
        @BindView(R.id.rxxp_image)
        SimpleDraweeView rxxpImage;
        @BindView(R.id.rxxp_title)
        TextView rxxpTitle;
        @BindView(R.id.rxxp_price)
        TextView rxxpPrice;
        @BindView(R.id.rxxp_relate)
        RelativeLayout layout;
        public ViewHolderRxxp(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    //定义接口
    private RxxpCallBack rxxpCallBack;
    public void setRxxpCallBack(RxxpCallBack rxxpCallBack){
        this.rxxpCallBack = rxxpCallBack;
    }
    public interface RxxpCallBack{
        void callBaack(int commodityId);
    }}
