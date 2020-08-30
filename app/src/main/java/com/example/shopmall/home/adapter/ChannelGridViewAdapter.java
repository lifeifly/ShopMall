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

public class ChannelGridViewAdapter extends BaseAdapter {
    private List<HomeResultBean.ResultBean.ChannelInfoBean> channelInfoBeanList;
    private Context context;

    public ChannelGridViewAdapter(List<HomeResultBean.ResultBean.ChannelInfoBean> channelInfoBeanList, Context context) {
        this.channelInfoBeanList = channelInfoBeanList;
        this.context = context;
    }



    @Override
    public int getCount() {
        return channelInfoBeanList == null ? 0 : channelInfoBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return channelInfoBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_channel_gv,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        HomeResultBean.ResultBean.ChannelInfoBean channelInfoBean=channelInfoBeanList.get(position);
        holder.tvGridView.setText(channelInfoBean.getChannel_name());
        Picasso.with(context).load(ConstansUrl.BASE_URl_IMAGE+channelInfoBean.getImage()).into(holder.ivGridView);
        return convertView;
    }
    class ViewHolder{
        private ImageView ivGridView;
        private TextView tvGridView;

        public ViewHolder(View itemView) {
            ivGridView=(ImageView)itemView.findViewById(R.id.iv_channel);
            tvGridView=(TextView)itemView.findViewById(R.id.tv_channel);
        }
    }
}
