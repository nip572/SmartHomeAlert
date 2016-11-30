
package com.example.nipun.smarthomealert;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Order {

    private String estimatedDelivery;
    private String products;
    private String status;
    private String totalPrice;

    /**
     * 
     * @return
     *     The estimatedDelivery
     */
    public String getEstimatedDelivery() {
        return estimatedDelivery;
    }

    /**
     * 
     * @param estimatedDelivery
     *     The estimatedDelivery
     */
    public void setEstimatedDelivery(String estimatedDelivery) {
        this.estimatedDelivery = estimatedDelivery;
    }

    /**
     * 
     * @return
     *     The products
     */
    public String getProducts() {
        return products;
    }

    /**
     * 
     * @param products
     *     The products
     */
    public void setProducts(String products) {
        this.products = products;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The totalPrice
     */
    public String getTotalPrice() {
        return totalPrice;
    }

    /**
     * 
     * @param totalPrice
     *     The totalPrice
     */
    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

}
