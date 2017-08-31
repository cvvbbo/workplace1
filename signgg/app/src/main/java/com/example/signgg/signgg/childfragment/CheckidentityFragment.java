package com.example.signgg.signgg.childfragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.signgg.signgg.Fragment.BaseFragment;

/**
 * Created by xiong on 2017/8/28.
 */

public class CheckidentityFragment extends BaseFragment {
    @Override
    public View getsuccess() {
        TextView view=new TextView(getActivity());
        view.setText("www。baidu。com");
        view.setTextSize(16);
        view.setTextColor(Color.GRAY);
        return view;
    }

    @Override
    public void loadData() {
        differentstate.showsuccessView();

    }
}
