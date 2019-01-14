package com.example.wdshop.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.activity.BaseActivity;
import com.example.wdshop.api.Apis;
import com.example.wdshop.mine.bean.AddressBean;
import com.example.wdshop.mine.bean.UpdateAddressBean;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.view.Iview;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 *修改地址的Activity
 * */
public class UpdateAddressActivity extends BaseActivity implements Iview {

    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.recipients_edit)
    EditText recipientsEdit;
    @BindView(R.id.telephone_edit)
    EditText telephoneEdit;
    @BindView(R.id.area_edit)
    EditText areaEdit;
    @BindView(R.id.area_image)
    ImageView areaImage;
    @BindView(R.id.detailed_edit)
    EditText detailedEdit;
    @BindView(R.id.postcode_edit)
    EditText postcodeEdit;
    private PresenterImpl presenter;
    private String realName;
    private String phone;
    private String area;
    private String detailed;
    private String postcode;
    private int id;

    private String address;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_updata_address;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        AddressBean.ResultBean resultBean = intent.getParcelableExtra("resultBean");
        String realName = resultBean.getRealName();
        String phone = resultBean.getPhone();
        String address = resultBean.getAddress();
        id = resultBean.getId();
        String[] split = address.split("\\ ");
        String zipCode = resultBean.getZipCode();
        recipientsEdit.setText(realName);
        telephoneEdit.setText(phone);
        areaEdit.setText(split[0]+" "+split[1]+" "+split[2]);
        detailedEdit.setText(split[3]);
        postcodeEdit.setText(zipCode);
        setResult(200,intent);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new PresenterImpl(this);
        ButterKnife.bind(this);
    }

    @Override
    public void requestData(Object o) {
        if (o instanceof UpdateAddressBean) {
            UpdateAddressBean addressBean = (UpdateAddressBean) o;
            Toast.makeText(UpdateAddressActivity.this, addressBean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void requestFail(Object o) {
        if (o instanceof Exception) {
            Exception e = (Exception) o;
            e.printStackTrace();
        }
        Toast.makeText(UpdateAddressActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.save, R.id.area_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.save:
                getData();
                finish();
                break;
            case R.id.area_image:
                //判断输入法的隐藏状态
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    //调用CityPicker选取区域
                    selectAddress();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 加载数据
     */
    public void getData() {
        //获取输入框的值
        realName = recipientsEdit.getText().toString();
        phone = telephoneEdit.getText().toString();
        area = areaEdit.getText().toString();
        detailed = detailedEdit.getText().toString();
        postcode = postcodeEdit.getText().toString();
        Map<String, String> map = new HashMap();
        map.put("id", String.valueOf(id));
        map.put("realName", realName);
        map.put("phone", phone);
        map.put("address", area + " " + detailed);
        map.put("zipCode", postcode);
        presenter.putRequest(Apis.URL_CHANGE_ADDRESS_PUT, map, UpdateAddressBean.class);
    }

    /**
     * 城市三级联动
     */
    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(UpdateAddressActivity.this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("江苏省")
                .city("常州市")
                .district("天宁区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                areaEdit.setText(province.trim() + " " + city.trim() + " " + district.trim());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
