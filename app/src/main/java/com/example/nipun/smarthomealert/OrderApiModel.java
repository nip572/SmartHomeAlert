package com.example.nipun.smarthomealert;

/**
 * Created by Nipun on 12/6/16.
 */

public class OrderApiModel {

    private String userId;
    private String products;

    /**
     *
     * @return
     * The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The products
     */
    public String getProducts() {
        return products;
    }

    /**
     *
     * @param products
     * The products
     */
    public void setProducts(String products) {
        this.products = products;
    }

    public OrderApiModel(String userId, String products) {
        this.userId = userId;
        this.products = products;
    }
}
