package com.example.shopmall.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopmall.R;
import com.example.shopmall.base.ConstansUrl;
import com.example.shopmall.bean.HomeResultBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HotGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<HomeResultBean.ResultBean.HotInfoBean> hotInfoBeanList;

    public HotGridViewAdapter(Context context, List<HomeResultBean.ResultBean.HotInfoBean> hotInfoBeanList) {
        this.context = context;
        this.hotInfoBeanList = hotInfoBeanList;
    }

    @Override
    public int getCount() {
        return hotInfoBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return hotInfoBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_hot_gv, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //对应条目的数据
        HomeResultBean.ResultBean.HotInfoBean hotInfoBean = hotInfoBeanList.get(position);
        Picasso.with(context).load(ConstansUrl.BASE_URl_IMAGE + hotInfoBean.getFigure()).into(viewHolder.iv_Hot);
        viewHolder.tv_name_hot.setText(hotInfoBean.getName());
        viewHolder.tv_price_hot.setText("￥"+hotInfoBean.getCover_price());
        return convertView;
    }

    class ViewHolder {
        ImageView iv_Hot;
        TextView tv_name_hot, tv_price_hot;

        ViewHolder(View itemView) {
            iv_Hot = (ImageView) itemView.findViewById(R.id.iv_hot_item);
            tv_name_hot = (TextView) itemView.findViewById(R.id.tv_name_hot_item);
            tv_price_hot = (TextView) itemView.findViewById(R.id.tv_price_hot_item);
        }
    }
}
