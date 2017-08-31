package com.example.signgg.signgg.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.signgg.signgg.Application.MyApplication;
import com.example.signgg.signgg.R;
import com.example.signgg.signgg.net.bean.Userinfoitembean;

import java.util.List;

/**
 * Created by xiong on 2017/8/28.
 */

public class Melistadapter extends BaseAdapter {

    private final Activity a;
    private  List<Userinfoitembean.DataBean> data;

    public Melistadapter(Activity a, List<Userinfoitembean.DataBean> data) {
        this.a=a;
        this.data=data;
    }

    @Override
    public int getCount() {
        return data.size();
        //return 10;
    }


    //这个bean本身就写的很乱。。
    @Override
    public Userinfoitembean.DataBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder;
        Userinfoitembean.DataBean item = getItem(position);
        if (convertView == null) {
            viewholder =new Viewholder();
            convertView = View.inflate(MyApplication.mcontext, R.layout.me_list_adapter, null);

            viewholder.iv = (ImageView) convertView.findViewById(R.id.ic_home);
            viewholder.tv= (TextView) convertView.findViewById(R.id.tv_home);

            convertView.setTag(viewholder);


        }else {
            viewholder = (Viewholder) convertView.getTag();
        }
            //假数据
            //viewholder.iv.setImageResource(R.drawable.needreplace1);

            //解析json数据，然后再获取图片
            Glide.with(a).load(item.getApi_img().getLocal_url()).into(viewholder.iv);

            viewholder.tv.setText(item.getName());

        //假数据
        //viewholder.tv.setText("我是条目");

        return convertView;
    }

    class  Viewholder{
        public  ImageView iv;
        public  TextView tv;
    }








}
