package com.example.nipun.smarthomealert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nipun on 11/26/16.
 */

public class ModelRelatedRecipes{

    private Integer id;
    private String title;
    private Integer readyInMinutes;
    private String image;
    private List<String> imageUrls = new ArrayList<String>();

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The readyInMinutes
     */
    public Integer getReadyInMinutes() {
        return readyInMinutes;
    }

    /**
     *
     * @param readyInMinutes
     * The readyInMinutes
     */
    public void setReadyInMinutes(Integer readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The imageUrls
     */
    public List<String> getImageUrls() {
        return imageUrls;
    }

    /**
     *
     * @param imageUrls
     * The imageUrls
     */
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

}

