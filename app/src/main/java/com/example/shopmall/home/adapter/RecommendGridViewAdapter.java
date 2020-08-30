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

public class RecommendGridViewAdapter extends BaseAdapter {
    private HomeResultBean.ResultBean resultBean;
    private Context context;

    public RecommendGridViewAdapter(HomeResultBean.ResultBean resultBean, Context context) {
        this.resultBean = resultBean;
        this.context = context;
    }

    @Override
    public int getCount() {
        return resultBean.getRecommend_info().size();
    }

    @Override
    public Object getItem(int position) {
        return resultBean.getRecommend_info().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_recommend_gv_layout,parent,false);
            holder= new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        HomeResultBean.ResultBean.RecommendInfoBean recommendInfoBean=resultBean.getRecommend_info().get(position);
        Picasso.with(context).load(ConstansUrl.BASE_URl_IMAGE+recommendInfoBean.getFigure()).into(holder.iv_recommend_item);
        holder.tv_recommend_name.setText(recommendInfoBean.getName());
        holder.tv_recommend_price.setText("￥"+recommendInfoBean.getCover_price());
        return convertView;
    }

    static class ViewHolder {
        ImageView iv_recommend_item;
        TextView tv_recommend_name, tv_recommend_price;
        ViewHolder(View itemView){
            iv_recommend_item=(ImageView)itemView.findViewById(R.id.iv_recommend_item);
            tv_recommend_name=(TextView)itemView.findViewById(R.id.tv_recommend_name);
            tv_recommend_price=(TextView)itemView.findViewById(R.id.tv_recommend_price);
        }
    }
}
