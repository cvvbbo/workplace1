package com.example.signgg.signgg.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.signgg.signgg.Application.MyApplication;
import com.example.signgg.signgg.R;
import com.example.signgg.signgg.adapter.Melistadapter;
import com.example.signgg.signgg.childfragment.CheckidentityFragment;
import com.example.signgg.signgg.net.bean.Userinfoitembean;
import com.example.signgg.signgg.net.httphelper;
import com.example.signgg.signgg.utils.Constant;
import com.example.signgg.signgg.utils.GsonUtil;
import com.example.signgg.signgg.utils.LogUtil;
import com.example.signgg.signgg.utils.SignUtils;
import com.example.signgg.signgg.utils.Test3Builder;
import com.example.signgg.signgg.utils.sharepreferenceUtils;
import com.example.signgg.signgg.view.activity.CheckidentityActivity;
import com.example.signgg.signgg.view.activity.UppersonidentityActivity;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xiong on 2017/8/17.
 */

public class Mefragment extends BaseFragment {
    @BindView(R.id.useinfor)
    TextView useinfor;
    @BindView(R.id.rltext1)
    RelativeLayout rltext1;
    Unbinder unbinder;
    private ListView listView;
    private View view;
    private RelativeLayout relativeLayout;
    private List<Userinfoitembean.DataBean> data1 =new ArrayList<>();
    private Melistadapter adapter;


    @Override
    public View getsuccess() {

//        TextView view = new TextView(MyApplication.mcontext);
//        //但是如果注释掉了下面两句话会一直显示loading
//        view.setTextColor(Color.GRAY);
//       view.setTextSize(15);
//        view.setText("个人界面");
//        return view;
        view = View.inflate(getActivity(), R.layout.me_fragment, null);
        listView = (ListView) view.findViewById(R.id.me_list);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.rltext1);

        LogUtil.e("有值么"+data1);

        adapter = new Melistadapter(getActivity(),data1);
        listView.setAdapter(adapter);
        return view;

    }

    @Override
    public void loadData() {

        //这个号码也是方法sp存储里面的，因为获取个人中心界面，需要上传的数据还有自己的手机号
//        String getphone = sharepreferenceUtils.getStringdata(MyApplication.mcontext, "getphone", "");
//        LogUtil.e(getphone);
//        //拼接字符串
//        String getuserinfo = SignUtils.Getuserinfo(getphone);
//        //加密
//        String mylord = Test3Builder.Mylord(getuserinfo);
//        httphelper.create().dopost(Constant.GET_USER_INFO_URL, mylord, new httphelper.httpcallback() {
//            @Override
//            public void success(String s) {
//                differentstate.showsuccessView();
//                LogUtil.e(s);
//
//            }
//
//            @Override
//            public void fail(Exception e) {
//                differentstate.showerrorView();
//
//            }
//        });

        String me_page = sharepreferenceUtils.getStringdata(getActivity(), "me_page", "");
        if (!TextUtils.isEmpty(me_page)){
            Parse(me_page);
            LogUtil.e("通过缓存进来");
        }
        getDate();
        LogUtil.e("第一次进来");

//        String getuseriteminfo = SignUtils.Getuseriteminfo();
//        String mylord1 = Test3Builder.Mylord(getuseriteminfo);
//        httphelper.create().dopost(Constant.GET_USERPAGER_PAGER, mylord1, new httphelper.httpcallback() {
//            @Override
//            public void success(String s) {
//                //GsonUtil.parseJsonToList(s,new TypeToken<List<Userinfoitembean>>(){}.getType());
//                // differentstate.showsuccessView();
//                LogUtil.e(s);
//
//
//
//                //缓存json
//                //sharepreferenceUtils.saveStringdata(getActivity(),"me_page",s);
//
//
//               // Parse(s);
//
//                Userinfoitembean userinfoitembean = GsonUtil.parseJsonToBean(s, Userinfoitembean.class);
//                List<Userinfoitembean.DataBean> itemdata = userinfoitembean.getData();
//
//                //先创建一个集合，然后把解析的集合数据放在自己创建的集合里面
//                data.addAll(itemdata);
//                adapter.notifyDataSetChanged();
//                differentstate.showsuccessView();
//
//            }
//
//            @Override
//            public void fail(Exception e) {
//
//            }
//        });


    }

    private void getDate() {
        //第二个接口
        String getuseriteminfo = SignUtils.Getuseriteminfo();
        String mylord1 = Test3Builder.Mylord(getuseriteminfo);
        httphelper.create().dopost(Constant.GET_USERPAGER_PAGER, mylord1, new httphelper.httpcallback() {
            @Override
            public void success(String s) {
                //GsonUtil.parseJsonToList(s,new TypeToken<List<Userinfoitembean>>(){}.getType());
               // differentstate.showsuccessView();
                LogUtil.e(s);



                //缓存json
                sharepreferenceUtils.saveStringdata(getActivity(),"me_page",s);


                Parse(s);

            }

            @Override
            public void fail(Exception e) {

            }
        });
    }

    private void Parse(String s) {
        Userinfoitembean userinfoitembean = GsonUtil.parseJsonToBean(s, Userinfoitembean.class);
        List<Userinfoitembean.DataBean> itemdata = userinfoitembean.getData();

        //先创建一个集合，然后把解析的集合数据放在自己创建的集合里面
        data1.addAll(itemdata);
        adapter.notifyDataSetChanged();
        differentstate.showsuccessView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                switch (position) {
                    case 0:
                      //用不用fragment以后再说吧。。
                        Intent intent=new Intent(getActivity(), UppersonidentityActivity.class);
                        //startActivity(intent);
                        startActivityForResult(intent,1);


                        break;
                }


            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==2){
            String user_name = data.getStringExtra("user_name");
            LogUtil.e("返回的界面"+user_name);

            //移除第一个
            data1.remove(0);

            adapter.notifyDataSetChanged();


        }

    }
}
