package com.example.signgg.signgg.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.signgg.signgg.R;
import com.example.signgg.signgg.utils.sharepreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiong on 2017/8/15.
 */

public class PasswloginActivity extends AppCompatActivity {

    @BindView(R.id.tb)
    Toolbar tb;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.signload)
    TextView signload;
    @BindView(R.id.forgetpassword)
    TextView forgetpassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_password_view);
        ButterKnife.bind(this);
        inintToolbar();
        sharepreferenceUtils.saveBooleandata(PasswloginActivity.this, "isfrist", false);


        //验证身份
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswloginActivity.this, CheckidentityActivity.class);
                startActivity(intent);
                sharepreferenceUtils.saveBooleandata(PasswloginActivity.this, "islogin", false);
                finish();
            }
        });

        //切换登录方式
        signload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswloginActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PasswloginActivity.this,ForgetpasswordActivity.class);
                startActivity(intent);
            }
        });


    }

    private void inintToolbar() {
        //这个是登录的
        setSupportActionBar(tb);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setTitle("秒签小哥");

        //这个通用，自定义的还是非自定义的都是通用的。
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
