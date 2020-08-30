package com.example.addsubactivity;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class AddSubLayout extends LinearLayout implements View.OnClickListener{
    private ImageView iv_add;
    private ImageView iv_sub;
    private TextView tv_number;



    //默认值
    private int value=1;
    //最小值
    private int minValue=1;
    //最大值
    private int maxValue=5;

    public AddSubLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.add_sub_layout, this);
        iv_add = findViewById(R.id.iv_add);
        iv_sub = findViewById(R.id.iv_sub);
        tv_number = findViewById(R.id.tv_number);

        int currentValue = getValue();
        setValue(currentValue);
        iv_sub.setOnClickListener(this);
        iv_add.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_add:
                addNumber();
                break;
            case R.id.iv_sub:
                subNumber();
                break;
        }

    }

    private void addNumber() {
        if (value<maxValue){
            value++;
            tv_number.setText(value+"");
        }
        if (listener!=null){
            listener.onNumberChange(value);
        }
    }

    private void subNumber() {
        if (value>minValue){
            value--;
            tv_number.setText(value+"");
        }
        listener.onNumberChange(value);
    }

    public int getValue() {
        String valueStr=tv_number.getText().toString().trim();
        if (!TextUtils.isEmpty(valueStr)){
            value=Integer.parseInt(valueStr);
        }
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
    //当数据发生变化的监听接口
    public interface NumberChangeListener{
        void onNumberChange(int value);
    }
    private NumberChangeListener listener;

    public NumberChangeListener getListener() {
        return listener;
    }

    public void setListener(NumberChangeListener listener) {
        this.listener = listener;
    }
}
