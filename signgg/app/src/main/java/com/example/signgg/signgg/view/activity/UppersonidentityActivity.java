package com.example.signgg.signgg.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.signgg.signgg.R;
import com.example.signgg.signgg.utils.sharepreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiong on 2017/8/15.
 */

public class UppersonidentityActivity extends AppCompatActivity {


    @BindView(R.id.tb)
    Toolbar tb;
    @BindView(R.id.idcard_name)
    EditText idcardName;
    @BindView(R.id.idcard_number)
    EditText idcardNumber;
    @BindView(R.id.frontidentity)
    ImageView frontidentity;
    @BindView(R.id.backidentity)
    ImageView backidentity;
    @BindView(R.id.upbt)
    Button upbt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.up_person_identity);
        ButterKnife.bind(this);

        inintToolbar();

        upbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UppersonidentityActivity.this,Loadcheckidentity.class);
                startActivity(intent);
                //这个就不用结束界面了

                //提交之后有个待审核界面，审核之后才是正常的界面。审核失败又是重新提交的界面
                //审核的json就有三种，未审核（checkidentityActivity），待审核，已通过（beginActivity）
                sharepreferenceUtils.saveBooleandata(UppersonidentityActivity.this,"isidentity",false);
                finish();
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

                //返回上一个身份验证界面
                finish();
            }
        });
    }
}
