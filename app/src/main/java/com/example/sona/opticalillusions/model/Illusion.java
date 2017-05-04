package com.example.sona.opticalillusions.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Soňa on 07-Apr-17.
 */

public class Illusion extends RealmObject implements Parcelable {

    @PrimaryKey
    private String name;
    private String category;
    private String description;
    private String thumbnail;
    private String picture;
    private String animation;
    private boolean isFavourite;

    private static final String GEO = "Geometric Illusions";
    private static final String COL = "Color Illusions";
    private static final String THD = "3D Illusions";
    private static final String MOT = "Motion Illusions";

    public Illusion() {
    }


    public Illusion(String name, String category, String description, String thumbnail, String picture, String animation, boolean isFavourite) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.thumbnail = thumbnail;
        this.picture = picture;
        this.animation = animation;
        this.isFavourite = isFavourite;
    }

    protected Illusion(Parcel in) {
        name = in.readString();
        category = in.readString();
        description = in.readString();
        thumbnail = in.readString();
        picture = in.readString();
        animation = in.readString();
        isFavourite = in.readByte() != 0;
    }

    public static final Creator<Illusion> CREATOR = new Creator<Illusion>() {
        @Override
        public Illusion createFromParcel(Parcel in) {
            return new Illusion(in);
        }

        @Override
        public Illusion[] newArray(int size) {
            return new Illusion[size];
        }
    };

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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAnimation() {
        return animation;
    }

    public void setAnimation(String animation) {
        this.animation = animation;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(category);
        dest.writeString(description);
        dest.writeString(thumbnail);
        dest.writeString(picture);
        dest.writeString(animation);
        dest.writeByte((byte) (isFavourite ? 1 : 0));
    }

    @Override
    public String toString() {
        return "Illusion{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", picture='" + picture + '\'' +
                ", animation='" + animation + '\'' +
                ", isFavourite=" + isFavourite +
                '}';
    }
}
