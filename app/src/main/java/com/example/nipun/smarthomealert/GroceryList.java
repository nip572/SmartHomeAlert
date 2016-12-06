
package com.example.nipun.smarthomealert;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GroceryList implements Serializable {

    private String imageURL;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroceryList that = (GroceryList) o;

        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     *

     * @return
     *     The imageURL
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * 
     * @param imageURL
     *     The imageURL
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

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

    @Override
    public String toString() {
        return "GroceryList{" +
                "name='" + name + '\'' +
                '}';
    }
}
