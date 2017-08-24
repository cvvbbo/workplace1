package com.example.signgg.signgg.Fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.signgg.signgg.Application.MyApplication;
import com.example.signgg.signgg.net.httphelper;
import com.example.signgg.signgg.utils.Constant;
import com.example.signgg.signgg.utils.LogUtil;
import com.example.signgg.signgg.utils.SignUtils;
import com.example.signgg.signgg.utils.Test3;
import com.example.signgg.signgg.utils.Test3Builder;
import com.example.signgg.signgg.utils.sharepreferenceUtils;

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

        //这个号码也是方法sp存储里面的
        String getphone = sharepreferenceUtils.getStringdata(MyApplication.mcontext, "getphone", "");
        LogUtil.e(getphone);

        String getuserinfo = SignUtils.Getuserinfo(getphone);

        String mylord = Test3Builder.Mylord(getuserinfo);


        httphelper.create().dopost(Constant.GET_USER_INFO_URL, mylord, new httphelper.httpcallback() {
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
