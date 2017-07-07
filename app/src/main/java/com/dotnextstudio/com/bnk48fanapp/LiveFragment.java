package com.dotnextstudio.com.bnk48fanapp;

import android.os.Bundle;

/**
 * Created by watcharatepinkong on 6/30/17.
 */

public class LiveFragment extends LiveBaseFragment {
    public static LiveBaseFragment  newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        LiveBaseFragment fragment = new LiveBaseFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onStart() {
        super.onStart();

    }
}
