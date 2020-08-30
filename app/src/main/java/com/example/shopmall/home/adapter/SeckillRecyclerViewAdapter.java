package com.example.shopmall.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmall.R;
import com.example.shopmall.base.ConstansUrl;
import com.example.shopmall.bean.HomeResultBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SeckillRecyclerViewAdapter extends RecyclerView.Adapter<SeckillRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<HomeResultBean.ResultBean.SeckillInfoBean.ListBean> seckillInfoBeanList;

    public SeckillRecyclerViewAdapter(Context context, List<HomeResultBean.ResultBean.SeckillInfoBean.ListBean> listBean) {
        this.context = context;
        seckillInfoBeanList = listBean;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(context, R.layout.item_seckill_rv, null), context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.setData(position);
    }


    @Override
    public int getItemCount() {
        return seckillInfoBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_seckill;
        private TextView tv_now_price, tv_origin_price;
        private Context context;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            iv_seckill = (ImageView) itemView.findViewById(R.id.iv_figure);
            tv_now_price = (TextView) itemView.findViewById(R.id.tv_cover_price);
            tv_origin_price = (TextView) itemView.findViewById(R.id.tv_origin_price);

            //设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (seckillClick!=null){
                        seckillClick.onItemClick(getLayoutPosition());
                    }
                }
            });
        }

        public void setData(int position) {
            HomeResultBean.ResultBean.SeckillInfoBean.ListBean listBean = seckillInfoBeanList.get(position);
            tv_now_price.setText("￥" + listBean.getCover_price());
            tv_origin_price.setText("￥" + listBean.getOrigin_price());
            Picasso.with(context).load(ConstansUrl.BASE_URl_IMAGE + listBean.getFigure()).into(iv_seckill);
        }
    }
    //监听器
    public interface SeckillClick{
        void onItemClick(int position);
    }
    private  SeckillClick seckillClick;

    //设置item的监听
    public void setOnSeckillRecyclerView(SeckillClick seckillClick){
        this.seckillClick=seckillClick;
    }
}
