package com.example.signgg.signgg.utils;

/**
 * Created by xiong on 2017/8/17.
 */

public interface Constant {


    //这里还放网址

    String IS_FRIST= "isfrist";
    String IS_LOGIN="islogin";

    String IS_IDENTITY= "isidentity";

    String IS_CHECKIDENTITY ="ischeckidentity";

    //测试
    String BASE_URL="http://192.168.3.112:8081/vx/document/receive";

    //测试
    String BAES_UNSIGN="{\"mobile\":\"18911315109\",\"appv\":\"v1\",\"timestamp\":\"1502980086119\"}";

    //发送短信
    String BASE_SEND_MSG="{\"mobile\":\"15222202530\",\"smscode\":\"9923\",\"appv\":\"v1\",\"timestamp\":\"1503214167365\"}";


    String BASE_SEND_URL="http://192.168.3.47/v1/channelOffline/message";


    //验证登录信息
    String SIGN_CHECK_URL="http://192.168.3.47/v1/channelOffline/check";




    //基础的测试连接
    String ssss="http://192.168.3.47/";

}
