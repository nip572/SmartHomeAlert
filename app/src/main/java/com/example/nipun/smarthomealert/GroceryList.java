
package com.example.nipun.smarthomealert;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GroceryList implements Serializable {

    private String imageURL;
    private String name;

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

}
