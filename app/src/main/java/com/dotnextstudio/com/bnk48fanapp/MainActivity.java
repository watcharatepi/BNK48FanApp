package com.dotnextstudio.com.bnk48fanapp;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener {

    private final int INDEX_HOME = FragNavController.TAB1;
    private final int INDEX_CAL = FragNavController.TAB2;
    private final int INDEX_LIVE = FragNavController.TAB3;
    private final int INDEX_SNS = FragNavController.TAB4;
    private final int INDEX_MARKET = FragNavController.TAB5;
    private BottomBar mBottomBar;
    private FragNavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");


        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);
        mBottomBar.selectTabAtPosition(INDEX_HOME);


        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.container)
                .transactionListener(this)
                .rootFragmentListener(this, 5)
                .build();




        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
               ;
                switch (tabId) {
                    case R.id.tab_home:
                        Log.d("dev","home===>");
                        mNavController.switchTab(INDEX_HOME);
                        break;
                    case R.id.tab_cal:
                        Log.d("dev","cal===>");
                        mNavController.switchTab(INDEX_CAL);
                        break;
                    case R.id.tab_ds:
                        mNavController.switchTab(INDEX_LIVE);
                        break;
                    case R.id.tab_sns:
                        mNavController.switchTab(INDEX_SNS);
                        break;
                    case R.id.tab_market:
                        mNavController.switchTab(INDEX_MARKET);
                        break;
                }
            }
        });

        mBottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                mNavController.clearStack();
            }
        });

    }

    @Override
    public Fragment getRootFragment(int index) {

        switch (index) {
            case INDEX_HOME:
                return NewsFragment.newInstance(0);
            case INDEX_CAL:
                return CalFragment.newInstance(0);
            case INDEX_LIVE:
                return SocialFragment.newInstance(0);
            case INDEX_SNS:
                return NewsFragment.newInstance(0);
            case INDEX_MARKET:
                return NewsFragment.newInstance(0);
        }
        throw new IllegalStateException("Need to send an index that we know");
    }

    @Override
    public void onBackPressed() {
        if (!mNavController.isRootFragment()) {
            mNavController.popFragment();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mNavController != null) {
            mNavController.onSaveInstanceState(outState);
        }
    }

    @Override
    public void pushFragment(Fragment fragment) {
        if (mNavController != null) {
            mNavController.pushFragment(fragment);
        }
    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {
        // If we have a backstack, show the back button
        if (getSupportActionBar() != null && mNavController != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        }
    }


    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
        //do fragmentty stuff. Maybe change title, I'm not going to tell you how to live your life
        // If we have a backstack, show the back button
        if (getSupportActionBar() != null && mNavController != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        }
    }
}
