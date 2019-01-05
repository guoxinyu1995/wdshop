package com.example.wdshop.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.wdshop.R;
import com.example.wdshop.activity.PresonalDataActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MineFragment extends BaseFragment {
    @BindView(R.id.mine_text_name)
    TextView mineTextName;
    @BindView(R.id.presonal_text)
    TextView presonalText;
    @BindView(R.id.circle_text)
    TextView circleText;
    @BindView(R.id.footprint_text)
    TextView footprintText;
    @BindView(R.id.wallet_text)
    TextView walletText;
    @BindView(R.id.address_text)
    TextView addressText;
    @BindView(R.id.mine_simple)
    SimpleDraweeView mineSimple;
    Unbinder unbinder;

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
    }

    /**
     * 初始化视图
     */
    @Override
    protected int getLayoutResId() {
        return R.layout.mine_fragment;
    }

    @OnClick({R.id.mine_text_name, R.id.presonal_text, R.id.circle_text, R.id.footprint_text, R.id.wallet_text, R.id.address_text, R.id.mine_simple})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_text_name:
                break;
            case R.id.presonal_text:
                Intent intent = new Intent(getActivity(),PresonalDataActivity.class);
                startActivity(intent);
                break;
            case R.id.circle_text:
                break;
            case R.id.footprint_text:
                break;
            case R.id.wallet_text:
                break;
            case R.id.address_text:
                break;
            case R.id.mine_simple:
                break;
                default:
                    break;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
