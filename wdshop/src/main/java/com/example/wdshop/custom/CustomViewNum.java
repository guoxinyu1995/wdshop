package com.example.wdshop.custom;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.adaper.ShopCarAdapter;
import com.example.wdshop.bean.FindShoppingCartBean;
import com.example.wdshop.util.EditTextUtils;

import java.util.ArrayList;
import java.util.List;


public class CustomViewNum extends LinearLayout {

    private ImageView add;
    private ImageView cut;
    private EditText editText;
    private int num;
    private ShopCarAdapter shopCarAdapter;
    private List<FindShoppingCartBean.ResultBean> list = new ArrayList<>();
    private int position;

    public CustomViewNum(Context context) {
        super(context);
        initView(context);
    }

    public CustomViewNum(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    private Context mContext;
    private void initView(Context context) {
        mContext = context;
        View view=View.inflate(context,R.layout.custom_view_num,null);
        add = view.findViewById(R.id.cart_add);
        cut = view.findViewById(R.id.cart_cut);
        editText = view.findViewById(R.id.cart_num);
        addView(view);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    num = Integer.valueOf(s.toString());
                    list.get(position).setCount(num);
                }catch (Exception e){
                    list.get(position).setCount(1);
                }
                if(callBackListener!=null){
                    callBackListener.callback();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //点击加号
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                editText.setText(num + "");
                list.get(position).setCount(num);
                if(callBackListener!=null){
                    callBackListener.callback();
                }
                shopCarAdapter.notifyItemChanged(position);
            }
        });
        //点击减号
        cut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num > 1) {
                    num--;
                } else {
                    Toast.makeText(mContext, "不能在减了", Toast.LENGTH_SHORT).show();
                }
                editText.setText(num + "");
                list.get(position).setCount(num);
                if(callBackListener!=null){
                    callBackListener.callback();
                }
                shopCarAdapter.notifyItemChanged(position);
            }
        });
    }
    public void setData(ShopCarAdapter shopCarAdapter, List<FindShoppingCartBean.ResultBean> list, int i){
        this.list=list;
        this.shopCarAdapter=shopCarAdapter;
        position = i;
        num = list.get(i).getCount();
        editText.setText(num + "");
    }

    private CallBackListener callBackListener;

    public void setOnCallBack(CallBackListener listener){
        callBackListener=listener;
    }
    public interface CallBackListener{
        void callback();
    }
}
