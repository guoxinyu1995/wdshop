package com.example.wdshop.shoppingcart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.activity.BaseActivity;
import com.example.wdshop.api.Apis;
import com.example.wdshop.mine.activity.NewAddressActivity;
import com.example.wdshop.mine.bean.AddressBean;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.shoppingcart.adaper.CloseAddressAdaper;
import com.example.wdshop.shoppingcart.adaper.CloseShoppAdaaper;
import com.example.wdshop.shoppingcart.bean.CloseBean;
import com.example.wdshop.shoppingcart.bean.CreateOrderBean;
import com.example.wdshop.shoppingcart.bean.FindShoppingCartBean;
import com.example.wdshop.view.Iview;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 创建订单Activity
 * */
public class CloseActivity extends BaseActivity implements Iview {
    @BindView(R.id.add_address)
    TextView addAddress;
    @BindView(R.id.add_layout)
    LinearLayout addLayout;
    @BindView(R.id.realName)
    TextView realName;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.but_obtain)
    ImageView butObtain;
    @BindView(R.id.add_layout_view)
    ConstraintLayout addLayoutView;
    @BindView(R.id.layout_add)
    RelativeLayout layoutAdd;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.address_recyclerview)
    RecyclerView addressRecyclerview;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.settlement)
    TextView settlement;
    @BindView(R.id.relativelayout)
    RelativeLayout relativelayout;
    private int num = 0;
    private double totalPrice1 = 0;
    private PresenterImpl presenter;
    private CloseShoppAdaaper shoppAdaaper;
    private List<CloseBean> closeBeans;
    private CloseAddressAdaper addressAdaper;
    private int id;

    /**
     * 初始化布局
     */
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_close;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        closeBeans = new ArrayList<>();
        //接收值
        Intent intent = getIntent();
        ArrayList<FindShoppingCartBean.ResultBean> checkList = intent.getParcelableArrayListExtra("checkList");
        if (checkList != null) {
            for (FindShoppingCartBean.ResultBean re : checkList) {
                num += re.getCount();
                totalPrice1 += re.getCount() * re.getPrice();
                closeBeans.add(new CloseBean(re.getCommodityId(), re.getCount()));
            }
        }
        totalPrice.setText("共" + num + "件商品，需付款" + totalPrice1 + "元");
        //将集合传到适配器
        shoppAdaaper.setList(checkList);
        //加载地址的view
        getAddressView();
        //查询地址的接口
        presenter.getRequest(Apis.URL_RECEIVE_ADDRESS_LIST_GET, AddressBean.class);
    }

    /**
     * 初始化地址的view
     */
    private void getAddressView() {
        //创建布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        addressRecyclerview.setLayoutManager(layoutManager);
        //创建适配器
        addressAdaper = new CloseAddressAdaper(this);
        addressRecyclerview.setAdapter(addressAdaper);
        //选择
        addressAdaper.setCallBackCloseAddress(new CloseAddressAdaper.CallBackCloseAddress() {
            @Override
            public void callBack(AddressBean.ResultBean resultBean) {
                realName.setText(resultBean.getRealName());
                phone.setText(resultBean.getPhone());
                address.setText(resultBean.getAddress());
                //隐藏
                addressRecyclerview.setVisibility(View.GONE);
                butObtain.setBackgroundResource(R.drawable.ic_down_name);
            }
        });
    }

    /**
     * 出始化view
     */
    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new PresenterImpl(this);
        ButterKnife.bind(this);
        //创建布局管理器
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        //创建适配器
        shoppAdaaper = new CloseShoppAdaaper(this);
        recyclerview.setAdapter(shoppAdaaper);
        //加减
        shoppAdaaper.setCallBackClose(new CloseShoppAdaaper.CallBackClose() {
            @Override
            public void callBack(ArrayList<FindShoppingCartBean.ResultBean> list) {
                int countNum = 0;
                double totalPrice2 = 0;
                for (int i = 0; i < list.size(); i++) {
                    totalPrice2 += (list.get(i).getPrice() * list.get(i).getCount());
                    countNum += list.get(i).getCount();
                }
                num = countNum;
                totalPrice1 = totalPrice2;
                totalPrice.setText("共" + countNum + "件商品，需付款" + totalPrice2 + "元");
            }
        });
    }

    /**
     * 请求成功
     */
    @Override
    public void requestData(Object o) {
        //查询地址
        if (o instanceof AddressBean) {
            AddressBean addressBean = (AddressBean) o;
            if (addressBean == null || !addressBean.isSuccess()) {
                Toast.makeText(CloseActivity.this, addressBean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                List<AddressBean.ResultBean> result = addressBean.getResult();
                for (AddressBean.ResultBean re : result) {
                    if (re.getWhetherDefault() == 1) {
                        addLayout.setVisibility(View.GONE);
                        addLayoutView.setVisibility(View.VISIBLE);
                        realName.setText(re.getRealName());
                        phone.setText(re.getPhone());
                        address.setText(re.getAddress());
                        id = re.getId();
                    } else {
                        addLayout.setVisibility(View.VISIBLE);
                        addLayoutView.setVisibility(View.GONE);
                    }
                }
                addressAdaper.setmResult(result);
            }
            //创建订单
        }else if(o instanceof CreateOrderBean){
            CreateOrderBean orderBean = (CreateOrderBean) o;
            Toast.makeText(CloseActivity.this,orderBean.getMessage(),Toast.LENGTH_SHORT).show();
        }else if(o instanceof String){
            String str = (String) o;
            Toast.makeText(CloseActivity.this,str,Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 请求失败
     */
    @Override
    public void requestFail(Object o) {
        if (o instanceof Exception) {
            Exception e = (Exception) o;
            e.printStackTrace();
        }
        Toast.makeText(CloseActivity.this, "请求错误", Toast.LENGTH_SHORT).show();
    }

    /**
     * 点击事件
     */
    private boolean flag = true;

    @OnClick({R.id.add_address, R.id.but_obtain, R.id.settlement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_address:
                Intent intent = new Intent(CloseActivity.this, NewAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.but_obtain:
                if (flag) {
                    addressRecyclerview.setVisibility(View.GONE);
                    butObtain.setBackgroundResource(R.drawable.ic_down_name);
                } else {
                    addressRecyclerview.setVisibility(View.VISIBLE);
                    butObtain.setBackgroundResource(R.drawable.ic_up_name);
                }
                flag = !flag;
                break;
            case R.id.settlement:
                Gson gson = new Gson();
                String json = gson.toJson(closeBeans);
                Map<String,String> map = new HashMap<>();
                map.put("orderInfo",json);
                map.put("totalPrice",String.valueOf(totalPrice1));
                map.put("addressId",String.valueOf(id));
                presenter.postRequest(Apis.URL_CREATE_ORDER,map,CreateOrderBean.class);
                finish();
                break;
            default:
                break;
        }
    }
}
