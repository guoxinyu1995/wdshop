package com.example.wdshop.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
/**
 * 自定义scrollview用于展示详情
 * */
public class CustomScrollView extends ScrollView {
    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollViewListener !=null){
            mScrollViewListener.onScrollChange(this,l,t,oldl,oldt);
        }
    }


    public interface ScrollViewListener{
        void onScrollChange(CustomScrollView scrollView, int l, int t, int oldl, int oldt);
    }

    private ScrollViewListener  mScrollViewListener;

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        mScrollViewListener = scrollViewListener;
    }
}
