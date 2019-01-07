package com.example.wdshop.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdshop.R;

import com.example.wdshop.adaper.ShopCarAdapter;
import com.example.wdshop.api.Apis;
import com.example.wdshop.bean.FindShoppingCartBean;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.view.Iview;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CartFragment extends BaseFragment implements Iview {

    @BindView(R.id.btn_close)
    Button btnClose;
    @BindView(R.id.query)
    CheckBox query;
    @BindView(R.id.query_text)
    TextView queryText;
    @BindView(R.id.heji)
    TextView heji;
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.cart_recyycle)
    RecyclerView cartRecyycle;
    Unbinder unbinder;
    private PresenterImpl presenter;
    private ShopCarAdapter carAdapter;

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        presenter.getRequest(Apis.URL_FIND_CART_GET, FindShoppingCartBean.class);
    }

    /**
     * 初始化view
     */
    @Override
    protected void initView(View view) {
        presenter = new PresenterImpl(this);
        unbinder = ButterKnife.bind(this, view);
        //创建布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        cartRecyycle.setLayoutManager(layoutManager);
        // 创建适配器
        carAdapter = new ShopCarAdapter(getActivity());
        cartRecyycle.setAdapter(carAdapter);
    }

    /**
     * 初始化视图
     */
    @Override
    protected int getLayoutResId() {
        return R.layout.cart_fragment;
    }

    @Override
    public void requestData(Object o) {
        if(o instanceof FindShoppingCartBean){
            FindShoppingCartBean cartBean = (FindShoppingCartBean) o;
            if(cartBean==null || !cartBean.isSuccess()){
                Toast.makeText(getActivity(),cartBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else{
                carAdapter.setmResult(cartBean.getResult());
            }
        }
    }

    @Override
    public void requestFail(Object o) {
        if (o instanceof Exception) {
            Exception e = (Exception) o;
            e.printStackTrace();
        }
        Toast.makeText(getActivity(), "请求错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        presenter.onDetach();
    }

    @OnClick({R.id.btn_close, R.id.query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_close:
                break;
            case R.id.query:
                break;
                default:
                    break;
        }
    }
}
