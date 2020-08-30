package com.example.shopmall.shoppingcart.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheUtils {
    //得到保存的String类型的数据
    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("atguigu", Context.MODE_PRIVATE);
        String string = sp.getString(key, "");
        return string;
    }

    public static void saveString(Context context, String key,String value) {
        SharedPreferences sp = context.getSharedPreferences("atguigu", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(key,value).apply();

    }
}
