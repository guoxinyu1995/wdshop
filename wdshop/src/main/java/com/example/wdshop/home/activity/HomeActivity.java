package com.example.wdshop.home.activity;
/**
 * 首页Activity
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.activity.BaseActivity;
import com.example.wdshop.custom.NoScrollViewPager;
import com.example.wdshop.shoppingcart.fragment.CartFragment;
import com.example.wdshop.mycircle.fragment.CircleFragment;
import com.example.wdshop.home.fragment.HomeFragment;
import com.example.wdshop.mine.fragment.MineFragment;
import com.example.wdshop.order.fragment.OrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.viewpage)
    NoScrollViewPager viewpage;
    @BindView(R.id.radio_home)
    RadioButton radioHome;
    @BindView(R.id.radio_circle)
    RadioButton radioCircle;
    @BindView(R.id.radio_cart)
    RadioButton radioCart;
    @BindView(R.id.radio_order)
    RadioButton radioOrder;
    @BindView(R.id.radio_mine)
    RadioButton radioMine;
    @BindView(R.id.group)
    RadioGroup group;
    private List<Fragment> list;
    private HomeFragment homeFragment;
    private CircleFragment circleFragment;
    private CartFragment cartFragment;
    private OrderFragment orderFragment;
    private MineFragment mineFragment;

    /**
     * 初始化数据
     * */
    @Override
    protected void initData() {

    }

    /**
     * 初始化view
     * */
    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        list = new ArrayList<>();
        homeFragment = new HomeFragment();
        circleFragment = new CircleFragment();
        cartFragment = new CartFragment();
        orderFragment = new OrderFragment();
        mineFragment = new MineFragment();
        list.add(homeFragment);
        list.add(circleFragment);
        list.add(cartFragment);
        list.add(orderFragment);
        list.add(mineFragment);
        //创建适配器
        viewpage.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        //viewpage.setOffscreenPageLimit(5);
        //点击切换
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_home:
                        viewpage.setCurrentItem(0);
                        break;
                    case R.id.radio_circle:
                        viewpage.setCurrentItem(1);
                        break;
                    case R.id.radio_cart:
                        viewpage.setCurrentItem(2);
                        break;
                    case R.id.radio_order:
                        viewpage.setCurrentItem(3);
                        break;
                    case R.id.radio_mine:
                        viewpage.setCurrentItem(4);
                        break;
                        default:
                            break;
                }
            }
        });
        //滑动切换
        viewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        group.check(R.id.radio_home);
                        break;
                    case 1:
                        group.check(R.id.radio_circle);
                        break;
                    case 2:
                        group.check(R.id.radio_cart);
                        break;
                    case 3:
                        group.check(R.id.radio_order);
                        break;
                    case 4:
                        group.check(R.id.radio_mine);
                        break;
                        default:
                            break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    /**
     * 出始化视图
     * */
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home;
    }
    //监听返回键
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if(viewpage.getCurrentItem()==0){
                homeFragment.getBackData(true);
            }
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

}
