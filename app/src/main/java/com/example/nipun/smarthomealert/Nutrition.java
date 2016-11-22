package com.example.nipun.smarthomealert;

/**
 * Created by Nipun on 11/21/16.
 */


import javax.annotation.Generated;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Nutrition {

    private List<Nutrient> nutrients = new ArrayList<Nutrient>();
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();
    private CaloricBreakdown caloricBreakdown;

    /**
     *
     * @return
     *     The nutrients
     */
    public List<Nutrient> getNutrients() {
        return nutrients;
    }

    /**
     *
     * @param nutrients
     *     The nutrients
     */
    public void setNutrients(List<Nutrient> nutrients) {
        this.nutrients = nutrients;
    }

    /**
     *
     * @return
     *     The ingredients
     */
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     *
     * @param ingredients
     *     The ingredients
     */
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     *
     * @return
     *     The caloricBreakdown
     */
    public CaloricBreakdown getCaloricBreakdown() {
        return caloricBreakdown;
    }

    /**
     *
     * @param caloricBreakdown
     *     The caloricBreakdown
     */
    public void setCaloricBreakdown(CaloricBreakdown caloricBreakdown) {
        this.caloricBreakdown = caloricBreakdown;
    }

}
