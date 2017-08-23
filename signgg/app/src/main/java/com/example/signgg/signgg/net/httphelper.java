package com.example.signgg.signgg.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.example.signgg.signgg.Application.MyApplication;
import com.example.signgg.signgg.utils.LogUtil;
import com.example.signgg.signgg.utils.sharepreferenceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by xiong on 2017/8/15.
 */

public class httphelper {

    //以后凡是自己写的每一个类都要仔细检查！！！。。。

    //校验json数据的
    public static final MediaType JSON= MediaType.parse("application/json; charset=utf-8");

    private static httphelper helpter=new httphelper();
    private final OkHttpClient okHttpClient;
    //尝试在联网的工具类里面就试着让更新界面执行在主线程
    Handler handler=new Handler();
    private FileOutputStream fos;


    public  static httphelper create(){

        return helpter;

    }


    private   httphelper(){

//        CookieJar cookieJar=new CookieJar() {
//
//            private final Map<String, List<Cookie>> cookiesMap = new HashMap<String, List<Cookie>>();
//
//            @Override
//            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//
//                //清除相同的cookie
//                String host = url.host();
//                List<Cookie> cookiesList = cookiesMap.get(host);
//                if (cookiesList != null){
//                    cookiesMap.remove(host);
//                }
//                //再重新天添加
//                cookiesMap.put(host, cookies);
//                Log.e("有cookie",cookiesList+"");
//
//            }
//
//            @Override
//            public List<Cookie> loadForRequest(HttpUrl url) {
//                List<Cookie> cookiesList = cookiesMap.get(url.host());
//                //注：这里不能返回null，否则会报NULLException的错误。
//                //原因：当Request 连接到网络的时候，OkHttp会调用loadForRequest()
//
//                Log.e("---",cookiesMap+"");
//                return cookiesList != null ? cookiesList : new ArrayList<Cookie>();
//            }
//        };



        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(2, TimeUnit.SECONDS)
                .connectTimeout(2,TimeUnit.SECONDS)
                .addInterceptor(new AddCookiesInterceptor())
                .addInterceptor(new ReceivedCookiesInterceptor()).build();


    }


    public void doget(String url, final httpcallback callback){
        Request request=new Request.Builder().get().url(url).build();
        Call call = okHttpClient.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, final IOException e) {

                                //handler是专门用来更新ui的。下面这个post方法，相当于runonuithread
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        //看清楚，上面的是返回failure方法。。。智障的错误
                                        //callback.fail();
                                        callback.fail(e);

                    }
                });

               // callback.fail(e);


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                final String string = body.string();


                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //callback.fail();
                        callback.success(string);
                    }
                });

                //callback.success(string);


            }
        });
    }

    //验证签名。
    public void dopost(String url, String newsign, final httpcallback h){
        final Request request=new Request.Builder().url(url).post(RequestBody.create(JSON,newsign)).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        h.fail(e);
                    }
                });


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                ResponseBody body = response.body();
                final String string = body.string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        h.success(string);
                    }
                });


            }
        });

    }


   public  interface  httpcallback{
       void success(String s);
       void fail(Exception e);
   }

    public class AddCookiesInterceptor implements Interceptor {


        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
//            HashSet<String> preferences = (HashSet) Preferences.getDefaultPreferences()
//                    .getStringSet(Preferences.PREF_COOKIES, new HashSet<>());
            HashSet<String> preferences= sharepreferenceUtils.getHashsetdata(MyApplication.mcontext, "cookie", new HashSet<String>());


            for (String cookie : preferences) {
                //后台cookie的开头就是叫cookie。。。
                builder.addHeader("Cookie", cookie);
                Log.e("OkHttp", "Adding Header: " + cookie);
                // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
            }


            return chain.proceed(builder.build());
        }
    }

    public class ReceivedCookiesInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());

            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                HashSet<String> cookies = new HashSet<>();

                for (String header : originalResponse.headers("Set-Cookie")) {
                    cookies.add(header);
                    Log.e("---Received",cookies+"");
                }


                sharepreferenceUtils.saveHashsetdata(MyApplication.mcontext,"cookie",cookies);
            }

            return originalResponse;
        }
    }

}
