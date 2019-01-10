package com.example.wdshop.home.adaper;

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
import com.example.wdshop.home.bean.SearchBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 根据关键字查询商品的Adaper
 * */
public class SearchAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SearchBean.ResultBean> mResult;
    private Context mContext;

    public SearchAdaper(Context mContext) {
        this.mContext = mContext;
        mResult = new ArrayList<>();
    }

    public void setmResult(List<SearchBean.ResultBean> results) {
        mResult.clear();
        if (results != null) {
            mResult.addAll(results);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_item, viewGroup, false);
        ViewHolderSearch holderSearch = new ViewHolderSearch(view);
        return holderSearch;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolderSearch holderSearch = (ViewHolderSearch) viewHolder;
        holderSearch.findPrice.setText("￥:"+mResult.get(i).getPrice());
        holderSearch.findTitle.setText(mResult.get(i).getCommodityName());
        holderSearch.searchSimple.setImageURI(Uri.parse(mResult.get(i).getMasterPic()));
        holderSearch.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchCallBack!=null){
                    searchCallBack.callBaack(mResult.get(i).getCommodityId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResult.size();
    }

    class ViewHolderSearch extends RecyclerView.ViewHolder {
        @BindView(R.id.search_simple)
        SimpleDraweeView searchSimple;
        @BindView(R.id.find_title)
        TextView findTitle;
        @BindView(R.id.find_price)
        TextView findPrice;
        @BindView(R.id.search_relate)
        RelativeLayout layout;
        public ViewHolderSearch(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    //定义接口
    private SearchCallBack searchCallBack;
    public void setSearchCallBack(SearchCallBack searchCallBack){
        this.searchCallBack = searchCallBack;
    }
    public interface SearchCallBack{
        void callBaack(int commodityId);
    }
}
