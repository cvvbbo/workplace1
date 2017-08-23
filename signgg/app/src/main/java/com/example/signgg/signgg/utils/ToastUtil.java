package com.example.signgg.signgg.utils;

import android.widget.Toast;

import com.example.signgg.signgg.Application.MyApplication;

/**
 * Created by xiong on 2017/8/17.
 */

public class ToastUtil {


    private static Toast toast;

    public static void showToast(String msg) {
        if (toast == null) {
            //进行初始化
            toast = Toast.makeText(MyApplication.mcontext, msg, Toast.LENGTH_SHORT);
        } else {
            //说明不为空,只改变吐司的文字内容
            toast.setText(msg);
        }
        //最后再show
        toast.show();
    }

}