package com.example.shopmall.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.example.shopmall.R;
import com.example.shopmall.community.fragment.CommunityFragment;
import com.example.shopmall.home.fragment.HomeFragment;
import com.example.shopmall.shoppingcart.fragment.ShoppingcartFragment;
import com.example.shopmall.type.fragment.TypeFragment;
import com.example.shopmall.user.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private FrameLayout mainFrameLayout;
    private RadioGroup mainRg;
    private int positon = 0;//选中的fragment位置
    private List<Fragment> fragmentList;//放fragment的集合
    private Fragment tempFragment;//上次显示的fragment
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainFrameLayout = (FrameLayout) findViewById(R.id.main_frame);
        mainRg = (RadioGroup) findViewById(R.id.main_rg);
        initFragment();
        initListener();
        initData();
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new TypeFragment());
        fragmentList.add(new CommunityFragment());
        fragmentList.add(new ShoppingcartFragment());
        fragmentList.add(new UserFragment());
    }

    private void initData() {
    }

    private void initListener() {

        //设置RadioGroup的监听器,并让fragment随之变动
        mainRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.main_rg_home:
                        positon = 0;
                        break;
                    case R.id.main_rg_type:
                        positon = 1;
                        break;
                    case R.id.main_rg_community:
                        positon = 2;
                        break;
                    case R.id.main_rg_cart:
                        positon = 3;
                        break;
                    case R.id.main_rg_user:
                        positon = 4;
                        break;
                }
                Fragment nowFragment = fragmentList.get(positon);
                //设置选中的fragment
                switchFragment(tempFragment, nowFragment);
            }
        });
        //默认进入选中首页
        mainRg.check(R.id.main_rg_home);
    }

    private void switchFragment(Fragment preFragment, Fragment nowFragment) {
        //若切换前后界面不相同则进行切换
        if (preFragment != nowFragment) {
            FragmentManager fm = getSupportFragmentManager();
            //开启事务
            FragmentTransaction transaction = fm.beginTransaction();
            if (!nowFragment.isAdded())
            //如果要显示的页面没有被添加过
            {
                if (preFragment != null) {
                    transaction.hide(preFragment);
                }
                //如果to页面不为空，则添加到FrameLayout中
                transaction.add(R.id.main_frame, nowFragment).commit();
            } else {
                if (preFragment != null) {
                    transaction.hide(preFragment);
                }
                //如果to页面不为空，则直接显示
                transaction.show(nowFragment).commit();
            }
            tempFragment = nowFragment;
        }

    }
}
