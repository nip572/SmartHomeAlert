package com.example.nipun.smarthomealert;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class RecipeResponse extends Throwable {

    private Integer id;
    private String title;
    private String image;
    private String imageType;
    private Integer usedIngredientCount;
    private Integer missedIngredientCount;
    private Integer likes;

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
     * The imageType
     */
    public String getImageType() {
        return imageType;
    }

    /**
     *
     * @param imageType
     * The imageType
     */
    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    /**
     *
     * @return
     * The usedIngredientCount
     */
    public Integer getUsedIngredientCount() {
        return usedIngredientCount;
    }

    /**
     *
     * @param usedIngredientCount
     * The usedIngredientCount
     */
    public void setUsedIngredientCount(Integer usedIngredientCount) {
        this.usedIngredientCount = usedIngredientCount;
    }

    /**
     *
     * @return
     * The missedIngredientCount
     */
    public Integer getMissedIngredientCount() {
        return missedIngredientCount;
    }

    /**
     *
     * @param missedIngredientCount
     * The missedIngredientCount
     */
    public void setMissedIngredientCount(Integer missedIngredientCount) {
        this.missedIngredientCount = missedIngredientCount;
    }

    /**
     *
     * @return
     * The likes
     */
    public Integer getLikes() {
        return likes;
    }

    /**
     *
     * @param likes
     * The likes
     */
    public void setLikes(Integer likes) {
        this.likes = likes;
    }

}