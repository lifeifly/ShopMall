package com.example.shopmall.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.shopmall.R;
import com.example.shopmall.activity.GoodsActivity;
import com.example.shopmall.base.ConstansUrl;
import com.example.shopmall.bean.GoodsBean;
import com.example.shopmall.bean.HomeResultBean;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnLoadImageListener;
import com.youth.banner.transformer.RotateDownTransformer;
import com.youth.banner.transformer.RotateUpTransformer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.LogRecord;

import okhttp3.internal.http2.Http2Reader;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private HomeResultBean.ResultBean resultBean;
    private LayoutInflater inflater;
    //广告类型
    public static final int BANNER = 0;
    //通道类型
    public static final int CHANNEL = 1;
    //活动类型
    public static final int ACT = 2;
    //秒杀类型
    public static final int SECKILL = 3;
    //推荐类型
    public static final int RECOMMEND = 4;
    //热卖类型
    public static final int HOT = 5;
    //当前选中的类型
    public int currentTypr = 0;
    public static final String GOODS_BEAN="goodsbean";

    public HomeRecyclerViewAdapter(Context context, HomeResultBean.ResultBean resultBean) {
        this.context = context;
        this.resultBean = resultBean;
        this.inflater = LayoutInflater.from(context);
    }

    //相当于ListView中创建ViewHolder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(inflater.inflate(R.layout.banner_viewpager, parent, false), context, resultBean);
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(inflater.inflate(R.layout.channel_layout, parent, false), context);
        } else if (viewType == ACT) {
            return new ActViewHolder(inflater.inflate(R.layout.act_layout, parent, false), context);
        } else if (viewType == SECKILL) {
            return new SeckillViewHolder(inflater.inflate(R.layout.seckill_layout, parent, false), context);
        } else if (viewType == RECOMMEND) {
            return new RecommendViewHolder(inflater.inflate(R.layout.recommend_layout, parent, false), context);
        } else if (viewType == HOT) {
            return new HotViewHolder(inflater.inflate(R.layout.hot_layout, parent, false), context);
        }
        return null;
    }

    //绑定数据
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(resultBean.getBanner_info());
        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(resultBean.getChannel_info());
        } else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(resultBean.getAct_info());
        } else if (getItemViewType(position) == SECKILL) {
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(resultBean.getSeckill_info());
        } else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(resultBean);
        } else if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(resultBean.getHot_info());
        }
    }

    //得到条目的数据类型
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentTypr = BANNER;
                break;
            case CHANNEL:
                currentTypr = CHANNEL;
                break;
            case ACT:
                currentTypr = ACT;
                break;
            case SECKILL:
                currentTypr = SECKILL;
                break;
            case RECOMMEND:
                currentTypr = RECOMMEND;
                break;
            case HOT:
                currentTypr = HOT;
                break;
        }
        return currentTypr;
    }

    //总共有多项个条目
    @Override
    public int getItemCount() {
        return 6;
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        private Banner banner;
        private Context context;
        private HomeResultBean.ResultBean resultBean;

        public BannerViewHolder(@NonNull View itemView, Context context, HomeResultBean.ResultBean resultBean) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.banner);
            this.context = context;
            this.resultBean = resultBean;
        }

        public void setData(final List<HomeResultBean.ResultBean.BannerInfoBean> bannerInfoList) {
            //设置指示圆点
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            List<String> imageUris = new ArrayList<>();
            for (int i = 0; i < bannerInfoList.size(); i++) {
                String imageUri = resultBean.getBanner_info().get(i).getImage();
                imageUris.add(imageUri);
            }
            //设置手风琴动画效果
            banner.setBannerAnimation(Transformer.Accordion);
            banner.setImages(imageUris, new OnLoadImageListener() {
                @Override
                public void OnLoadImage(ImageView view, Object url) {
                    //加载图片并加入到ImageView
                    Picasso.with(context).load(ConstansUrl.BASE_URl_IMAGE + url).into(view);
                }
            });
            //设置banner的点击事件
            banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                    //这里的position从1开始，所有使用时需要减一
                    startGoodsActivity();
                }
            });
        }
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {
        private GridView gvChannel;
        private Context context;

        public ChannelViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            gvChannel = (GridView) itemView.findViewById(R.id.gv_channel);
        }

        public void setData(final List<HomeResultBean.ResultBean.ChannelInfoBean> channelInfoBeanList) {
            gvChannel.setAdapter(new ChannelGridViewAdapter(channelInfoBeanList, context));
            //设置点击事件
            gvChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
        }
    }

    class ActViewHolder extends RecyclerView.ViewHolder {
        private ViewPager actViewPager;
        private Context context;

        public ActViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            actViewPager = (ViewPager) itemView.findViewById(R.id.act_viewpager);
        }

        public void setData(final List<HomeResultBean.ResultBean.ActInfoBean> act_info) {
            //设置页面边距
            actViewPager.setPageMargin(20);
            actViewPager.setOffscreenPageLimit(3);
            //设置动画效果
            actViewPager.setPageTransformer(true, new RotateUpTransformer());
            actViewPager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return act_info.size();
                }

                /**
                 *
                 * @param view
                 * @param object 从instantiateItem方法返回的值
                 * @return
                 */
                @Override
                public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                    return view == object;
                }

                @NonNull
                @Override
                public Object instantiateItem(@NonNull View container, int position) {
                    ImageView view = new ImageView(context);
                    //设置沿XY轴拉伸
                    view.setScaleType(ImageView.ScaleType.FIT_XY);
                    //绑定数据
                    Picasso.with(context).load(ConstansUrl.BASE_URl_IMAGE + act_info.get(position).getIcon_url()).into(view);
                    ((ViewGroup) container).addView(view);
                    return view;
                }

                @Override
                public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                    container.removeView((View) object);
                }
            });
            //点击事件
            actViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    //相差多少时间，也是倒计时需要多少秒
    private long dt = 0;
    private TextView tv_time;
    //启动倒计时
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            dt = dt - 1000;
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            String time = format.format(new Date(dt));
            tv_time.setText(time);


            removeMessages(0);
            sendEmptyMessageDelayed(0, 1000);
            //dt等于0停止发送消息
            if (dt <= 0) {
                //把消息移除
                removeCallbacksAndMessages(null);
            }
        }
    };

    class SeckillViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rvSeckill;
        private Context context;
        private TextView tv_more;

        public SeckillViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            tv_more = (TextView) itemView.findViewById(R.id.tv_more_seckill);
            rvSeckill = (RecyclerView) itemView.findViewById(R.id.seckill_rv);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time_seckill);
        }

        public void setData(final HomeResultBean.ResultBean.SeckillInfoBean data) {
            //设置时间
            SeckillRecyclerViewAdapter adapter = new SeckillRecyclerViewAdapter(context, data.getList());
            rvSeckill.setAdapter(adapter);
            //为rvSeckill设置布局管理器
            rvSeckill.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            //设置item的点击事件
            adapter.setOnSeckillRecyclerView(new SeckillRecyclerViewAdapter.SeckillClick() {
                @Override
                public void onItemClick(int position) {
                    HomeResultBean.ResultBean.SeckillInfoBean.ListBean listBean=data.getList().get(position);
                    String name = listBean.getName();
                    String cover_price = listBean.getCover_price();
                    String figure = listBean.getFigure();
                    String product_id = listBean.getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(cover_price,figure,name,product_id);
//
                    Intent intent = new Intent(context, GoodsActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    context.startActivity(intent);
                }
            });

            //秒杀倒计时--毫秒
            dt = Integer.parseInt(data.getEnd_time()) - Integer.parseInt(data.getStart_time());
            handler.sendEmptyMessageDelayed(0, 1000);
        }
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_more_recommend;
        private GridView gv_recommend;
        private Context context;

        public RecommendViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            tv_more_recommend = (TextView) itemView.findViewById(R.id.tv_more_recommend);
            gv_recommend = (GridView) itemView.findViewById(R.id.gv_recommend);
        }

        public void setData(final HomeResultBean.ResultBean resultBean) {
            RecommendGridViewAdapter adapter = new RecommendGridViewAdapter(resultBean, context);
            gv_recommend.setAdapter(adapter);
            //设置布局管理器

            //设置监听器
            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HomeResultBean.ResultBean.RecommendInfoBean recommendInfoBean=resultBean.getRecommend_info().get(position);
                    String cover_price = recommendInfoBean.getCover_price();
                    String name = recommendInfoBean.getName();
                    String figure = recommendInfoBean.getFigure();
                    String product_id = recommendInfoBean.getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(cover_price,figure,name,product_id);

                    Intent intent = new Intent(context, GoodsActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    context.startActivity(intent);
                }
            });
        }
    }

    class HotViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private GridView gv_hot;
        private TextView tv_hot;

        public HotViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            tv_hot = (TextView) itemView.findViewById(R.id.tv_more_hot);
            gv_hot = (GridView) itemView.findViewById(R.id.hot_gv);
        }

        public void setData(final List<HomeResultBean.ResultBean.HotInfoBean> hotInfoBeanList) {
            HotGridViewAdapter adapter = new HotGridViewAdapter(context, hotInfoBeanList);
            gv_hot.setAdapter(adapter);

            //设置监听
            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HomeResultBean.ResultBean.HotInfoBean hotInfoBean=hotInfoBeanList.get(position);
                    String cover_price=hotInfoBean.getCover_price();
                    String name=hotInfoBean.getName();
                    String product_id=hotInfoBean.getProduct_id();
                    String figure=hotInfoBean.getFigure();
                    GoodsBean goodsBean=new GoodsBean(cover_price,figure,name,product_id);
                    Intent intent=new Intent(context,GoodsActivity.class);
                    intent.putExtra(GOODS_BEAN,goodsBean);
                    context.startActivity(intent);
                }
            });
        }
    }

    //跳转商品详情页面的方法
    public void startGoodsActivity() {
        Intent intent = new Intent(context, GoodsActivity.class);
        context.startActivity(intent);
    }
}
