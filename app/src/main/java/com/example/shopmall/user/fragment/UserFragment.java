package com.example.shopmall.user.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopmall.R;
import com.example.shopmall.base.BaseFragment;

public class UserFragment extends BaseFragment {


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.user_fragment_layout, container, false);
    }
}
