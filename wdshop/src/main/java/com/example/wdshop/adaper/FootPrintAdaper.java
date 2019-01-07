package com.example.wdshop.adaper;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wdshop.R;
import com.example.wdshop.bean.FootPrintBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FootPrintAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FootPrintBean.ResultBean> mResult;
    private Context mContext;

    public FootPrintAdaper(Context mContext) {
        this.mContext = mContext;
        mResult = new ArrayList<>();
    }
    public void setmResult(List<FootPrintBean.ResultBean> results){
        mResult.clear();
        if(results!=null){
            mResult.addAll(results);
        }
        notifyDataSetChanged();
    }
    public void addmResult(List<FootPrintBean.ResultBean> results){
        if(results!=null){
            mResult.addAll(results);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.footprint_item, viewGroup, false);
        ViewHolderFoot holderFoot = new ViewHolderFoot(view);
        return holderFoot;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolderFoot holderFoot = (ViewHolderFoot) viewHolder;
        holderFoot.fpTitle.setText(mResult.get(i).getCommodityName());
        holderFoot.fpPrice.setText("￥"+mResult.get(i).getPrice());
        holderFoot.fpImage.setImageURI(Uri.parse(mResult.get(i).getMasterPic()));
        holderFoot.browsenum.setText("已浏览"+mResult.get(i).getBrowseNum()+"次");
        //设置时间类型
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new java.util.Date(mResult.get(i).getBrowseTime()));
        holderFoot.browsetime.setText(date);
    }

    @Override
    public int getItemCount() {
        return mResult.size();
    }

    class ViewHolderFoot extends RecyclerView.ViewHolder {
        @BindView(R.id.fp_image)
        SimpleDraweeView fpImage;
        @BindView(R.id.fp_title)
        TextView fpTitle;
        @BindView(R.id.fp_price)
        TextView fpPrice;
        @BindView(R.id.browsenum)
        TextView browsenum;
        @BindView(R.id.browsetime)
        TextView browsetime;
        public ViewHolderFoot(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
