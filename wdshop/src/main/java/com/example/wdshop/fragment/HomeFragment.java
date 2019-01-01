package com.example.wdshop.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wdshop.R;
import com.example.wdshop.adaper.CommodityAdaper;
import com.example.wdshop.adaper.FashionAdaper;
import com.example.wdshop.adaper.QualityAdaper;
import com.example.wdshop.api.Apis;
import com.example.wdshop.bean.BannerBean;
import com.example.wdshop.bean.HomeFragmentBean;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.view.Iview;
import com.stx.xhb.xbanner.XBanner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment implements Iview {
    @BindView(R.id.home_image_category)
    ImageView homeImageCategory;
    @BindView(R.id.home_editx_search)
    EditText homeEditxSearch;
    @BindView(R.id.home_text_search)
    TextView homeTextSearch;
    @BindView(R.id.linearlayout_search)
    LinearLayout linearlayoutSearch;
    @BindView(R.id.home_image_search)
    ImageView homeImageSearch;
    @BindView(R.id.home_xbanner)
    XBanner homeXbanner;
    @BindView(R.id.hot_commodity)
    ImageView hotCommodity;
    @BindView(R.id.text_hot_commodity)
    TextView textHotCommodity;
    @BindView(R.id.hot_image_btn)
    ImageButton hotImageBtn;
    @BindView(R.id.hot_recycleview)
    RecyclerView hotRecycleview;
    @BindView(R.id.image_fashion)
    ImageView imageFashion;
    @BindView(R.id.text_fashion)
    TextView textFashion;
    @BindView(R.id.fashion_image_btn)
    ImageButton fashionImageBtn;
    @BindView(R.id.fashion_recycleview)
    RecyclerView fashionRecycleview;
    @BindView(R.id.image_quality)
    ImageView imageQuality;
    @BindView(R.id.text_quality)
    TextView textQuality;
    @BindView(R.id.quality_image_btn)
    ImageButton qualityImageBtn;
    @BindView(R.id.quality_recycleview)
    RecyclerView qualityRecycleview;
    Unbinder unbinder;
    @BindView(R.id.category_one_recycle)
    RecyclerView categoryOneRecycle;
    @BindView(R.id.category_two_recycle)
    RecyclerView categoryTwoRecycle;
    private PresenterImpl presenter;
    private CommodityAdaper commodityAdaper;
    private FashionAdaper fashionAdaper;
    private QualityAdaper qualityAdaper;

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        /**
         * banner轮播图
         * */
        presenter.getRequest(Apis.URL_BANNER_SHOE_GET, BannerBean.class);
        /**
         * 热销新品
         * */
        presenter.getRequest(Apis.URL_SHOP_SHOW_GET, HomeFragmentBean.class);
    }

    /**
     * 初始化view
     */
    @Override
    protected void initView(View view) {
        presenter = new PresenterImpl(this);
        //热销
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        hotRecycleview.setLayoutManager(layoutManager);
        commodityAdaper = new CommodityAdaper(getActivity());
        hotRecycleview.setAdapter(commodityAdaper);
        //魔力
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(OrientationHelper.VERTICAL);
        fashionRecycleview.setLayoutManager(layoutManager1);
        fashionAdaper = new FashionAdaper(getActivity());
        fashionRecycleview.setAdapter(fashionAdaper);
        //品质
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        qualityRecycleview.setLayoutManager(gridLayoutManager);
        qualityAdaper = new QualityAdaper(getActivity());
        qualityRecycleview.setAdapter(qualityAdaper);
    }

    /**
     * 初始化视图
     */
    @Override
    protected int getLayoutResId() {
        return R.layout.home_fragment;
    }

    @Override
    public void requestData(Object o) {
        if (o instanceof BannerBean) {
            final BannerBean bannerBean = (BannerBean) o;
            if (bannerBean == null || !bannerBean.isSuccess()) {
                Toast.makeText(getActivity(), bannerBean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                homeXbanner.setData(bannerBean.getResult(), null);
                homeXbanner.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        BannerBean.ResultBean resultBean = (BannerBean.ResultBean) model;
                        Glide.with(getActivity()).load(resultBean.getImageUrl()).into((ImageView) view);
                    }
                });
                homeXbanner.startAutoPlay();
            }
        } else if (o instanceof HomeFragmentBean) {
            HomeFragmentBean homeFragmentBean = (HomeFragmentBean) o;
            if (homeFragmentBean == null || !homeFragmentBean.isSuccess()) {
                Toast.makeText(getActivity(), homeFragmentBean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                commodityAdaper.setmRxxp(homeFragmentBean.getResult().getRxxp().get(0).getCommodityList());
                fashionAdaper.setmMlss(homeFragmentBean.getResult().getMlss().get(0).getCommodityList());
                qualityAdaper.setmPzsh(homeFragmentBean.getResult().getPzsh().get(0).getCommodityList());
            }
        }
    }

    @Override
    public void requestFail(Object o) {
        if (o instanceof Exception) {
            Exception e = (Exception) o;
            e.printStackTrace();
        }
        Toast.makeText(getActivity(), "网络请求错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.home_text_search, R.id.linearlayout_search, R.id.home_image_search, R.id.hot_image_btn,
            R.id.fashion_image_btn, R.id.quality_image_btn, R.id.home_image_category})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //点击显示类目页
            case R.id.home_image_category:
                break;
            //点击搜索
            case R.id.home_text_search:
                break;
            case R.id.linearlayout_search:
                break;
            //点击放大镜搜索
            case R.id.home_image_search:
                break;
            case R.id.hot_image_btn:
                break;
            case R.id.fashion_image_btn:
                break;
            case R.id.quality_image_btn:
                break;
            default:
                break;
        }
    }


}
