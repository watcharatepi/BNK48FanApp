package com.dotnextstudio.com.bnk48fanapp;

import android.os.Bundle;

/**
 * Created by watcharatepinkong on 6/30/17.
 */

public class SocialFragment extends SocialBaseFragment {
    public static SocialFragment  newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        SocialFragment fragment = new SocialFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onStart() {
        super.onStart();

    }
}
