package com.example.signgg.signgg.view.activity;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.example.signgg.signgg.Fragment.BaseFragment;
import com.example.signgg.signgg.Fragment.Homefragment;
import com.example.signgg.signgg.Fragment.Mefragment;
import com.example.signgg.signgg.Fragment.Taskfragment;
import com.example.signgg.signgg.R;
import com.example.signgg.signgg.utils.LogUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.R.attr.fragment;

/**
 * Created by xiong on 2017/8/16.
 *
 * 内存泄漏什么的都是写完之后的善后工作，别急想着方面的事情
 *
 *
 */

public class BeginActivity extends AppCompatActivity {


    @BindView(R.id.tb)
    Toolbar tb;
    @BindView(R.id.framelayout)
    FrameLayout framelayout;
    @BindView(R.id.rdbt1)
    RadioButton rdbt1;
    @BindView(R.id.rdbt2)
    RadioButton rdbt2;
    @BindView(R.id.rdbt3)
    RadioButton rdbt3;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;


    //之前这里有放
    ArrayList<Fragment> fragments;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin_activity);

        //等项目完成之后再来研究黄油刀的销毁
       ButterKnife.bind(this);
        initToolbar();

        initFragment();
        switchFragment(0);
       radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
               switch (checkedId){
                   case R.id.rdbt1:
                       LogUtil.e("按钮1","执行了么");
                       switchFragment(0);
                       break;
                   case R.id.rdbt2:
                       switchFragment(1);
                       break;
                   case R.id.rdbt3:
                       switchFragment(2);
                       break;
               }
           }
       });
    }

    public  void initFragment(){
        LogUtil.e("BeginActivity","执行了么");
        fragments=new ArrayList<>();
        fragments.add(new Homefragment());
        fragments.add(new Taskfragment());
        fragments.add(new Mefragment());
        }



    public void switchFragment(int j) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            Fragment f = fragments.get(i);
            if (i == j) {
                if (f.isAdded()) {
                    //已经添加过，直接展示
                    beginTransaction.show(f);
                } else {
                    //并没有添加，则把fragment添加进来
                    beginTransaction.add(R.id.framelayout, f);
                    //beginTransaction.addToBackStack(null);
                }
            } else {
                if (f.isAdded()) {
                    //当遍历的和当前的不相等的时候，把这些全部隐藏起来。
                    beginTransaction.hide(f);
                }
            }
        }
        //最后一定要记得提交
        beginTransaction.commit();
    }


    private void initToolbar() {
        setSupportActionBar(tb);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("秒签小哥");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
