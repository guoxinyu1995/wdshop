package com.example.wdshop.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdshop.R;
import com.example.wdshop.api.Apis;
import com.example.wdshop.bean.InformationBean;
import com.example.wdshop.bean.MineUpdateNameBean;
import com.example.wdshop.bean.MineUpdatePasswordBean;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.view.Iview;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PresonalDataActivity extends BaseActivity implements Iview {
    @BindView(R.id.my_profile)
    TextView myProfile;
    @BindView(R.id.my_profile_text)
    TextView myProfileText;
    @BindView(R.id.my_profile_simple)
    SimpleDraweeView myProfileSimple;
    @BindView(R.id.my_nivkname_text)
    TextView myNivknameText;
    @BindView(R.id.my_nickname)
    TextView myNickname;
    @BindView(R.id.my_password_text)
    TextView myPasswordText;
    @BindView(R.id.my_password)
    TextView myPassword;
    private AlertDialog dialog;
    private PresenterImpl presenter;

    /**
     * 加载视图
     */
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_presonal_data;
    }

    /**
     * 加载数据
     */
    @Override
    protected void initData() {
        Intent intent = getIntent();
        InformationBean.ResultBean result = (InformationBean.ResultBean) intent.getSerializableExtra("result");
        String headPic = result.getHeadPic();
        String nickName = result.getNickName();
        String password = result.getPassword();
        myProfileSimple.setImageURI(Uri.parse(headPic));
        myNickname.setText(nickName);
        myPassword.setText(password);
        setResult(200, intent);
    }

    /**
     * 初始化view
     */
    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new PresenterImpl(this);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.my_profile_text, R.id.my_profile_simple, R.id.my_nickname, R.id.my_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_profile_text:
                break;
            case R.id.my_profile_simple:
                break;
            case R.id.my_nickname:
                final AlertDialog.Builder builder = new AlertDialog.Builder(PresonalDataActivity.this);
                View viewname = View.inflate(this, R.layout.presonal_alertdalog_name, null);
                builder.setView(viewname);
                Button update = viewname.findViewById(R.id.updata_btn);
                Button cencal = viewname.findViewById(R.id.cancal_btn);
                final EditText updateedix = viewname.findViewById(R.id.updata_edix);
                //修改
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = updateedix.getText().toString().trim();
                        if (name.equals("")) {
                            Toast.makeText(PresonalDataActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                        } else {
                            Map<String, String> map = new HashMap<>();
                            map.put("nickName", name);
                            presenter.putRequest(Apis.URL_UPDATE_NAME_PUT, map, MineUpdateNameBean.class);
                            myNickname.setText(name);
                        }
                        dialog.dismiss();
                    }
                });
                //取消
                cencal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(PresonalDataActivity.this, "取消", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.my_password:
                final AlertDialog.Builder pbuilder = new AlertDialog.Builder(PresonalDataActivity.this);
                View viewpas = View.inflate(this, R.layout.presonal_alertdalog_password, null);
                pbuilder.setView(viewpas);
                final EditText former_edix = viewpas.findViewById(R.id.former_edix);
                final EditText new_edix = viewpas.findViewById(R.id.new_edix);
                Button updatep = viewpas.findViewById(R.id.updata_btn);
                Button cancelp = viewpas.findViewById(R.id.cancal_btn);
                //修改
                updatep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String formerPass = former_edix.getText().toString().trim();
                        String newPass = new_edix.getText().toString().trim();
                        if (formerPass.equals("") || newPass.equals("")) {
                            Toast.makeText(PresonalDataActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                        } else {
                            Map<String, String> map = new HashMap<>();
                            map.put("oldPwd",formerPass);
                            map.put("newPwd",newPass);
                            presenter.putRequest(Apis.URL_UPDATE_PWD_PUT, map, MineUpdatePasswordBean.class);
                            myPassword.setText(newPass);
                        }
                        dialog.dismiss();
                    }
                });
                //取消
                cancelp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(PresonalDataActivity.this, "取消", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog = pbuilder.create();
                dialog.show();
                break;
            default:
                break;
        }
    }

    /**
     * 请求成功
     */
    @Override
    public void requestData(Object o) {
        //修改昵称
        if (o instanceof MineUpdateNameBean) {
            MineUpdateNameBean updateBean = (MineUpdateNameBean) o;
            Toast.makeText(PresonalDataActivity.this, updateBean.getMessage(), Toast.LENGTH_SHORT).show();
       //修改密码
        }else if(o instanceof MineUpdatePasswordBean){
            MineUpdatePasswordBean passwordBean = (MineUpdatePasswordBean) o;
            Toast.makeText(PresonalDataActivity.this,passwordBean.getMessage(),Toast.LENGTH_SHORT).show();
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
        Toast.makeText(PresonalDataActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
    }
}
