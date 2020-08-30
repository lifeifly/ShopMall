package com.example.shopmall.shoppingcart.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.arch.core.internal.FastSafeIterableMap;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmall.R;
import com.example.shopmall.base.ConstansUrl;
import com.example.shopmall.bean.GoodsBean;
import com.example.shopmall.shoppingcart.utils.CartStorage;
import com.example.shopmall.shoppingcart.view.AddSubLayout;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerViewCart extends RecyclerView.Adapter<RecyclerViewCart.ViewHolder> {
    private TextView tvTotalPrice;
    private CheckBox checkAll;
    private Context context;
    private List<GoodsBean> goodsBeanList;
    private CheckBox cbAll;

    public RecyclerViewCart(Context context, List<GoodsBean> goodsBeanList, CheckBox checkBox, TextView textView, CheckBox cb_all) {
        this.checkAll = checkBox;
        this.tvTotalPrice = textView;
        this.context = context;
        this.goodsBeanList = goodsBeanList;
        this.cbAll=cb_all;
        showTotalPrice();
        setListener();
        //校验是否全选
        checkCheckedAllItem();
    }

    private void setListener() {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //1.根据位置找到对应的bean对象
                GoodsBean goodsBean = goodsBeanList.get(position);
                //2.设置取反状态
                goodsBean.setSelected(!goodsBean.isSelected());
                //3.刷新状态
                notifyItemChanged(position);
                //4.校验是否全选
                checkCheckedAllItem();
                //5.重新计算价格
                showTotalPrice();
            }
        });
        //设置全选和非全选的点击事件
        checkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.得到状态
                boolean isCheckded=checkAll.isChecked();
                //2.根据状态设置全选和非全选
                checkAllOrNone(isCheckded);

            }
        });
        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.得到状态
                boolean isCheckded=cbAll.isChecked();
                //2.根据状态设置全选和非全选
                checkAllOrNone(isCheckded);
                //3.计算总价格
                showTotalPrice();
            }
        });
    }

    public void checkAllOrNone(boolean isChecked) {
        if (goodsBeanList!=null&&goodsBeanList.size()>0){
            for (int i=0;i<goodsBeanList.size();i++){
                GoodsBean goodsBean=goodsBeanList.get(i);
                goodsBean.setSelected(isChecked);
                notifyItemChanged(i);
            }
        }
    }

    public void checkCheckedAllItem() {
        if (goodsBeanList.size() > 0 && goodsBeanList != null) {
            //选中的item条数
            int number=0;
            for (int i = 0; i < goodsBeanList.size(); i++) {
                GoodsBean goodsBean = goodsBeanList.get(i);
                if (goodsBean.isSelected()) {
                    number++;
                }else {
                    //非全选
                    checkAll.setChecked(false);
                    cbAll.setChecked(false);
                }
            }
            if (number==goodsBeanList.size()){
                //全选
                checkAll.setChecked(true);
                cbAll.setChecked(true);
            }
        }else {
            checkAll.setChecked(false);
            cbAll.setChecked(false);
        }
    }

    public void showTotalPrice() {
        tvTotalPrice.setText("合计：" + getTotalPrice());
    }

    public double getTotalPrice() {
        double totalPrice = 0.0;
        //计算总价格
        if (goodsBeanList.size() > 0 && goodsBeanList != null) {
            for (int i = 0; i < goodsBeanList.size(); i++) {
                GoodsBean goodsBean = goodsBeanList.get(i);
                if (goodsBean.isSelected()) {
                    totalPrice = totalPrice + Double.valueOf(goodsBean.getNumber()) * Double.valueOf(goodsBean.getCover_price());
                }
            }
        }
        return totalPrice;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_cart_rv_layout, null);
        ViewHolder viewHolder;
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //根据位置得到对应的数据
        final GoodsBean bean = goodsBeanList.get(position);
        //设置数据
        holder.cb_gov.setChecked(bean.isSelected());
        Picasso.with(context).load(ConstansUrl.BASE_URl_IMAGE + bean.getFigure()).into(holder.iv_gov);
        holder.tv_des_gov.setText(bean.getName());
        holder.tv_price_gov.setText("￥" + bean.getCover_price());
        holder.addSubLayout.setValue(bean.getNumber());
        //设置购物车物品最小值和最大值
        holder.addSubLayout.setMinValue(1);
        holder.addSubLayout.setMaxValue(8);
        //设置商品数量的变化
        holder.addSubLayout.setListener(new AddSubLayout.NumberChangeListener() {
            @Override
            public void onNumberChange(int value) {
                //1.内存和本地要更新
                bean.setNumber(value);
                CartStorage.getInstance().updateData(bean);
                //2.刷新适配器
                notifyItemChanged(position);
                //3.再次计算总价格
                showTotalPrice();

            }
        });
    }

    @Override
    public int getItemCount() {
        return goodsBeanList.size();
    }

    public void deleteData() {
        if (goodsBeanList.size()>0&&goodsBeanList!=null){
            for (int i=0;i<goodsBeanList.size();i++){
                GoodsBean goodsBean = goodsBeanList.get(i);
                if (goodsBean.isSelected()){
                    //从内存中移除
                    goodsBeanList.remove(i);
                    //保存到本地
                    CartStorage.getInstance().deleteData(goodsBean);
                    //刷新适配器
                    notifyItemRemoved(i);
                    i--;
                }
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cb_gov;
        private ImageView iv_gov;
        private TextView tv_des_gov;
        private TextView tv_price_gov;
        private AddSubLayout addSubLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cb_gov = (CheckBox) itemView.findViewById(R.id.cb_gov);
            iv_gov = (ImageView) itemView.findViewById(R.id.iv_gov);
            tv_des_gov = (TextView) itemView.findViewById(R.id.tv_desc_gov);
            tv_price_gov = (TextView) itemView.findViewById(R.id.tv_price_gov);
            addSubLayout = (AddSubLayout) itemView.findViewById(R.id.numberAddSubView);
            //设置item的点击事件
            cb_gov.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    //定义监听Item的监听
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
