package com.example.wdshop.shoppingcart.bean;
/**
 * 加入购物车拼接参数的bean
 * */
public class ShoppCartBean {
    private int commodityId;
    private int count;

    public ShoppCartBean(int commodityId, int count) {
        this.commodityId = commodityId;
        this.count = count;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
