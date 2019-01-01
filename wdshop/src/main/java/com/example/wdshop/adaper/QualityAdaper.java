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
import com.example.wdshop.bean.HomeFragmentBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QualityAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HomeFragmentBean.ResultBean.PzshBean.CommodityListBeanX> mPzsh;
    private Context mContext;

    public QualityAdaper(Context mContext) {
        this.mContext = mContext;
        mPzsh = new ArrayList<>();
    }

    public void setmPzsh(List<HomeFragmentBean.ResultBean.PzshBean.CommodityListBeanX> pzshs) {
        mPzsh.addAll(pzshs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pzsh_item, viewGroup, false);
        ViewHolderPzsh holderPzsh = new ViewHolderPzsh(view);
        return holderPzsh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolderPzsh holderPzsh = (ViewHolderPzsh) viewHolder;
        holderPzsh.pzshTitle.setText(mPzsh.get(i).getCommodityName());
        holderPzsh.pzshPrice.setText("￥"+mPzsh.get(i).getPrice());
        holderPzsh.pzshImage.setImageURI(Uri.parse(mPzsh.get(i).getMasterPic()));
    }

    @Override
    public int getItemCount() {
        return mPzsh.size();
    }

    class ViewHolderPzsh extends RecyclerView.ViewHolder {
        @BindView(R.id.pzsh_image)
        SimpleDraweeView pzshImage;
        @BindView(R.id.pzsh_title)
        TextView pzshTitle;
        @BindView(R.id.pash_price)
        TextView pzshPrice;
        public ViewHolderPzsh(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
