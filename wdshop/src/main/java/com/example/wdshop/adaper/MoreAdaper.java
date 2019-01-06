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
import com.example.wdshop.bean.MoreBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MoreBean.ResultBean> mResult;
    private Context mContext;

    public MoreAdaper(Context mContext) {
        this.mContext = mContext;
        mResult = new ArrayList<>();
    }
    public void setmResult(List<MoreBean.ResultBean> result){
        mResult.clear();
        if(result!=null){
            mResult.addAll(result);
        }
        notifyDataSetChanged();
    }
    public void addmResult(List<MoreBean.ResultBean> result){
        if(result!=null){
            mResult.addAll(result);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.more_item, viewGroup, false);
        ViewHolderMore holderMore = new ViewHolderMore(view);
        return holderMore;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolderMore holderMore = (ViewHolderMore) viewHolder;
        holderMore.morePrice.setText("￥"+mResult.get(i).getPrice());
        holderMore.moreTitle.setText(mResult.get(i).getCommodityName());
        holderMore.moreImage.setImageURI(Uri.parse(mResult.get(i).getMasterPic()));
        //点击
        holderMore.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(moreCallBack!=null){
                    moreCallBack.callBaack(mResult.get(i).getCommodityId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResult.size();
    }

    class ViewHolderMore extends RecyclerView.ViewHolder {
        @BindView(R.id.more_image)
        SimpleDraweeView moreImage;
        @BindView(R.id.more_title)
        TextView moreTitle;
        @BindView(R.id.more_price)
        TextView morePrice;
        @BindView(R.id.more_relate)
        RelativeLayout layout;
        public ViewHolderMore(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    //定义接口
    private MoreCallBack moreCallBack;
    public void setMoreCallBack(MoreCallBack moreCallBack){
        this.moreCallBack = moreCallBack;
    }
    public interface MoreCallBack{
        void callBaack(int commodityId);
    }
}
