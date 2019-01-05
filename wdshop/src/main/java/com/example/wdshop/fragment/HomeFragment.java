package com.example.wdshop.fragment;


import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wdshop.R;
import com.example.wdshop.adaper.CatagralFindAdaper;
import com.example.wdshop.adaper.CatagralOneAdaper;
import com.example.wdshop.adaper.CatagralTwoAdaper;
import com.example.wdshop.adaper.CommodityAdaper;
import com.example.wdshop.adaper.FashionAdaper;
import com.example.wdshop.adaper.MoreAdaper;
import com.example.wdshop.adaper.QualityAdaper;
import com.example.wdshop.adaper.SearchAdaper;
import com.example.wdshop.api.Apis;
import com.example.wdshop.bean.BannerBean;
import com.example.wdshop.bean.CatagralFindBean;
import com.example.wdshop.bean.CatagralTwoBean;
import com.example.wdshop.bean.CatatgralOneBean;
import com.example.wdshop.bean.HomeFragmentBean;
import com.example.wdshop.bean.MoreBean;
import com.example.wdshop.bean.SearchBean;
import com.example.wdshop.presents.PresenterImpl;
import com.example.wdshop.util.EditTextUtils;
import com.example.wdshop.view.Iview;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stx.xhb.xbanner.XBanner;

