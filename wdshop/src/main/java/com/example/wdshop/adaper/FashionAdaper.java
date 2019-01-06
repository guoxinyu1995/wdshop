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

public class FashionAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HomeFragmentBean.ResultBean.MlssBean.CommodityListBeanXX> mMlss;
    private Context mContext;

    public FashionAdaper(Context mContext) {
        this.mContext = mContext;
        mMlss = new ArrayList<>();
    }

    public void setmMlss(List<HomeFragmentBean.ResultBean.MlssBean.CommodityListBeanXX> mlsss) {
        mMlss.addAll(mlsss);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mlss_item, viewGroup, false);
        ViewHolderMlss holderMlss = new ViewHolderMlss(view);
        return holderMlss;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolderMlss holderMlss = (ViewHolderMlss) viewHolder;
        holderMlss.mlssPrice.setText("￥"+mMlss.get(i).getPrice());
        holderMlss.mlssTitle.setText(mMlss.get(i).getCommodityName());
        holderMlss.mlssImage.setImageURI(Uri.parse(mMlss.get(i).getMasterPic()));

        holderMlss.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mlssCallBack!=null){
                    mlssCallBack.callBack(mMlss.get(i).getCommodityId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMlss.size();
    }

    class ViewHolderMlss extends RecyclerView.ViewHolder {
        @BindView(R.id.mlss_image)
        SimpleDraweeView mlssImage;
        @BindView(R.id.mlss_title)
        TextView mlssTitle;
        @BindView(R.id.mlss_price)
        TextView mlssPrice;
        @BindView(R.id.mlss_relate)
        RelativeLayout layout;
        public ViewHolderMlss(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    //定义接口
    private MlssCallBack mlssCallBack;
    public void setMlssCallBack(MlssCallBack mlssCallBack){
        this.mlssCallBack = mlssCallBack;
    }
    public interface MlssCallBack{
        void callBack(int commodityId);
    }
}
