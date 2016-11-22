package com.example.nipun.smarthomealert;

/**
 * Created by Nipun on 11/21/16.
 */

import java.util.List;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Ingredient {

    private String name;
    private Double amount;
    private String unit;
    private List<Nutrient_> nutrients = new ArrayList<Nutrient_>();

    /**
     *
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
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
     *     The nutrients
     */
    public List<Nutrient_> getNutrients() {
        return nutrients;
    }

    /**
     *
     * @param nutrients
     *     The nutrients
     */
    public void setNutrients(List<Nutrient_> nutrients) {
        this.nutrients = nutrients;
    }

}
