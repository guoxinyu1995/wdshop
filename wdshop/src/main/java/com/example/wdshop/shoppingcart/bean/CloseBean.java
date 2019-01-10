package com.example.wdshop.shoppingcart.bean;
/**
 * 创建订单拼接参数的bean
 * */
public class CloseBean {
    private int commodityId;
    private int amount;

    public CloseBean(int commodityId, int count) {
        this.commodityId = commodityId;
        this.amount = count;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getCount() {
        return amount;
    }

    public void setCount(int count) {
        this.amount = count;
    }
}
