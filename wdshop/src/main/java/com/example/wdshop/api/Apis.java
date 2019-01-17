package com.example.wdshop.api;

public class Apis {
    /***用户注册*/
    public static final String POST_URL_USER_REGISTER = "user/v1/register";
    /***用户登录*/
    public static final String POST_URL_USER_LOGIN = "user/v1/login";
    /***查询用户钱包*/
    public static final String GET_URL_USER_FIND_USER_WALLET = "user/verify/v1/changeReceiveAddress";
    /**
     * 圈子列表 http://172.17.8.100/small/circle/v1/findCircleList
     */
    public static final String URL_FIND_CIRCLE_LIST_GET = "circle/v1/findCircleList?page=%d&count=%d";
    /**
     * 圈子点赞
     */
    public static final String URL_CIRCLE_ADD_POST = "circle/verify/v1/addCircleGreat";
    /**
     * 圈子取消点赞
     */
    public static final String URL_CIRCLE_CANCLE_DELETE = "circle/verify/v1/cancelCircleGreat?circleId=%d";
    /**
     * banner轮播图
     */
    public static final String URL_BANNER_SHOE_GET = "commodity/v1/bannerShow";
    /**
     * 首页商品展示
     */
    public static final String URL_SHOP_SHOW_GET = "commodity/v1/commodityList";
    /**
     * 类目一级
     */
    public static final String URL_CATATGRAL_ONE_GET = "commodity/v1/findFirstCategory";
    /**
     * 类目二级
     */
    public static final String URL_CATATGRAL_TWO_GET = "commodity/v1/findSecondCategory?firstCategoryId=%s";
    /**
     * 二级条目查询
     */
    public static final String URL_CATATGRAL_FIND_GET = "commodity/v1/findCommodityByCategory?categoryId=%s&page=%d&count=%d";
    /**
     * 根据关键词查询商品信息
     */
    public static final String URL_SEARCH_GET = "commodity/v1/findCommodityByKeyword?keyword=%s&page=%d&count=%d";
    /**
     * 根据商品列表归属标签查询商品信息
     */
    public static final String URL_MORE_GET = "commodity/v1/findCommodityListByLabel?labelId=%s&page=%d&count=%d";
    /**
     * 根据用户ID查询用户信息
     */
    public static final String URL_VIEW_USER_INFORMATION = "user/verify/v1/getUserById";
    /**
     * 修改昵称
     */
    public static final String URL_UPDATE_NAME_PUT = "user/verify/v1/modifyUserNick";
    /**
     * 修改用户密码
     */
    public static final String URL_UPDATE_PWD_PUT = "user/verify/v1/modifyUserPwd";
    /**
     * 我的足迹
     * 接口地址：http://172.17.8.100/small/commodity/verify/v1/browseList
     */
    public static final String URL_BROWSE_LIST_GET = "commodity/verify/v1/browseList?page=%d&count=%d";
    /**
     * 用户上传头像
     * 接口地址：http://172.17.8.100/small/user/verify/v1/modifyHeadPic
     */
    public static final String URL_MODIFY_HEANPIC_POST = "user/verify/v1/modifyHeadPic";
    /**
     * 商品详情
     * 接口地址：http://172.17.8.100/small/commodity/v1/findCommodityDetailsById
     */
    public static final String URL_FIND_COMMODITY_GET = "commodity/v1/findCommodityDetailsById?commodityId=%d";
    /**
     * 同步购物车数据
     * 接口地址：http://172.17.8.100/small/order/verify/v1/syncShoppingCart
     *请求方式:PUT
     * */
    public static final String URL_SHOPPING_CART_PUT="order/verify/v1/syncShoppingCart";
    /**
     * 查询购物车
     * 接口地址：http://172.17.8.100/small/order/verify/v1/findShoppingCart
     * 请求方式:GET
     * */
    public static final String URL_FIND_CART_GET="order/verify/v1/findShoppingCart";
    /**
     * 查询用户钱包
     * 接口地址：http://172.17.8.100/small/user/verify/v1/findUserWallet
     * 请求方式:GET
     * */
    public static final String URL_FIND_WALLET_GET="user/verify/v1/findUserWallet?page=%d&count=%d";
    /**
     * 收货地址列表
     * 接口地址：http://172.17.8.100/small/user/verify/v1/receiveAddressList
     * 请求方式:GET
     * */
    public static final String URL_RECEIVE_ADDRESS_LIST_GET="user/verify/v1/receiveAddressList";
    /**
     * 新增收货地址
     * 接口地址：http://172.17.8.100/small/user/verify/v1/addReceiveAddress
     * 请求方式:POST
     * */
    public static final String URL_ADD_RECEIVER_ADDRESS_POST="user/verify/v1/addReceiveAddress";
    /**
     * 修改收货信息
     * 接口地址：http://172.17.8.100/small/user/verify/v1/changeReceiveAddress
     * 请求方式:PUT
     * */
    public static final String URL_CHANGE_ADDRESS_PUT = "user/verify/v1/changeReceiveAddress";
    /**
     *设置默认收货地址
     * 接口地址：http://172.17.8.100/small/user/verify/v1/setDefaultReceiveAddress
     * 请求方式:POST
     * */
    public static final String URL_DEFAULT_RECEIVE_ADDRESS = "user/verify/v1/setDefaultReceiveAddress";
    /**
     * 创建订单
     * 接口地址：http://172.17.8.100/small/order/verify/v1/createOrder
     * 请求方式:POST
     * */
    public static final String URL_CREATE_ORDER = "order/verify/v1/createOrder";
    /**
     * 根据订单状态查询订单信息
     * 接口地址：http://172.17.8.100/small/order/verify/v1/findOrderListByStatus
     * 请求方式:GET
     * */
    public static final String URL_FIND_ORDER_LIST_BY_STATUS_GET = "order/verify/v1/findOrderListByStatus?status=%d&page=%d&count=%d";
    /**
     *   删除订单
     * 接口地址：http://172.17.8.100/small/order/verify/v1/deleteOrder
     * 请求方式:DELETE
     * */
    public static final String URL_DELETE_ORDER_DELETE = "order/verify/v1/deleteOrder?orderId=%s";
    /**
     * 支付
     * 接口地址：http://172.17.8.100/small/order/verify/v1/pay
     * 请求方式:POST
     * */
    public static final String URL_PAY_POST = "order/verify/v1/pay";
    /**
     *  收货
     * 接口地址：http://172.17.8.100/small/order/verify/v1/confirmReceipt
     * 请求方式:PUT
     * */
    public static final String URL_CONFIRM_RECEIPT_PUT = "order/verify/v1/confirmReceipt";
    /**
     *商品评论
     * 接口地址：http://172.17.8.100/small/commodity/verify/v1/addCommodityComment
     * 请求方式:POST
     * */
    public static final String URL_ADD_COMMODITY_COMMENT_POST = "commodity/verify/v1/addCommodityComment";
    /**
     * 用户上传头像
     * 接口地址：http://172.17.8.100/small/user/verify/v1/modifyHeadPic
     * 请求方式:POST
     * */
    public static final String URL_MODEIFY_HEAD_PIC = "user/verify/v1/modifyHeadPic";
    /**
     *  我的圈子
     * 接口地址：http://172.17.8.100/small/circle/verify/v1/findMyCircleById
     * 请求方式:GET
     * */
    public static final String URL_FIND_CIRCLE_BY_ID_GET = "circle/verify/v1/findMyCircleById?page=%d&count=%d";
    /**
     * 删除我发表过的圈子
     * 接口地址：http://172.17.8.100/small/circle/verify/v1/deleteCircle
     * 请求方式:DELETE
     * */
    public static final String URL_DELETE_CIRCLE_DELETE = "circle/verify/v1/deleteCircle?circleId=%d";
}