import java.util.concurrent.atomic.AtomicReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment implements Iview {
    @BindView(R.id.home_image_category)
    ImageView homeImageCategory;
    @BindView(R.id.home_editx_search)
    EditText homeEditxSearch;
    @BindView(R.id.home_image_search)
    ImageView homeImageSearch;
    @BindView(R.id.home_xbanner)
    XBanner homeXbanner;
    @BindView(R.id.hot_commodity)
    ImageView hotCommodity;
    @BindView(R.id.text_hot_commodity)
    TextView textHotCommodity;
    @BindView(R.id.hot_recycleview)
    RecyclerView hotRecycleview;
    @BindView(R.id.image_fashion)
    ImageView imageFashion;
    @BindView(R.id.text_fashion)
    TextView textFashion;
    @BindView(R.id.fashion_recycleview)
    RecyclerView fashionRecycleview;
    @BindView(R.id.image_quality)
    ImageView imageQuality;
    @BindView(R.id.text_quality)
    TextView textQuality;
    @BindView(R.id.quality_recycleview)
    RecyclerView qualityRecycleview;
    @BindView(R.id.category_one_recycle)
    RecyclerView categoryOneRecycle;
    @BindView(R.id.category_two_recycle)
    RecyclerView categoryTwoRecycle;
    @BindView(R.id.find_recycle)
    RecyclerView findRecycle;
    @BindView(R.id.scroll)
    ScrollView scrollView;
    @BindView(R.id.hot_image_btn)
    ImageButton hotImageBtn;
    @BindView(R.id.fashion_image_btn)
    ImageButton fashionImageBtn;
    @BindView(R.id.quality_image_btn)
    ImageButton qualityImageBtn;
    @BindView(R.id.image_no)
    ImageView imageView;
    @BindView(R.id.text_no)
    TextView textView;
    @BindView(R.id.xrecycle)
    XRecyclerView xRecyclerView;
    @BindView(R.id.more_image)
    ImageView moreImage;
    @BindView(R.id.more_text)
    TextView moreText;
    Unbinder unbinder;
    private PresenterImpl presenter;
    private CommodityAdaper commodityAdaper;
    private FashionAdaper fashionAdaper;
    private QualityAdaper qualityAdaper;
    private CatagralOneAdaper oneAdaper;
    private CatagralTwoAdaper twoAdaper;
    private int mPage;
    private int mCount = 5;
    private CatagralFindAdaper findAdaper;
    private SearchAdaper searchAdaper;
    private String reId;
    private String mlId;
    private String pzId;
    private MoreAdaper moreAdaper;

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
        unbinder = ButterKnife.bind(this, view);
        //热销
        getHot();
        //魔力
        getFashion();
        //品质
        getQuality();
        //初始化输入框失去焦点
        EditTextUtils.losePoint(getActivity(),homeEditxSearch);

    }

    //品质生活
    private void getQuality() {
        //创建布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        qualityRecycleview.setLayoutManager(gridLayoutManager);
        //创建适配器
        qualityAdaper = new QualityAdaper(getActivity());
        qualityRecycleview.setAdapter(qualityAdaper);
    }

    //魔力时尚
    private void getFashion() {
        //创建布局管理器
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(OrientationHelper.VERTICAL);
        fashionRecycleview.setLayoutManager(layoutManager1);
        //创建适配器
        fashionAdaper = new FashionAdaper(getActivity());
        fashionRecycleview.setAdapter(fashionAdaper);
    }

    //热销新品
    private void getHot() {
        //创建布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        hotRecycleview.setLayoutManager(layoutManager);
        //创建适配器
        commodityAdaper = new CommodityAdaper(getActivity());
        hotRecycleview.setAdapter(commodityAdaper);
    }

    /**
     * 初始化视图
     */
    @Override
    protected int getLayoutResId() {
        return R.layout.home_fragment;
    }

    /**
     * 请求成功
     */
    @Override
    public void requestData(Object o) {
        //轮播图
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
            }
            //首页数据
        } else if (o instanceof HomeFragmentBean) {
            HomeFragmentBean homeFragmentBean = (HomeFragmentBean) o;
            reId = String.valueOf(homeFragmentBean.getResult().getRxxp().get(0).getId());
            mlId = String.valueOf(homeFragmentBean.getResult().getMlss().get(0).getId());
            pzId = String.valueOf(homeFragmentBean.getResult().getPzsh().get(0).getId());
            if (homeFragmentBean == null || !homeFragmentBean.isSuccess()) {
                Toast.makeText(getActivity(), homeFragmentBean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                //热销新品
                commodityAdaper.setmRxxp(homeFragmentBean.getResult().getRxxp().get(0).getCommodityList());
                //魔力时尚
                fashionAdaper.setmMlss(homeFragmentBean.getResult().getMlss().get(0).getCommodityList());
                //品质生活
                qualityAdaper.setmPzsh(homeFragmentBean.getResult().getPzsh().get(0).getCommodityList());
            }
            //一级类目
        } else if (o instanceof CatatgralOneBean) {
            CatatgralOneBean oneBean = (CatatgralOneBean) o;
            if (oneBean == null || !oneBean.isSuccess()) {
                Toast.makeText(getActivity(), oneBean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                oneAdaper.setmResult(oneBean.getResult());
            }
            //二级类目
        } else if (o instanceof CatagralTwoBean) {
            CatagralTwoBean twoBean = (CatagralTwoBean) o;
            if (twoBean == null || !twoBean.isSuccess()) {
                Toast.makeText(getActivity(), twoBean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                twoAdaper.setmResult(twoBean.getResult());
            }
            //根据二级类目查询商品信息
        } else if (o instanceof CatagralFindBean) {
            CatagralFindBean findBean = (CatagralFindBean) o;
            if (findBean == null || !findBean.isSuccess()) {
                Toast.makeText(getActivity(), findBean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                findAdaper.setmResult(findBean.getResult());
            }
            //根据关键词查询商品信息
        } else if (o instanceof SearchBean) {
            SearchBean searchBean = (SearchBean) o;
            if (searchBean == null || !searchBean.isSuccess()) {
                Toast.makeText(getActivity(), searchBean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                //当集合的长度==0时，显示没有商品
                if (searchBean.getResult().size() == 0) {
                    imageView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    searchAdaper.setmResult(searchBean.getResult());
                }
            }
            //根据商品列表归属标签查询商品信息
        }else if(o instanceof MoreBean){
            MoreBean moreBean = (MoreBean) o;
            if(moreBean == null || !moreBean.isSuccess()){
                Toast.makeText(getActivity(),moreBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else{
                if(mPage==1){
                    moreAdaper.setmResult(moreBean.getResult());
                }else{
                    moreAdaper.addmResult(moreBean.getResult());
                }
                mPage++;
                xRecyclerView.loadMoreComplete();
                xRecyclerView.refreshComplete();
            }
        }
    }

    /**
     * 请求失败
     */
    @Override
    public void requestFail(Object o) {
        if (o instanceof Exception) {
            Exception e = (Exception) o;
            e.printStackTrace();
        }
        Toast.makeText(getActivity(), "网络请求错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDetach();
    }

    /**
     * 点击事件
     */
    private boolean flag = true;

    @OnClick({R.id.home_image_search, R.id.home_image_category, R.id.hot_image_btn,
            R.id.fashion_image_btn, R.id.quality_image_btn,R.id.home_editx_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //点击显示类目页
            case R.id.home_image_category:
                if (flag) {
                    categoryOneRecycle.setVisibility(View.VISIBLE);
                    categoryTwoRecycle.setVisibility(View.VISIBLE);
                    getCatagralOne();
                    getCatagralTwo();
                    getCatagralFind();
                } else {
                    categoryOneRecycle.setVisibility(View.GONE);
                    categoryTwoRecycle.setVisibility(View.GONE);
                }
                flag = !flag;
                break;
            //点击放大镜搜索
            case R.id.home_image_search:
                mPage = 1;
                String shop = homeEditxSearch.getText().toString().trim();
                presenter.getRequest(String.format(Apis.URL_SEARCH_GET, shop, mPage, mCount), SearchBean.class);
                getSearch();
                homeEditxSearch.setText("" +
                        "");
                break;
            //热销商品加载更多
            case R.id.hot_image_btn:
                moreText.setText("热销商品");
                moreText.setTextColor(Color.parseColor("#ff7f57"));
                moreImage.setBackgroundResource(R.mipmap.bg_rxxp_syf);
                categoryOneRecycle.setVisibility(View.GONE);
                categoryTwoRecycle.setVisibility(View.GONE);
                getMoreId(reId);
                getMoreRecycle(reId);
                break;
            //魔力时尚加载更多
            case R.id.fashion_image_btn:
                moreText.setText("魔力时尚");
                moreText.setTextColor(Color.parseColor("#6699ff"));
                moreImage.setBackgroundResource(R.mipmap.bg_mlss_syf);
                categoryOneRecycle.setVisibility(View.GONE);
                categoryTwoRecycle.setVisibility(View.GONE);
                getMoreId(mlId);
                getMoreRecycle(mlId);
                break;
            //品质生活加载更多
            case R.id.quality_image_btn:
                moreText.setText("品质生活");
                moreText.setTextColor(Color.parseColor("#ff6600"));
                moreImage.setBackgroundResource(R.mipmap.bg_pzsh_syf);
                categoryOneRecycle.setVisibility(View.GONE);
                categoryTwoRecycle.setVisibility(View.GONE);
                getMoreId(pzId);
                getMoreRecycle(pzId);
                break;
            case R.id.home_editx_search:
                //点击输入框获得焦点
                EditTextUtils.searchPoint(getActivity(),homeEditxSearch);
                break;
            default:
                break;
        }
    }

    /**
     * 加载更多的布局
     */
    private void getMoreRecycle(final String labelId) {
        mPage = 1;
        //创建布局管理器
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        //创建适配器
        moreAdaper = new MoreAdaper(getActivity());
        xRecyclerView.setAdapter(moreAdaper);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                getMoreId(labelId);
            }

            @Override
            public void onLoadMore() {
                getMoreId(labelId);
            }
        });
        getMoreId(labelId);
        scrollView.setVisibility(View.GONE);
        xRecyclerView.setVisibility(View.VISIBLE);
        moreText.setVisibility(View.VISIBLE);
        moreImage.setVisibility(View.VISIBLE);
    }

    /**
     * 加载更多请求数据的方法
     */
    private void getMoreId(String labelId) {
        presenter.getRequest(String.format(Apis.URL_MORE_GET, labelId,mPage,mCount), MoreBean.class);
    }

    /**
     * 根据关键词查询商品信息
     */
    private void getSearch() {
        //创建布局管理器
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        findRecycle.setLayoutManager(layoutManager);
        //创建适配器
        searchAdaper = new SearchAdaper(getActivity());
        findRecycle.setAdapter(searchAdaper);
        //scrollView隐藏
        scrollView.setVisibility(View.GONE);
        xRecyclerView.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        moreText.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        moreImage.setVisibility(View.GONE);
        //展示数据显示
        findRecycle.setVisibility(View.VISIBLE);
    }

    /**
     * 根据二级类目查询商品信息
     */
    private void getCatagralFind() {
        //创建布局管理器
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        findRecycle.setLayoutManager(layoutManager);
        //创建适配器
        findAdaper = new CatagralFindAdaper(getActivity());
        //设置适配器
        findRecycle.setAdapter(findAdaper);
    }

    /**
     * 类目二级数据
     */
    private void getCatagralTwo() {
        mPage = 1;
        //创建布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        categoryTwoRecycle.setLayoutManager(layoutManager);
        //创建适配器
        twoAdaper = new CatagralTwoAdaper(getActivity());
        //设置适配器
        categoryTwoRecycle.setAdapter(twoAdaper);
        twoAdaper.setCatagralTwoCallBack(new CatagralTwoAdaper.CatagralTwoCallBack() {
            @Override
            public void callBack(String id) {
                categoryOneRecycle.setVisibility(View.GONE);
                categoryTwoRecycle.setVisibility(View.GONE);
                scrollView.setVisibility(View.GONE);
                findRecycle.setVisibility(View.VISIBLE);
                xRecyclerView.setVisibility(View.GONE);
                moreText.setVisibility(View.GONE);
                moreImage.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                presenter.getRequest(String.format(Apis.URL_CATATGRAL_FIND_GET, id, mPage, mCount), CatagralFindBean.class);
            }
        });
    }

    /**
     * 类目一级数据
     */
    private void getCatagralOne() {
        //创建布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        categoryOneRecycle.setLayoutManager(layoutManager);
        //创建适配器
        oneAdaper = new CatagralOneAdaper(getActivity());
        //设置适配器
        categoryOneRecycle.setAdapter(oneAdaper);
        presenter.getRequest(Apis.URL_CATATGRAL_ONE_GET, CatatgralOneBean.class);
        presenter.getRequest(String.format(Apis.URL_CATATGRAL_TWO_GET, 1001002), CatagralTwoBean.class);
        oneAdaper.setCatagralCallBack(new CatagralOneAdaper.CatagralCallBack() {
            @Override
            public void callBack(String id) {
                presenter.getRequest(String.format(Apis.URL_CATATGRAL_TWO_GET, id), CatagralTwoBean.class);
            }
        });
    }

    //监听返回键
    public void getBackData(boolean back) {
        if (back) {
            xRecyclerView.setVisibility(View.GONE);
            moreText.setVisibility(View.GONE);
            moreImage.setVisibility(View.GONE);
            categoryOneRecycle.setVisibility(View.GONE);
            categoryTwoRecycle.setVisibility(View.GONE);
            findRecycle.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
