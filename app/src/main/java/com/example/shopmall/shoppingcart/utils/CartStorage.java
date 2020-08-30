package com.example.shopmall.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.example.shopmall.activity.MyApp;
import com.example.shopmall.bean.GoodsBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class CartStorage {
    public static final String JSON_CART = "json_cart";
    private static CartStorage instance;
    private Context context;
    //SparseArray的性能好于HashMap
    private SparseArray<GoodsBean> sparseArray;

    private CartStorage(Context context) {
        this.context = context;
        //把之前存储的数据读取出来
        sparseArray = new SparseArray<>(100);
        //列表转换成SparseArray
        listToSparseArray();
    }
    public static CartStorage getInstance() {
        if (instance == null) {
            instance = new CartStorage(MyApp.getContext());
        }
        return instance;
    }
    //从本地读取的数据加入到SparseArray中
    private void listToSparseArray() {
        List<GoodsBean> goodsBeansList = getAllData();
        //把列表转换成SparseArray
        if (goodsBeansList!=null&&goodsBeansList.size()>0) {
            for (int i = 0; i < goodsBeansList.size(); i++) {
                GoodsBean goodsBean = goodsBeansList.get(i);
                sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);
            }
        }
    }
    private List<GoodsBean> sparseToList() {
        List<GoodsBean> goodsBeans = new ArrayList<>();
        for (int i = 0; i < sparseArray.size(); i++) {
            GoodsBean goodsBean = sparseArray.valueAt(i);
            goodsBeans.add(goodsBean);
        }
        return goodsBeans;
    }
    //获取本地数据
    public List<GoodsBean> getAllData() {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        //1.从本地获取2.使用Gson转换成列表
        String json = CacheUtils.getString(context, JSON_CART);
        if (!TextUtils.isEmpty(json)) {
//直接把String类型转换成List
            goodsBeanList = new Gson().fromJson(json, new TypeToken<List<GoodsBean>>() {
            }.getType());
        }
        return goodsBeanList;
    }

    //添加数据
    public void addData(GoodsBean goodsBean) {
        //先检查是否被添加过
        GoodsBean tempGoods = sparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));
        if (tempGoods != null) {
            //已被添加，只需改变Number
            tempGoods.setNumber(tempGoods.getNumber() + 1);
        } else {
            //没被添加
            tempGoods = goodsBean;
            tempGoods.setNumber(1);
        }
        //同步到内存
        sparseArray.put(Integer.parseInt(tempGoods.getProduct_id()), tempGoods);
        //同步到本地
        saveLocal();
    }

    //删除数据
    public void deleteData(GoodsBean goodsBean) {
        //1.从内存删除
        sparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));
        //2.把内存保存到本地
        saveLocal();
    }

    //改变数据
    public void updateData(GoodsBean goodsBean) {
        //内存
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);
        saveLocal();
    }

    //保存数据到本地
    private void saveLocal() {
        //把内存转换成列表
        List<GoodsBean> list = sparseToList();
//把列表转换成String类型
        String json = new Gson().toJson(list);
        //把String类型保存
        CacheUtils.saveString(context, JSON_CART, json);
    }
}
