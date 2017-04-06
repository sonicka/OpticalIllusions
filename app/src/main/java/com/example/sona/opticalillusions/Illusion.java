package com.example.sona.opticalillusions;
import android.graphics.drawable.Drawable;
import android.net.LinkAddress;

/**
 * Created by Soňa on 05-Apr-17.
 */

public class Illusion {
    private String name;
    private String id;
    private Category category;
    private String description;
    private int picture;
    private LinkAddress animation;
    private IllusionManagerImpl manager;

    public Illusion(String name, int picture) {
        this.name = name;
        this.picture = picture;
    }

    public Illusion(String name, String id, Category category, String description, int picture, IllusionManagerImpl manager) {
        this.name = name;
        this.id = id;
        this.category = category;
        this.description = description;
        this.picture = picture;
        this.manager = manager;
    }

    public Illusion(String name, String id, Category category, String description, int picture, LinkAddress animation, IllusionManagerImpl manager) {
        this.name = name;
        this.id = id;
        this.category = category;
        this.description = description;
        this.picture = picture;
        this.animation = animation;
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getPicture() {
        return picture;
    }

    public LinkAddress getAnimation() {
        return animation;
    }
}
