package com.example.wdshop.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdshop.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PresonalDataActivity extends BaseActivity {
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

    }

    /**
     * 初始化view
     */
    @Override
    protected void initView(Bundle savedInstanceState) {
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
                View view1 = View.inflate(this, R.layout.presonal_alertdalog, null);
                builder.setView(view1);
                Button update = view1.findViewById(R.id.updata_btn);
                Button cencal = view1.findViewById(R.id.cancal_btn);
                EditText updateedix = view1.findViewById(R.id.updata_edix);
                //修改
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(PresonalDataActivity.this,"确定",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                //取消
                cencal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(PresonalDataActivity.this,"取消",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();

                dialog.show();
                break;
            case R.id.my_password:
                break;
            default:
                break;
        }
    }

}
