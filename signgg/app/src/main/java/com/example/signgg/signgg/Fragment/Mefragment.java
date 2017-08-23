package com.example.signgg.signgg.Fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.signgg.signgg.Application.MyApplication;
import com.example.signgg.signgg.net.httphelper;
import com.example.signgg.signgg.utils.Constant;
import com.example.signgg.signgg.utils.LogUtil;
import com.example.signgg.signgg.utils.Test3;
import com.example.signgg.signgg.utils.Test3Builder;

import java.util.HashMap;

/**
 * Created by xiong on 2017/8/17.
 */

public class Mefragment extends BaseFragment {
    @Override
    public View getsuccess() {

        TextView textView=new TextView(MyApplication.mcontext);
        //但是如果注释掉了下面两句话会一直显示loading
        textView.setTextColor(Color.GRAY);
        textView.setTextSize(15);
        textView.setText("个人界面");
        return textView;

    }

    @Override
    public void loadData() {


        //下面这一整串代码要封装起来
        Test3 t3=new Test3();
        HashMap<String, String> tomap = t3.tomap(Constant.BASE_SEND_MSG);
        String sortmap = t3.sortmap(tomap);
        String encode = Test3.encode(sortmap);
        String token = t3.addString(encode, "MIAOQIAN_API_TOKEN");
        String upwrite = t3.upwrite(token);
        String encode1 = Test3.encode(upwrite);
        String upwrite1 = t3.upwrite(encode1);


        //去除最后一个大括号(这里也要重新写个方法)
        String s=(Constant.BASE_SEND_MSG.substring(0,Constant.BASE_SEND_MSG.length()-1));

        String newsign=s+",\"sign\":\""+upwrite1+"\"}";
        Test3Builder t=new Test3Builder();
        String mylord = t.Mylord(Constant.BASE_SEND_MSG);
        LogUtil.e(mylord);

        LogUtil.e(newsign);


        httphelper.create().dopost(Constant.BASE_SEND_URL, newsign, new httphelper.httpcallback() {
            @Override
            public void success(String s) {
                differentstate.showsuccessView();
                LogUtil.e(s);

            }

            @Override
            public void fail(Exception e) {
                differentstate.showerrorView();

            }
        });

    }


}
