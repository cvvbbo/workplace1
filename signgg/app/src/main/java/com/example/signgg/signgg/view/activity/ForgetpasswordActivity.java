package com.example.signgg.signgg.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.signgg.signgg.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiong on 2017/8/22.
 */

public class ForgetpasswordActivity extends AppCompatActivity {

    @BindView(R.id.forgetpasswordnext)
    Button forgetpasswordnext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword);
        ButterKnife.bind(this);

        forgetpasswordnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ForgetpasswordActivity.this,WritesignwordActivity.class);
                startActivity(intent);
            }
        });

    }


}
