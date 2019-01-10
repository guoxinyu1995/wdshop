package com.example.wdshop.shoppingcart.bean;
/**
 * 加入购物车的bean
 * */
public class AddCartBean {

    private String message;
    private String status;
    private final String SUCCESS_STATUS="0000";
    public boolean isSuccess(){
        return status.equals(SUCCESS_STATUS);
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
