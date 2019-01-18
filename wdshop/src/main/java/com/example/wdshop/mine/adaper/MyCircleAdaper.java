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
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCircleAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<MyCircleBean.ResultBean> list;
    private final int TYPE_ONE = 1;
    private final int TYPE_TWO = 2;
    private final int TYPE_THREE = 3;
    private boolean flag = false;

    public MyCircleAdaper(Context context) {
        this.context = context;
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

    public List<MyCircleBean.ResultBean> getList() {
        return list;
    }

    public void setCheckbox(boolean flag) {
        this.flag = flag;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        String[] split = list.get(position).getImage().split("\\,");
        if (split.length == 1) {
            return TYPE_ONE;
        } else if (split.length == 2) {
            return TYPE_TWO;
        } else if (split.length == 3) {
            return TYPE_THREE;
        } else {
            return TYPE_THREE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder = null;
        if (i == TYPE_ONE) {
            View one = LayoutInflater.from(context).inflate(R.layout.my_circle_item_view_one, viewGroup, false);
            holder = new ViewHolderOne(one);
        } else if (i == TYPE_TWO) {
            View two = LayoutInflater.from(context).inflate(R.layout.my_circle_item_view_two, viewGroup, false);
            holder = new ViewHolderTwo(two);
        } else if (i == TYPE_THREE) {
            View three = LayoutInflater.from(context).inflate(R.layout.my_circle_item_view_three, viewGroup, false);
            holder = new ViewHolderThree(three);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int type = getItemViewType(i);
        MyCircleBean.ResultBean resultBean = list.get(i);
        switch (type) {
            case TYPE_ONE:
                ViewHolderOne holderOne = (ViewHolderOne) viewHolder;
                holderOne.circle_check.setChecked(resultBean.isCheck());
                holderOne.circle_content.setText(resultBean.getContent());
                holderOne.circle_num.setText(resultBean.getGreatNum() + "");
                Uri uri1 = Uri.parse(resultBean.getImage());
                holderOne.circle_image01.setImageURI(uri1);
                String date1 = new SimpleDateFormat("yyyy-MM-dd").format(
                        new Date(resultBean.getCreateTime()));
                holderOne.circle_time.setText(date1);
                if (flag) {
                    holderOne.circle_check.setVisibility(View.VISIBLE);
                    //判断复选框是否选择
                    if (holderOne.circle_check.isChecked()) {
                        resultBean.setCheck(true);
                    } else {
                        resultBean.setCheck(false);
                    }
                } else {
                    holderOne.circle_check.setVisibility(View.GONE);
                }
                if (resultBean.getGreatNum() > 0) {
                    holderOne.circle_prise.setImageResource(R.mipmap.common_btn_prise_s);
                } else {
                    holderOne.circle_prise.setImageResource(R.mipmap.common_btn_prise_n);
                }
                break;
            case TYPE_TWO:
                ViewHolderTwo holderTwo = (ViewHolderTwo) viewHolder;
                holderTwo.circleCheck.setChecked(resultBean.isCheck());
                holderTwo.circleContent.setText(resultBean.getContent());
                holderTwo.circleNum.setText(resultBean.getGreatNum() + "");
                String[] split2 = resultBean.getImage().split("\\,");
                holderTwo.circleImage01.setImageURI(Uri.parse(split2[0]));
                holderTwo.circleImage02.setImageURI(Uri.parse(split2[1]));
                String date2 = new SimpleDateFormat("yyyy-MM-dd").format(
                        new Date(resultBean.getCreateTime()));
                holderTwo.circleTime.setText(date2);
                if (flag) {
                    holderTwo.circleCheck.setVisibility(View.VISIBLE);
                    //判断复学框是否选择
                    if (holderTwo.circleCheck.isChecked()) {
                        resultBean.setCheck(true);
                    } else {
                        resultBean.setCheck(false);
                    }
                } else {
                    holderTwo.circleCheck.setVisibility(View.GONE);
                }
                if (resultBean.getGreatNum() > 0) {
                    holderTwo.circlePrise.setImageResource(R.mipmap.common_btn_prise_s);
                } else {
                    holderTwo.circlePrise.setImageResource(R.mipmap.common_btn_prise_n);
                }
                break;
            case TYPE_THREE:
                ViewHolderThree holderThree = (ViewHolderThree) viewHolder;
                holderThree.circleCheck.setChecked(resultBean.isCheck());
                holderThree.circleContent.setText(resultBean.getContent());
                holderThree.circleNum.setText(resultBean.getGreatNum() + "");
                String[] split3 = resultBean.getImage().split("\\,");
                holderThree.circleImage01.setImageURI(Uri.parse(split3[0]));
                holderThree.circleImage02.setImageURI(Uri.parse(split3[1]));
                holderThree.circleImage03.setImageURI(Uri.parse(split3[2]));
                String date3 = new SimpleDateFormat("yyyy-MM-dd").format(
                        new Date(resultBean.getCreateTime()));
                holderThree.circleTime.setText(date3);
                if (flag) {
                    holderThree.circleCheck.setVisibility(View.VISIBLE);
                    //判断复学框是否选择
                    if (holderThree.circleCheck.isChecked()) {
                        resultBean.setCheck(true);
                    } else {
                        resultBean.setCheck(false);
                    }
                } else {
                    holderThree.circleCheck.setVisibility(View.GONE);
                }
                if (resultBean.getGreatNum() > 0) {
                    holderThree.circlePrise.setImageResource(R.mipmap.common_btn_prise_s);
                } else {
                    holderThree.circlePrise.setImageResource(R.mipmap.common_btn_prise_n);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {

        @BindView(R.id.circle_prise)
        ImageView circle_prise;
        @BindView(R.id.circle_num)
        TextView circle_num;
        @BindView(R.id.circle_content)
        TextView circle_content;
        @BindView(R.id.circle_time)
        TextView circle_time;
        @BindView(R.id.circle_check)
        CheckBox circle_check;
        @BindView(R.id.circle_image01)
        SimpleDraweeView circle_image01;

        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ViewHolderTwo extends RecyclerView.ViewHolder {
        @BindView(R.id.circle_check)
        CheckBox circleCheck;
        @BindView(R.id.circle_content)
        TextView circleContent;
        @BindView(R.id.circle_image01)
        SimpleDraweeView circleImage01;
        @BindView(R.id.circle_image02)
        SimpleDraweeView circleImage02;
        @BindView(R.id.circle_time)
        TextView circleTime;
        @BindView(R.id.circle_num)
        TextView circleNum;
        @BindView(R.id.circle_prise)
        ImageView circlePrise;

        public ViewHolderTwo(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ViewHolderThree extends RecyclerView.ViewHolder {

        @BindView(R.id.circle_check)
        CheckBox circleCheck;
        @BindView(R.id.circle_content)
        TextView circleContent;
        @BindView(R.id.circle_image01)
        SimpleDraweeView circleImage01;
        @BindView(R.id.circle_image02)
        SimpleDraweeView circleImage02;
        @BindView(R.id.circle_image03)
        SimpleDraweeView circleImage03;
        @BindView(R.id.circle_time)
        TextView circleTime;
        @BindView(R.id.circle_num)
        TextView circleNum;
        @BindView(R.id.circle_prise)
        ImageView circlePrise;

        public ViewHolderThree(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
