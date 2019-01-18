package com.example.wdshop.mine.adaper;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wdshop.R;
import com.example.wdshop.custom.CustomMultiImageView;
import com.example.wdshop.mine.bean.MyCircleBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCircleAdaper1 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<MyCircleBean.ResultBean> list;
    public MyCircleAdaper1(Context mContext) {
        this.mContext = mContext;
        list = new ArrayList<>();
    }

    public void setList(List<MyCircleBean.ResultBean> lists) {
        list.clear();
        if (list != null) {
            list.addAll(lists);
        }
        notifyDataSetChanged();
    }

    public void addList(List<MyCircleBean.ResultBean> lists) {
        if (list != null) {
            list.addAll(lists);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.my_circle_item_view_image, viewGroup, false);
           ViewHoldeImage holder = new ViewHoldeImage(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                ViewHoldeImage holdeImage = (ViewHoldeImage) viewHolder;
                holdeImage.circleContent.setText(list.get(i).getContent());
                String[] image = list.get(i).getImage().split("\\,");
                List<String> sList = new ArrayList<>();
                for(int a = 0;a<image.length;a++){
                    sList.add(image[a]);
                }
                holdeImage.circleImage01.setList(sList);
                String date = new SimpleDateFormat("yyyy-MM-dd").format(
                        new Date(list.get(i).getCreateTime()));
                holdeImage.circleTime.setText(date);
                holdeImage.circleNum.setText(list.get(i).getGreatNum()+"");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHoldeImage extends RecyclerView.ViewHolder {
        @BindView(R.id.circle_check)
        CheckBox circleCheck;
        @BindView(R.id.circle_content)
        TextView circleContent;
        @BindView(R.id.circle_image01)
        CustomMultiImageView circleImage01;
        @BindView(R.id.circle_time)
        TextView circleTime;
        @BindView(R.id.circle_num)
        TextView circleNum;
        @BindView(R.id.circle_prise)
        ImageView circlePrise;

        public ViewHoldeImage(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
