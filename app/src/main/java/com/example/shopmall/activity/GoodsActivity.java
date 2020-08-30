package com.example.shopmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.shopmall.R;
import com.example.shopmall.base.ConstansUrl;
import com.example.shopmall.bean.GoodsBean;
import com.example.shopmall.home.adapter.HomeRecyclerViewAdapter;
import com.example.shopmall.shoppingcart.utils.CartStorage;
import com.squareup.picasso.Picasso;


public class GoodsActivity extends Activity implements View.OnClickListener {
    private ImageButton ibGoodInfoBack;
    private ImageButton ibGoodInfoMore;
    private ImageView ivGoodInfoImage;
    private TextView tvGoodInfoName;
    private TextView tvGoodInfoDesc;
    private TextView tvGoodInfoPrice;
    private TextView tvGoodInfoStore;
    private TextView tvGoodInfoStyle;
    private WebView wbGoodInfoMore;
    private LinearLayout llGoodsRoot;
    private TextView tvGoodInfoCallcenter;
    private TextView tvGoodInfoCollection;
    private TextView tvGoodInfoCart;
    private Button btnGoodInfoAddcart;
    private TextView tv_more_share, tv_more_search, tv_more_home;
    private GoodsBean goodsBean;


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2020-08-26 23:45:14 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        ibGoodInfoBack = (ImageButton) findViewById(R.id.ib_good_info_back);
        ibGoodInfoMore = (ImageButton) findViewById(R.id.ib_good_info_more);
        ivGoodInfoImage = (ImageView) findViewById(R.id.iv_good_info_image);
        tvGoodInfoName = (TextView) findViewById(R.id.tv_good_info_name);
        tvGoodInfoDesc = (TextView) findViewById(R.id.tv_good_info_desc);
        tvGoodInfoPrice = (TextView) findViewById(R.id.tv_good_info_price);
        tvGoodInfoStore = (TextView) findViewById(R.id.tv_good_info_store);
        tvGoodInfoStyle = (TextView) findViewById(R.id.tv_good_info_style);
        wbGoodInfoMore = (WebView) findViewById(R.id.wb_good_info_more);
        llGoodsRoot = (LinearLayout) findViewById(R.id.ll_goods_root);
        tvGoodInfoCallcenter = (TextView) findViewById(R.id.tv_good_info_callcenter);
        tvGoodInfoCollection = (TextView) findViewById(R.id.tv_good_info_collection);
        tvGoodInfoCart = (TextView) findViewById(R.id.tv_good_info_cart);
        btnGoodInfoAddcart = (Button) findViewById(R.id.btn_good_info_addcart);
        tv_more_share = (TextView) findViewById(R.id.tv_more_share);
        tv_more_search = (TextView) findViewById(R.id.tv_more_search);
        tv_more_home = (TextView) findViewById(R.id.tv_more_home);

        ibGoodInfoBack.setOnClickListener(this);
        ibGoodInfoMore.setOnClickListener(this);
        btnGoodInfoAddcart.setOnClickListener(this);

        tvGoodInfoCallcenter.setOnClickListener(this);
        tvGoodInfoCollection.setOnClickListener(this);
        tvGoodInfoCart.setOnClickListener(this);

        tv_more_share.setOnClickListener(this);
        tv_more_search.setOnClickListener(this);
        tv_more_home.setOnClickListener(this);


    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2020-08-26 23:45:14 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == ibGoodInfoBack) {
            // Handle clicks for ibGoodInfoBack
            finish();
        } else if (v == ibGoodInfoMore) {
            // Handle clicks for ibGoodInfoMore
        } else if (v == btnGoodInfoAddcart) {
            CartStorage.getInstance().addData(goodsBean);
            Toast.makeText(GoodsActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();
            // Handle clicks for btnGoodInfoAddcart
        } else if (v == tvGoodInfoCallcenter) {

        } else if (v == tvGoodInfoCollection) {

        } else if (v == tvGoodInfoCart) {

        } else if (v == tv_more_share) {

        } else if (v == tv_more_search) {

        } else if (v == tv_more_home) {

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        findViews();
        Intent intent=getIntent();
        goodsBean = (GoodsBean) intent.getSerializableExtra(HomeRecyclerViewAdapter.GOODS_BEAN);
        setDataForView(goodsBean);
    }

    private void setDataForView(GoodsBean goodsBean) {
        //设置图片
        Picasso.with(this).load(ConstansUrl.BASE_URl_IMAGE+goodsBean.getFigure()).into(ivGoodInfoImage);
        //设置文本
        tvGoodInfoName.setText(goodsBean.getName());
        //设置价格
        tvGoodInfoPrice.setText("￥"+goodsBean.getCover_price());
        //根据产品号加载不同网页
        setWebViewData(goodsBean.getProduct_id());
    }

    private void setWebViewData(String product_id) {
        if (product_id!=null){
            wbGoodInfoMore.loadUrl("http://www.atguigu.com");
            //设置支持javaScript
            WebSettings webSettings=wbGoodInfoMore.getSettings();
            webSettings.setUseWideViewPort(true);//支持双击变大变小
            webSettings.setJavaScriptEnabled(true);//支持javascript
            //优先使用缓存
            wbGoodInfoMore.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            wbGoodInfoMore.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    //返回值为true，直接在webview中打开，返回值为false，用系统浏览器和第三方浏览器打开
                    return true;
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(request.getUrl().toString());
                    return true;
                }
            });
        }
    }
}
