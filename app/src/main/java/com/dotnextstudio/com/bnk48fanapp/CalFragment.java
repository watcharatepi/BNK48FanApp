package com.dotnextstudio.com.bnk48fanapp;

import android.os.Bundle;

/**
 * Created by watcharatepinkong on 6/30/17.
 */

public class CalFragment extends CalBaseFragment {
    public static CalFragment  newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        CalFragment fragment = new CalFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onStart() {
        super.onStart();

    }
}
