package com.example.wdshop.mine.adaper;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wdshop.R;
import com.example.wdshop.mine.bean.MyCircleBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCircleImageItemAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MyCircleBean.ResultBean> mResult;
    private Context mContext;

    public MyCircleImageItemAdaper(Context mContext) {
        this.mContext = mContext;
        mResult = new ArrayList<>();
    }

    public void setmResult(List<MyCircleBean.ResultBean> results) {
        mResult.clear();
        if (results != null) {
            mResult.addAll(results);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.my_circle_item_view_moreitem, viewGroup, false);
        ViewHolderMoreImage holderMoreImage = new ViewHolderMoreImage(view);
        return holderMoreImage;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolderMoreImage holderMoreImage = (ViewHolderMoreImage) viewHolder;
        holderMoreImage.circleContent.setText(mResult.get(i).getContent());
        String date = new SimpleDateFormat("yyyy-MM-dd").format(
                new java.util.Date(mResult.get(i).getCreateTime()));
        holderMoreImage.circleTime.setText(date);
        holderMoreImage.circleNum.setText(mResult.get(i).getGreatNum()+"");
        String image = mResult.get(i).getImage().split("\\,")[0];
        holderMoreImage.circleImage01.setImageURI(Uri.parse(image));

    }

    @Override
    public int getItemCount() {
        return mResult.size();
    }

    class ViewHolderMoreImage extends RecyclerView.ViewHolder {
        @BindView(R.id.circle_check)
        CheckBox circleCheck;
        @BindView(R.id.circle_content)
        TextView circleContent;
        @BindView(R.id.circle_image01)
        SimpleDraweeView circleImage01;
        @BindView(R.id.circle_time)
        TextView circleTime;
        @BindView(R.id.circle_num)
        TextView circleNum;
        @BindView(R.id.circle_prise)
        ImageView circlePrise;
        public ViewHolderMoreImage(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    //定义接口
    private CallBackMore callBackMore;
    public void setCallBackMore(CallBackMore callBackMore){
        this.callBackMore = callBackMore;
    }
    public interface CallBackMore{
        void callBack(MyCircleBean.ResultBean resultBean);
    }
}
