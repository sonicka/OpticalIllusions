package com.example.sona.opticalillusions.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Soňa on 08-Apr-17.
 */

public class FavouriteIllusion extends RealmObject implements Parcelable{
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

    protected FavouriteIllusion(Parcel in) {
        id = in.readInt();
        name = in.readString();
        category = in.readString();
        description = in.readString();
        thumbnail = in.readInt();
        picture = in.readInt();
        animation = in.readString();
    }

    public static final Creator<FavouriteIllusion> CREATOR = new Creator<FavouriteIllusion>() {
        @Override
        public FavouriteIllusion createFromParcel(Parcel in) {
            return new FavouriteIllusion(in);
        }

        @Override
        public FavouriteIllusion[] newArray(int size) {
            return new FavouriteIllusion[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(category);
        dest.writeString(description);
        dest.writeInt(thumbnail);
        dest.writeInt(picture);
        dest.writeString(animation);
    }
}
