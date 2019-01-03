package com.example.wdshop.activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.api.Apis;
import com.example.wdshop.bean.LoginBean;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.util.EmptyUtil;
import com.example.wdshop.view.Iview;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 登录Activity
 */
public class LoginActivity extends BaseActivity implements Iview {
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_pass)
    EditText editPass;
    @BindView(R.id.image_eye)
    ImageButton imageEye;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.text_remember)
    TextView textRemember;
    @BindView(R.id.text_register)
    TextView textRegister;
    @BindView(R.id.login_button)
    Button loginButton;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private PresenterImpl presenter;
    private String name;
    private String password;
    private final int RERUEST_NUM = 100;
    /**
     * 网络请求数据
     */
    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("phone", editPhone.getText().toString().trim());
        map.put("pwd", editPass.getText().toString().trim());
        presenter.startRequest(Apis.POST_URL_USER_LOGIN, map, LoginBean.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

    }

    /**
     * 初始化view
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new PresenterImpl(this);
        ButterKnife.bind(this);
        //初始化SharedPreferences
        initSharedPreferences();
        //将记住密码的状态值取出
        boolean r_check = sp.getBoolean("r_check", false);
        if (r_check) {
            //取值
            String name = sp.getString("name", null);
            String password = sp.getString("password", null);
            //设置值
            editPhone.setText(name);
            editPass.setText(password);
            checkbox.setChecked(true);
        }
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


    /**
     * 初始化SharedPreferences
     */
    private void initSharedPreferences() {
        sp = getSharedPreferences("User", MODE_PRIVATE);
        edit = sp.edit();
    }

    /**
     * 点击事件
     */
    @OnClick({R.id.text_register, R.id.login_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, RERUEST_NUM);
                break;
            case R.id.login_button:
                name = editPhone.getText().toString().trim();
                password = editPass.getText().toString().trim();
                //非空判断
                if (EmptyUtil.isNull(name, password)) {
                    getData();
                } else {
                    Toast.makeText(LoginActivity.this, "手机号和密码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void requestData(Object o) {
        if (o instanceof LoginBean) {
            LoginBean bean = (LoginBean) o;
            int userId = bean.getResult().getUserId();
            String sessionId = bean.getResult().getSessionId();
            if (bean == null || !bean.isSuccess()) {
                Toast.makeText(LoginActivity.this, bean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                //判断记住密码是否勾选
                if (checkbox.isChecked()) {
                    //存值
                    edit.putString("name", name);
                    edit.putString("password", password);
                    //存入一个勾选转态
                    edit.putBoolean("r_check", true);
                    //提交
                    edit.commit();
                } else {
                    //清空
                    edit.clear();
                    //提交
                    edit.commit();
                }
                sp.edit().putString("userId",String.valueOf(userId)).putString("sessionId",sessionId).commit();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                LoginBean.ResultBean result = bean.getResult();
                intent.putExtra("result",result);
                startActivity(intent);
                finish();
            }
        }
    }
   /* int userId = loginBean.getResult().getUserId();
    String sessionId = loginBean.getResult().getSessionId();
    sharedPreferences = getSharedPreferences("Header", MODE_PRIVATE);
                sharedPreferences.edit().putString("userId",userId+"").putString("sessionId",sessionId).commit();
*/
    @Override
    public void requestFail(Object o) {
        if (o instanceof Exception) {
            Exception e = (Exception) o;
            e.printStackTrace();
        }
        Toast.makeText(LoginActivity.this, "请求错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RERUEST_NUM && resultCode == 200) {
            //接收注册成功的值
            String name = data.getStringExtra("name");
            String password = data.getStringExtra("password");
            editPhone.setText(name);
            editPass.setText(password);
        }

    }
}
