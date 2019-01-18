package com.example.wdshop.mine.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.mine.activity.AddressActivity;
import com.example.wdshop.mine.activity.FootPrintActivity;
import com.example.wdshop.mine.activity.MyCircleActivity;
import com.example.wdshop.mine.activity.MyCircleActivity1;
import com.example.wdshop.mine.activity.PresonalDataActivity;
import com.example.wdshop.mine.activity.WalletActivity;
import com.example.wdshop.api.Apis;
import com.example.wdshop.mine.bean.InformationBean;
import com.example.wdshop.fragment.BaseFragment;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.view.Iview;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
/**
 * 我的页面的Fragment
 * */
public class MineFragment extends BaseFragment implements Iview {
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
    private PresenterImpl presenter;
    private String headPic;
    private String nickName;
    private String password;
    private InformationBean.ResultBean result;

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        presenter.getRequest(Apis.URL_VIEW_USER_INFORMATION,InformationBean.class);
    }

    /**
     * 初始化view
     */
    @Override
    protected void initView(View view) {
        presenter = new PresenterImpl(this);
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
                intent.putExtra("result",result);
                startActivityForResult(intent,100);
                break;
            case R.id.circle_text:
                Intent intent_circle = new Intent(getActivity(),MyCircleActivity1.class);
                startActivity(intent_circle);
                break;
            case R.id.footprint_text:
                Intent intent_foot = new Intent(getActivity(),FootPrintActivity.class);
                startActivity(intent_foot);
                break;
            case R.id.wallet_text:
                Intent intent_wallet = new Intent(getActivity(),WalletActivity.class);
                startActivity(intent_wallet);
                break;
            case R.id.address_text:
                Intent intent_address = new Intent(getActivity(),AddressActivity.class);
                startActivity(intent_address);
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
        presenter.onDetach();
    }
    /**
     * 加载成功
     * */
    @Override
    public void requestData(Object o) {
        if(o instanceof InformationBean){
            InformationBean informationBean = (InformationBean) o;
            headPic = informationBean.getResult().getHeadPic();
            nickName = informationBean.getResult().getNickName();
            password = informationBean.getResult().getPassword();
            result = informationBean.getResult();
            if(informationBean==null || !informationBean.isSuccess()){
                Toast.makeText(getActivity(),informationBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else{
                mineSimple.setImageURI(Uri.parse(headPic));
                mineTextName.setText(nickName);
            }
        }
    }
    /**
     * 加载失败
     * */
    /**
     * 请求失败
     */
    @Override
    public void requestFail(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == 200){
            presenter.getRequest(Apis.URL_VIEW_USER_INFORMATION,InformationBean.class);
        }
    }
}
