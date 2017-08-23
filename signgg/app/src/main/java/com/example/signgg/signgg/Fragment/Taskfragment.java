package com.example.signgg.signgg.Fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.signgg.signgg.Application.MyApplication;

/**
 * Created by xiong on 2017/8/17.
 */

public class Taskfragment extends  BaseFragment {
    @Override
    public View getsuccess() {
        TextView textView=new TextView(MyApplication.mcontext);
        textView.setTextColor(Color.GRAY);
        textView.setTextSize(15);
        textView.setText("任务界面");
        return textView;
    }

    @Override
    public void loadData() {
        differentstate.showsuccessView();

    }


}
