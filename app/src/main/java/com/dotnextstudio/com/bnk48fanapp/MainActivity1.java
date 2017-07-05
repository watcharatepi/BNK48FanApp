package com.dotnextstudio.com.bnk48fanapp;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity1 extends AppCompatActivity  {
    //Better convention to properly name the indices what they are in your app
    private final int INDEX_RECENTS = FragNavController.TAB1;
    private final int INDEX_FAVORITES = FragNavController.TAB2;
    private final int INDEX_NEARBY = FragNavController.TAB3;
    private final int INDEX_FRIENDS = FragNavController.TAB4;
    private final int INDEX_FOOD = FragNavController.TAB5;
    private BottomBar mBottomBar;
    private FragNavController mNavController;

    public FragmentManager mFragmentManager;

    public Fragment fragment1 = new NewsFragment().newInstance(0);
    public Fragment fragment2 = new CalFragment().newInstance(0);
    public Fragment mFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);


        mFragmentManager = getSupportFragmentManager();

       // addHideFragment(fragment1);
        addHideFragment(fragment2);

        mFragmentManager.beginTransaction().add(R.id.container, fragment1).commit();
        mFragment = fragment1;
        mBottomBar.selectTabAtPosition(INDEX_RECENTS);



        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_home:
                        setTitle("Hot News");

                        hideShowFragment(mFragment, fragment1);
                        mFragment = fragment1;
                        break;
                    case R.id.tab_cal:
                        setTitle("Calendar");
                        hideShowFragment(mFragment, fragment2);
                        mFragment = fragment2;
                        break;

                }
            }
        });

       /* mBottomBar.selectTabAtPosition(INDEX_NEARBY);
        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.container)
                .transactionListener(this)
                .rootFragmentListener(this, 5)
                .build();





        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_home:
                        mNavController.switchTab(INDEX_RECENTS);
                        break;
                    case R.id.tab_cal:
                        mNavController.switchTab(INDEX_FAVORITES);
                        break;
                    case R.id.tab_sns:
                        mNavController.switchTab(INDEX_NEARBY);
                        break;
                    case R.id.tab_ds:
                        mNavController.switchTab(INDEX_FRIENDS);
                        break;
                    case R.id.tab_market:
                        mNavController.switchTab(INDEX_FOOD);
                        break;
                }
            }
        });

        mBottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                mNavController.clearStack();
            }
        });*/


        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(getResources().getDrawable(R.drawable.testprofile))

                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .inflateMenu(R.menu.menu)
                .withTranslucentStatusBar(false)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            // Toast.makeText(MenuDrawerActivity.this, ((Nameable) drawerItem).getName().getText(MenuDrawerActivity.this), Toast.LENGTH_SHORT).show();
                        }

                        return false;
                    }
                }).build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

//        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

    }

    private void addHideFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().add(R.id.container, fragment).hide(fragment).commit();
    }

    //Method to hide and show the fragment you need. It is called in the BottomBar click listener.
    private void hideShowFragment(Fragment hide, Fragment show) {
        mFragmentManager.beginTransaction().hide(hide).show(show).commit();
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




}