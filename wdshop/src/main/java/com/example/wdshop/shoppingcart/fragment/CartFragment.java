package com.example.wdshop.shoppingcart.fragment;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdshop.R;

import com.example.wdshop.shoppingcart.activity.CloseActivity;
import com.example.wdshop.shoppingcart.adaper.ShopCarAdapter;
import com.example.wdshop.api.Apis;
import com.example.wdshop.shoppingcart.bean.FindShoppingCartBean;
import com.example.wdshop.fragment.BaseFragment;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.view.Iview;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
/**
 * 购物车Fragment
 * */
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
    private List<FindShoppingCartBean.ResultBean> result;
    private ArrayList<FindShoppingCartBean.ResultBean> checkList;
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
        carAdapter.setCallBackCart(new ShopCarAdapter.CallBackCart() {
            @Override
            public void callBack(int position) {
                carAdapter.setDel(position);
            }
        });

        carAdapter.setCallBackList(new ShopCarAdapter.CallBackList() {
            @Override
            public void callBack(List<FindShoppingCartBean.ResultBean> mResult) {
                double totalPrice = 0;
                int num = 0;
                //实例化集合存入勾选的商品
                checkList = new ArrayList<>();
                for(int i = 0;i<mResult.size();i++){
                    //获取选中状态
                    if(mResult.get(i).isChecked()){
                        checkList.add(mResult.get(i));
                        totalPrice=totalPrice+(mResult.get(i).getCount()*mResult.get(i).getPrice());
                        num++;
                    }
                }
                if(num<mResult.size()){
                    query.setChecked(false);
                }else{
                    query.setChecked(true);
                }
                total.setText("￥"+totalPrice);
            }
        });
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
            result = cartBean.getResult();
            if(cartBean==null || !cartBean.isSuccess()){
                Toast.makeText(getActivity(),cartBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else{
                carAdapter.setmResult(cartBean.getResult());
            }
        }
    }

    /**
     * 请求失败
     */
    @Override
    public void requestFail(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
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
                if(checkList!=null && checkList.size()!=0){
                    Intent intent = new Intent(getActivity(),CloseActivity.class);
                    intent.putParcelableArrayListExtra("checkList",checkList);
                    //startActivity(intent);
                    startActivityForResult(intent,100);
                }else{
                    Toast.makeText(getActivity(), "请选择要提交的商品", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.query:
                getCheck(query.isChecked());
                carAdapter.notifyDataSetChanged();
                break;
                default:
                    break;
        }
    }

    private void getCheck(boolean checked) {
        double totalPrice = 0;
        for(int i = 0;i<result.size();i++){
            result.get(i).setChecked(checked);
            totalPrice = totalPrice+(result.get(i).getCount()*result.get(i).getPrice());
        }
        if(checked){
            total.setText("￥"+totalPrice);
        }else{
            total.setText("￥0.00");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==200){
            presenter.getRequest(Apis.URL_FIND_CART_GET, FindShoppingCartBean.class);
        }
    }
}
