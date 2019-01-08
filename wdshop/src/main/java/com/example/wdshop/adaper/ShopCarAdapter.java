package com.example.wdshop.adaper;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wdshop.R;
import com.example.wdshop.bean.FindShoppingCartBean;
import com.example.wdshop.custom.CustomViewNum;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
public class ShopCarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FindShoppingCartBean.ResultBean> mResult;
    private Context mContext;

    public ShopCarAdapter(Context mContext) {
        this.mContext = mContext;
        mResult = new ArrayList<>();
    }
    public void setmResult(List<FindShoppingCartBean.ResultBean> results){
        mResult.clear();
        if(results!=null){
            mResult.addAll(results);
        }
        notifyDataSetChanged();
    }

    public void setDel(int position){
        mResult.remove(position);
        notifyItemRemoved(position);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cart_shopping_item, viewGroup, false);
        ViewHolderCart holderCart = new ViewHolderCart(view);
        return holderCart;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolderCart holderCart = (ViewHolderCart) viewHolder;
        holderCart.cartImage.setImageURI(Uri.parse(mResult.get(i).getPic()));
        holderCart.cartPrice.setText("￥"+mResult.get(i).getPrice());
        holderCart.cartTitle.setText(mResult.get(i).getCommodityName());
        holderCart.viewNum.setData(this,mResult,i);
        holderCart.cartRadio.setChecked(mResult.get(i).isChecked());
        holderCart.cartRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mResult.get(i).setChecked(true);
                }else{
                    mResult.get(i).setChecked(false);
                }
                if(callBackList!=null){
                    callBackList.callBack(mResult);
                }
            }
        });
        holderCart.buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBackCart!=null){
                    callBackCart.callBack(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResult.size();
    }

    class ViewHolderCart extends RecyclerView.ViewHolder {
        @BindView(R.id.cart_radio)
        CheckBox cartRadio;
        @BindView(R.id.cart_image)
        SimpleDraweeView cartImage;
        @BindView(R.id.cart_title)
        TextView cartTitle;
        @BindView(R.id.cart_price)
        TextView cartPrice;
        @BindView(R.id.cart_custom_view_num)
        CustomViewNum viewNum;
        @BindView(R.id.cart_relative)
        RelativeLayout cartRelative;
        @BindView(R.id.shop_car_del)
        Button buttonDel;
        public ViewHolderCart(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    //定义接口
    private CallBackCart callBackCart;
    public void setCallBackCart(CallBackCart callBackCart){
        this.callBackCart = callBackCart;
    }
    public interface CallBackCart{
        void callBack(int position);
    }


    private CallBackList callBackList;
    public void setCallBackList(CallBackList callBackList){
        this.callBackList = callBackList;
    }
    public interface CallBackList{
        void callBack(List<FindShoppingCartBean.ResultBean> mResult);
    }
}
