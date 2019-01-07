package com.example.wdshop.register.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.activity.BaseActivity;
import com.example.wdshop.api.Apis;

import com.example.wdshop.register.bean.RegisterBean;
import com.example.wdshop.login.activity.LoginActivity;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.util.EmptyUtil;
import com.example.wdshop.view.Iview;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 注册Activity
 */
public class RegisterActivity extends BaseActivity implements Iview {
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_verification)
    EditText editVerification;
    @BindView(R.id.text_verification)
    TextView textVerification;
    @BindView(R.id.edit_pass)
    EditText editPass;
    @BindView(R.id.image_eye)
    ImageButton imageEye;
    @BindView(R.id.text_have)
    TextView textHave;
    @BindView(R.id.register_button)
    Button registerButton;
    private String name;
    private String password;
    private PresenterImpl presenter;
    private final int RESULT_NUM = 200;
    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

    }
    /**
     * 初始化view
     * */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new PresenterImpl(this);
        ButterKnife.bind(this);
        //密码显示和隐藏
        imageEye.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    editPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    editPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
                return false;
            }
        });
    }
    /**
     * 初始化视图
     * */
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @OnClick({R.id.text_verification, R.id.text_have, R.id.register_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_verification:
                Toast.makeText(RegisterActivity.this,"8888",Toast.LENGTH_SHORT).show();
                break;
            case R.id.text_have:
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.register_button:
                name = editPhone.getText().toString().trim();
                password = editPass.getText().toString().trim();
                if (EmptyUtil.isNull(name, password)) {
                    getData();
                } else {
                    Toast.makeText(RegisterActivity.this, "手机号和密码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 网络请求数据
     */
    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("phone", editPhone.getText().toString().trim());
        map.put("pwd", editPass.getText().toString().trim());
        presenter.postRequest(Apis.POST_URL_USER_REGISTER, map, RegisterBean.class);
    }

    @Override
    public void requestData(Object o) {
        if(o instanceof RegisterBean){
            RegisterBean bean = (RegisterBean) o;
            if(bean==null || !bean.isSuccess()){
                Toast.makeText(RegisterActivity.this,bean.getMessage(),Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("password",password);
                setResult(RESULT_NUM,intent);
                finish();
            }
        }
    }

    @Override
    public void requestFail(Object o) {
        if (o instanceof Exception) {
            Exception e = (Exception) o;
            e.printStackTrace();
        }
        Toast.makeText(RegisterActivity.this, "请求错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
