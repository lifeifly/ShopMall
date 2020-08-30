package com.example.shopmall.shoppingcart.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmall.R;
import com.example.shopmall.base.BaseFragment;
import com.example.shopmall.base.ConstansUrl;
import com.example.shopmall.bean.GoodsBean;
import com.example.shopmall.shoppingcart.adapter.RecyclerViewCart;
import com.example.shopmall.shoppingcart.utils.CartStorage;

import java.util.List;

import static android.view.View.VISIBLE;

public class ShoppingcartFragment extends BaseFragment implements View.OnClickListener {
    private TextView tv_edit_title;

    private RecyclerView rv_cart;
    private LinearLayout ll_check_all;
    private CheckBox checkBox_all;
    private TextView tv_shopcart_total;
    private Button btn_check_out;

    private LinearLayout ll_delete;
    private CheckBox cb_all;
    private Button btn_delete;
    private Button btn_collection;

    private LinearLayout empty_ll;
    private ImageView iv_empty;
    private TextView tv_empty;

    private RecyclerViewCart adapter;

    private static final int ACTION_EDIT = 0X111;
    private static final int ACTION_COMPLETED = 0X222;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.shoppongcart_fragment_layout, container, false);
        tv_edit_title = (TextView) view.findViewById(R.id.tv_shopcart_edit);

        rv_cart = (RecyclerView) view.findViewById(R.id.rv_cart);
        ll_check_all = (LinearLayout) view.findViewById(R.id.ll_check_all);
        checkBox_all = (CheckBox) view.findViewById(R.id.checkbox_all);
        tv_shopcart_total = (TextView) view.findViewById(R.id.tv_shopcart_total);
        btn_check_out = (Button) view.findViewById(R.id.btn_check_out);

        ll_delete = (LinearLayout) view.findViewById(R.id.ll_delete);
        cb_all = (CheckBox) view.findViewById(R.id.cb_all);
        btn_delete = (Button) view.findViewById(R.id.btn_delete);
        btn_collection = (Button) view.findViewById(R.id.btn_collection);

        empty_ll = (LinearLayout) view.findViewById(R.id.empty_ll);
        iv_empty = (ImageView) view.findViewById(R.id.iv_empty);
        tv_empty = (TextView) view.findViewById(R.id.tv_empty);

        btn_check_out.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_check_out.setOnClickListener(this);

        initListener();
        return view;
    }

    private void initListener() {
        //设置默认的编辑状态
        tv_edit_title.setTag(ACTION_EDIT);
        tv_edit_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int action = (int) v.getTag();
                if (ACTION_EDIT == action) {
                    //切换为完成状态
                    showDelete();
                } else {
                    //切换成编辑状态
                    hideDelete();
                }
            }
        });
    }

    private void hideDelete() {
        //1.设置文本和状态编辑
        tv_edit_title.setText("编辑");
        tv_edit_title.setTag(ACTION_EDIT);
        //2.变成勾选
        if (adapter != null) {
            adapter.checkAllOrNone(true);
            adapter.checkCheckedAllItem();
            adapter.showTotalPrice();
        }
        //3.删除视图显示
        ll_delete.setVisibility(View.GONE);
        //4.结算视图隐藏
        ll_check_all.setVisibility(VISIBLE);
    }

    private void showDelete() {
        //1.设置文本和状态完成
        tv_edit_title.setText("完成");
        tv_edit_title.setTag(ACTION_COMPLETED);
        //2.变成非勾选
        if (adapter != null) {
            adapter.checkAllOrNone(false);
            adapter.checkCheckedAllItem();
            adapter.showTotalPrice();
        }
        //3.删除视图显示
        ll_delete.setVisibility(VISIBLE);
        //4.结算视图隐藏
        ll_check_all.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_check_out) {
            //结算

        } else if (v == btn_delete) {
            //删除选中的
            adapter.deleteData();
            //校验状态
            adapter.checkCheckedAllItem();
            if (adapter.getItemCount()==0){
                emptyCart();
            }
        } else if (v == btn_collection) {

        }
    }



    @Override
    public void onResume() {
        super.onResume();
        showData();
    }

    //展示数据
    private void showData() {
        List<GoodsBean> goodsBeanList = CartStorage.getInstance().getAllData();
        if (goodsBeanList != null && goodsBeanList.size() > 0) {
            ll_check_all.setVisibility(VISIBLE);
            empty_ll.setVisibility(View.GONE);
            tv_edit_title.setVisibility(VISIBLE);
            ll_check_all.setVisibility(VISIBLE);
            //有数据
            //设置适配器
            adapter = new RecyclerViewCart(context, goodsBeanList, checkBox_all, tv_shopcart_total, cb_all);
            rv_cart.setAdapter(adapter);
            //设置布局管理器
            rv_cart.setLayoutManager(new LinearLayoutManager(context));

        } else {
            //没有数据
            //显示没有数据的布局
            emptyCart();
        }
    }
    public void emptyCart(){
        empty_ll.setVisibility(VISIBLE);
        tv_edit_title.setVisibility(View.GONE);
        ll_delete.setVisibility(View.GONE);
        ll_check_all.setVisibility(View.GONE);
    }


}
