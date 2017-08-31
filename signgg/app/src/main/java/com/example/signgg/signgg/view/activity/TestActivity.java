package com.example.signgg.signgg.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.signgg.signgg.R;
import com.example.signgg.signgg.net.httphelper;
import com.example.signgg.signgg.utils.Constant;
import com.example.signgg.signgg.utils.LogUtil;
import com.example.signgg.signgg.utils.Netutils;
import com.example.signgg.signgg.utils.SignUtils;
import com.example.signgg.signgg.utils.Test3Builder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xiong on 2017/8/17.
 */

public class TestActivity  extends Activity {


    //handler和thread的区别
    Handler handler=new Handler(){};
    String s;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_view);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                OkHttpClient client=  new OkHttpClient.Builder().readTimeout(2,TimeUnit.SECONDS).
//                        connectTimeout(2,TimeUnit.SECONDS).build();
//                Request request=new Request.Builder().post().url(s).build();
//                Call call = client.newCall(request);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//
//                    }
//                });
//
//
//            }
//        });

        JSONObject json=new JSONObject();
        try {
            json.put("as","bb");
            json.put("as","bb");
            json.put("as","bb");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String a="\""+json+"\"";


        //String newsign=s+",\"sign\":\""+upwrite1+"\"}";
        LogUtil.e(a);
        LogUtil.e(json.toString());

//
//        Date d = new Date();
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
//        String format = sf.format(d);
//        System.out.println(format);
//
//       // long time=System.currentimeMillis()/1000;//获取系统时间的10位的时间戳
//        long l = System.currentTimeMillis() ;
//
//        String  str=String.valueOf(l);
//        System.out.println(str);

//        new Thread(){
//            @Override
//            public void run() {
//                if (Netutils.isNetworkAvalible(TestActivity.this)){
//                    Log.e("网络是否可用","Y");
//                }else {
//                    Log.e("网络不可用","N");
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Netutils.checkNetwork(TestActivity.this);
//
//                        }
//                    });
//
//                }
//
//            }
//        }.start();

        String getuseriteminfo = SignUtils.Getuseriteminfo();
        String mylord = Test3Builder.Mylord(getuseriteminfo);

        String getuserinfo = SignUtils.Getuserinfo("18134107080");
        String mylord1 = Test3Builder.Mylord(getuserinfo);



        httphelper.create().dopost(Constant.GET_USERPAGER_PAGER, mylord, new httphelper.httpcallback() {
            @Override
            public void success(String s) {
                LogUtil.e(s);

            }

            @Override
            public void fail(Exception e) {
                LogUtil.e(e+"服务器失败。。");

            }
        });


    }
}
