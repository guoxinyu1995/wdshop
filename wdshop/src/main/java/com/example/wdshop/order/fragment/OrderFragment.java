package com.example.wdshop.order.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.wdshop.R;
import com.example.wdshop.custom.NoScrollViewPager;
import com.example.wdshop.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
/**
 *订单的fragment
 * */
public class OrderFragment extends BaseFragment{
    @BindView(R.id.all_button)
    ImageButton allButton;
    @BindView(R.id.all_text)
    TextView allText;
    @BindView(R.id.stocks_button)
    ImageButton stocksButton;
    @BindView(R.id.stockke_text)
    TextView stockkeText;
    @BindView(R.id.obligation_button)
    ImageButton obligationButton;
    @BindView(R.id.obligation_text)
    TextView obligationText;
    @BindView(R.id.wait_button)
    ImageButton waitButton;
    @BindView(R.id.wait_text)
    TextView waitText;
    @BindView(R.id.remait_button)
    ImageButton remaitButton;
    @BindView(R.id.remait_text)
    TextView remaitText;
    @BindView(R.id.order_viewpager)
    NoScrollViewPager orderViewpager;
    Unbinder unbinder;
    private List<Fragment> list;
    private AllFragment allFragment;
    private ObligationFragment obligationFragment;
    private RemaitFragment remaitFragment;
    private StocksFragment stocksFragment;
    private WaitFragment waitFragment;

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

    }

    /**
     * 初始化view
     */
    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        list = new ArrayList<>();
        allFragment = new AllFragment();
        obligationFragment = new ObligationFragment();
        remaitFragment = new RemaitFragment();
        stocksFragment = new StocksFragment();
        waitFragment = new WaitFragment();
        list.add(allFragment);
        list.add(obligationFragment);
        list.add(waitFragment);
        list.add(remaitFragment);
        list.add(stocksFragment);
        orderViewpager.setOffscreenPageLimit(5);
        //创建适配器
        orderViewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
    }

    /**
     * 初始化视图
     */
    @Override
    protected int getLayoutResId() {
        return R.layout.order_fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.all_button, R.id.stocks_button, R.id.obligation_button, R.id.wait_button, R.id.remait_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //全部订单
            case R.id.all_button:
                orderViewpager.setCurrentItem(0);
                break;
                //已完成
            case R.id.stocks_button:
                orderViewpager.setCurrentItem(4);
                break;
                //待付款
            case R.id.obligation_button:
                orderViewpager.setCurrentItem(1);
                break;
                //待收货
            case R.id.wait_button:
                orderViewpager.setCurrentItem(2);
                break;
                //待评价
            case R.id.remait_button:
                orderViewpager.setCurrentItem(3);
                break;
                default:
                    break;
        }
    }
}
