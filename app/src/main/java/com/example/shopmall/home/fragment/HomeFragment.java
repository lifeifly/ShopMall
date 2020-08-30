package com.example.shopmall.home.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmall.R;
import com.example.shopmall.base.BaseFragment;
import com.example.shopmall.base.ConstansUrl;
import com.example.shopmall.bean.HomeResultBean;
import com.example.shopmall.home.adapter.HomeRecyclerViewAdapter;
import com.google.gson.Gson;

public class HomeFragment extends BaseFragment {
    private HomeRecyclerViewAdapter adapter;
    private TextView tv_search_titlebar, tv_message_titlebar;
    private RecyclerView rv_home;
    private ImageButton ib_home;



    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.home_fragment_layout, container, false);
        tv_search_titlebar = (TextView) view.findViewById(R.id.tv_search_home);
        tv_message_titlebar = (TextView) view.findViewById(R.id.tv_message_home);
        rv_home = (RecyclerView) view.findViewById(R.id.rv_home);
        ib_home = (ImageButton) view.findViewById(R.id.ib_home);
        //为ib_home设置监听，点击它时会回到顶部
        ib_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回到顶部
                rv_home.scrollToPosition(0);
            }
        });
        return view;
    }

    @Override
    public void onSuccess(String result) {
        super.onSuccess(result);
        HomeResultBean homeResultBean = new Gson().fromJson(result, HomeResultBean.class);
        HomeResultBean.ResultBean resultBean = homeResultBean.getResult();
        if (resultBean != null) {
            adapter = new HomeRecyclerViewAdapter(context, resultBean);
            rv_home.setAdapter(adapter);

            //需要设置布局管理者才能显示
            //需要使用GridLayoutManager，因为它有跨度监听方法
            GridLayoutManager layoutManager = new GridLayoutManager(context, 1);
            rv_home.setLayoutManager(layoutManager);
            //为布局管理者设置跨度监听
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    //position代表位置用来代表几个跨度
                    if (position <= 3) {
                        //隐藏ib——button
                        ib_home.setVisibility(View.GONE);
                    } else {
                        //显示ib_button
                        ib_home.setVisibility(View.VISIBLE);
                    }
                    //只能显示1
                    return 1;
                }
            });
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        super.onError(ex, isOnCallback);
        Log.d("Main", ex.getMessage());
    }
}
