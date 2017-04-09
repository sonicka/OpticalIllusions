package com.example.sona.opticalillusions.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Soňa on 08-Apr-17.
 */

public class FavouriteIllusion extends RealmObject{
    @PrimaryKey
    private int id;
    private String name;
    private String category;
    private String description;
    private int thumbnail;
    private int picture;
    private String animation;

    public FavouriteIllusion () {
    }

    public FavouriteIllusion(int id, String name, int thumbnail) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public FavouriteIllusion(int id, String name, String category, String description, int thumbnail, int picture, String animation) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.thumbnail = thumbnail;
        this.picture = picture;
        this.animation = animation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getAnimation() {
        return animation;
    }

    public void setAnimation(String animation) {
        this.animation = animation;
    }

    @Override
    public String toString() {
        return "FavouriteIllusion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", thumbnail=" + thumbnail +
                ", picture=" + picture +
                ", animation='" + animation + '\'' +
                '}';
    }
}
