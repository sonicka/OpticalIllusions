package com.example.sona.opticalillusions;
import android.graphics.drawable.Drawable;
import android.net.LinkAddress;

/**
 * Created by Soňa on 05-Apr-17.
 */

public class Illusion {
    private String name;
    private String id;
    private String description;
    private Drawable picture;
    private LinkAddress animation;

    public Illusion(String name, String id, String description, Drawable picture, LinkAddress animation) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.picture = picture;
        this.animation = animation;
    }
}
