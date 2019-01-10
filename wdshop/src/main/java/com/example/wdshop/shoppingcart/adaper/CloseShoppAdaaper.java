package com.example.wdshop.shoppingcart.adaper;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wdshop.R;
import com.example.wdshop.shoppingcart.bean.FindShoppingCartBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 提交订单展示商品的Adaper
 * */
public class CloseShoppAdaaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<FindShoppingCartBean.ResultBean> list;
    private Context mContext;

    public CloseShoppAdaaper(Context mContext) {
        this.mContext = mContext;
        list = new ArrayList<>();
    }

    public void setList(ArrayList<FindShoppingCartBean.ResultBean> result) {
        list.clear();
        if (result != null) {
            list.addAll(result);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.close_shopping_item, viewGroup, false);
        ViewHolderClose holderClose = new ViewHolderClose(view);
        return holderClose;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolderClose holderClose = (ViewHolderClose) viewHolder;
        holderClose.cartImage.setImageURI(Uri.parse(list.get(i).getPic()));
        holderClose.cartPrice.setText("￥"+list.get(i).getPrice());
        holderClose.cartTitle.setText(list.get(i).getCommodityName());
        holderClose.num.setText("共"+list.get(i).getCount()+"件");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolderClose extends RecyclerView.ViewHolder {
        @BindView(R.id.cart_image)
        SimpleDraweeView cartImage;
        @BindView(R.id.cart_title)
        TextView cartTitle;
        @BindView(R.id.cart_price)
        TextView cartPrice;
        @BindView(R.id.text_num)
        TextView num;
        public ViewHolderClose(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    //定义接口
    private CallBackClose callBackClose;
    public void setCallBackClose(CallBackClose callBackClose){
        this.callBackClose = callBackClose;
    }
    public interface CallBackClose{
        void callBack(ArrayList<FindShoppingCartBean.ResultBean> list);
    }
}
