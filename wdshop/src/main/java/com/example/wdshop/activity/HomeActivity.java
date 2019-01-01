package com.example.wdshop.activity;
/**
 * 首页Activity
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.wdshop.R;
import com.example.wdshop.fragment.CartFragment;
import com.example.wdshop.fragment.CircleFragment;
import com.example.wdshop.fragment.HomeFragment;
import com.example.wdshop.fragment.MineFragment;
import com.example.wdshop.fragment.OrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.viewpage)
    ViewPager viewpage;
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
        list.add(new HomeFragment());
        list.add(new CircleFragment());
        list.add(new CartFragment());
        list.add(new OrderFragment());
        list.add(new MineFragment());
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

}
