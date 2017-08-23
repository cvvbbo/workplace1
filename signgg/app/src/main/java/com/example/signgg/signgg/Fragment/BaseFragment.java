package com.example.signgg.signgg.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.signgg.signgg.Application.MyApplication;
import com.example.signgg.signgg.view.ui.Differentstateview;

/**
 * Created by xiong on 2017/8/16.
 */

public abstract class BaseFragment extends Fragment {


    public Differentstateview differentstate;
    Handler handler=new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //这个是复用，复用有坑，慎用，暂时没有理解2017.8.17
        //if (differentstate!=null) {
            differentstate = new Differentstateview(getContext());
            differentstate.getSuccessview(getsuccess());
            differentstate.showlodingView();
            //显示成功数据的界面一开始并不显示
            //differentstate.showsuccessView();
           // differentstate.getSuccessview(getsuccess());
            differentstate.setOnResloadlingListener(new Differentstateview.OnResloadling() {
                @Override
                public void load() {
                    differentstate.showlodingView();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            loadData();
//                        }
//                    },800);

                    loadData();

                }
            });
        //}

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loadData();
//            }
//        },800);

        loadData();

        return differentstate;
    }



    public abstract  View getsuccess();

    public  abstract void loadData();
}
