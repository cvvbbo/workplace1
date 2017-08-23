package com.example.signgg.signgg.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xiong on 2017/8/22.
 */

public class SignUtils {

    //发送验证码的签名验证
    public static String getsmsSign(String phone,String code){
        JSONObject json=new JSONObject();
        //记录当前时间，13位完整的
        long l = System.currentTimeMillis() ;
        String  str=String.valueOf(l);
        try {
            json.put("timestamp", str);
            json.put("appv","v1");
            json.put("mobile", phone);
            json.put("smscode", code);
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return json.toString();

    }

    public static String sendsmsSign(String phone){
        JSONObject json=new JSONObject();
        //记录当前时间，13位完整的
        long l = System.currentTimeMillis() ;
        String  str=String.valueOf(l);
        try {
            json.put("mobile", phone);
            json.put("appv","v1");
            json.put("timestamp", str);
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return json.toString();

    }

    public void Getuserinfo(String phone){
        JSONObject json=new JSONObject();
        long l = System.currentTimeMillis() ;
        String  str=String.valueOf(l);
        try {
            json.put("mobile",phone);
            json.put("timestamp", str);
            json.put("appv","v1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //获取时间戳
    public static String gettimestamp(){
        long l = System.currentTimeMillis() ;
        String  str=String.valueOf(l);
        return str;
    }

}
