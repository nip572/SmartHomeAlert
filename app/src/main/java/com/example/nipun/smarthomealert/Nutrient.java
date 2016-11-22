package com.example.nipun.smarthomealert;

/**
 * Created by Nipun on 11/21/16.
 */

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Nutrient {

    private String title;
    private Double amount;
    private String unit;
    private Double percentOfDailyNeeds;

    /**
     *
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     *     The amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     *     The amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     *
     * @return
     *     The unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     *
     * @param unit
     *     The unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     *
     * @return
     *     The percentOfDailyNeeds
     */
    public Double getPercentOfDailyNeeds() {
        return percentOfDailyNeeds;
    }

    /**
     *
     * @param percentOfDailyNeeds
     *     The percentOfDailyNeeds
     */
    public void setPercentOfDailyNeeds(Double percentOfDailyNeeds) {
        this.percentOfDailyNeeds = percentOfDailyNeeds;
    }

}
