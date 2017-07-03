package com.dotnextstudio.com.bnk48fanapp;


import android.os.Bundle;
public class NewsFragment extends BaseFragment {

    public static NewsFragment  newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onStart() {
        super.onStart();

    }


}