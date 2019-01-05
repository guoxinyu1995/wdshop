package com.example.wdshop.api;

public class Apis {
    /***用户注册*/
    public static final String POST_URL_USER_REGISTER="user/v1/register";
    /***用户登录*/
    public static final String POST_URL_USER_LOGIN="user/v1/login";
    /***用户修改昵称*/
    public static final String PUT_URL_USER_MODIFY_USER_NICK="user/verify/v1/modifyUserNick";
    /***用户修改用户密码*/
    public static final String PUT_URL_USER_MODIFY_USER_PWD="user/verify/v1/modifyUserPwd";
    /***用户上传头像*/
    public static final String POST_URL_USER_MODIFY_HEAD_PIC="user/verify/v1/modifyHeadPic";
    /***根据用户ID查询用户信息*/
    public static final String GET_URL_USER_GET_USER_BYID="user/verify/v1/getUserById";
    /***收货地址列表*/
    public static final String GET_URL_USER_RECYCLE_ADDRESS_LIST="user/verify/v1/receiveAddressList";
   /*** 新增收货地址*/
    public static final String POST_URL_USER_ADD_RECYCLE_ADDRESS_LIST="user/verify/v1/addReceiveAddress";
   /*** 设置默认收货地址*/
    public static final String POST_URL_USER_SET_DEFAULT_RECYCLE_ADDRESS_LIST="user/verify/v1/setDefaultReceiveAddress";
    /***修改收货信息*/
    public static final String PUT_URL_USER_CHANGE_RECYCLE_ADDRESS="user/verify/v1/changeReceiveAddress";
    /***查询用户钱包*/
    public static final String GET_URL_USER_FIND_USER_WALLET="user/verify/v1/changeReceiveAddress";
    /**
     * 圈子列表 http://172.17.8.100/small/circle/v1/findCircleList
     */
    public static final String URL_FIND_CIRCLE_LIST_GET="circle/v1/findCircleList?userId=%d&sessionId=%s&page=%d&count=%d";
    /**
     *圈子点赞
     * */
    public static final String URL_CIRCLE_ADD_POST ="circle/verify/v1/addCircleGreat";
    /**
     *圈子取消点赞
     * */
    public static final String URL_CIRCLE_CANCLE_DELETE ="circle/verify/v1/cancelCircleGreat?circleId=%d";
   /**
    * banner轮播图
    * */
   public static final String URL_BANNER_SHOE_GET = "commodity/v1/bannerShow";
   /**
    * 首页商品展示
    * */
   public static final String URL_SHOP_SHOW_GET="commodity/v1/commodityList";
   /**
    * 类目一级
    * */
   public static final String URL_CATATGRAL_ONE_GET="commodity/v1/findFirstCategory";
   /**
    * 类目二级
    * */
   public static final String URL_CATATGRAL_TWO_GET="commodity/v1/findSecondCategory?firstCategoryId=%s";
   /**
    * 二级条目查询
    * */
   public static final String URL_CATATGRAL_FIND_GET="commodity/v1/findCommodityByCategory?categoryId=%s&page=%d&count=%d";
   /**
    *根据关键词查询商品信息
    * */
   public static final String URL_SEARCH_GET = "commodity/v1/findCommodityByKeyword?keyword=%s&page=%d&count=%d";
   /**
    *根据商品列表归属标签查询商品信息
    * */
   public static final String URL_MORE_GET = "commodity/v1/findCommodityListByLabel?labelId=%s&page=%d&count=%d";
}
