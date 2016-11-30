
package com.example.nipun.smarthomealert;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class FireBaseModel {

    private String address;
    private String automaticOrder;
    private Integer daysToOrder;
    private String email;
    private List<GroceryList> groceryList = new ArrayList<GroceryList>();
    private String open;
    private List<Order> orders = new ArrayList<Order>();
    private String pushNotifications;
    private Integer radius;
    private Integer temperatureValue;
    private Integer weightValue;
    private Integer minimumThreshold;

    /**
     * 
     * @return
     *     The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * @return
     *     The automaticOrder
     */
    public String getAutomaticOrder() {
        return automaticOrder;
    }

    /**
     * 
     * @param automaticOrder
     *     The automaticOrder
     */
    public void setAutomaticOrder(String automaticOrder) {
        this.automaticOrder = automaticOrder;
    }

    /**
     * 
     * @return
     *     The daysToOrder
     */
    public Integer getDaysToOrder() {
        return daysToOrder;
    }

    /**
     * 
     * @param daysToOrder
     *     The daysToOrder
     */
    public void setDaysToOrder(Integer daysToOrder) {
        this.daysToOrder = daysToOrder;
    }

    /**
     * 
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return
     *     The groceryList
     */
    public List<GroceryList> getGroceryList() {
        return groceryList;
    }

    /**
     * 
     * @param groceryList
     *     The groceryList
     */
    public void setGroceryList(List<GroceryList> groceryList) {
        this.groceryList = groceryList;
    }

    /**
     * 
     * @return
     *     The open
     */
    public String getOpen() {
        return open;
    }

    /**
     * 
     * @param open
     *     The open
     */
    public void setOpen(String open) {
        this.open = open;
    }

    /**
     * 
     * @return
     *     The orders
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * 
     * @param orders
     *     The orders
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    /**
     * 
     * @return
     *     The pushNotifications
     */
    public String getPushNotifications() {
        return pushNotifications;
    }

    /**
     * 
     * @param pushNotifications
     *     The pushNotifications
     */
    public void setPushNotifications(String pushNotifications) {
        this.pushNotifications = pushNotifications;
    }

    /**
     * 
     * @return
     *     The radius
     */
    public Integer getRadius() {
        return radius;
    }

    /**
     * 
     * @param radius
     *     The radius
     */
    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    /**
     * 
     * @return
     *     The temperatureValue
     */
    public Integer getTemperatureValue() {
        return temperatureValue;
    }

    /**
     * 
     * @param temperatureValue
     *     The temperatureValue
     */
    public void setTemperatureValue(Integer temperatureValue) {
        this.temperatureValue = temperatureValue;
    }

    /**
     * 
     * @return
     *     The weightValue
     */
    public Integer getWeightValue() {
        return weightValue;
    }

    /**
     * 
     * @param weightValue
     *     The weightValue
     */
    public void setWeightValue(Integer weightValue) {
        this.weightValue = weightValue;
    }

    /**
     * 
     * @return
     *     The minimumThreshold
     */
    public Integer getMinimumThreshold() {
        return minimumThreshold;
    }

    /**
     * 
     * @param minimumThreshold
     *     The minimumThreshold
     */
    public void setMinimumThreshold(Integer minimumThreshold) {
        this.minimumThreshold = minimumThreshold;
    }

}
