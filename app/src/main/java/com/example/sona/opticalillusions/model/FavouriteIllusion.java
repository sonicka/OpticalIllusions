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

}
