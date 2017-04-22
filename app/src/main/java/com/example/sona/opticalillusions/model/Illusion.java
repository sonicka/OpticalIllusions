package com.example.sona.opticalillusions.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Soňa on 07-Apr-17.
 */

public class Illusion extends RealmObject implements Parcelable{

    @PrimaryKey
    private String name;
    private String category;
    private String description;
    private int thumbnail;
    private int picture;
    private String animation;
    private boolean isFavourite;
    private boolean isChecked;

    private static final String GEO = "Geometric Illusions";
    private static final String COL = "Color Illusions";
    private static final String THD = "3D Illusions";
    private static final String MOT = "Motion Illusions";

    public Illusion () {
    }

    public Illusion(String name, int picture) {
        this.name = name;
        this.picture = picture;

    }  public Illusion(String name) {
        this.name = name;
    }

    public Illusion(String name, String category, String description, int thumbnail, int picture, String animation, boolean isFavourite) {
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
        thumbnail = in.readInt();
        picture = in.readInt();
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

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    @Override
    public String toString() {
        return "Illusion{" +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", picture=" + picture +
                ", animation='" + animation + '\'' +
                "is favourite? " + isFavourite + '\'' + 
                '}';
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
        dest.writeInt(thumbnail);
        dest.writeInt(picture);
        dest.writeString(animation);
        dest.writeByte((byte) (isFavourite ? 1 : 0));
    }

    public boolean isChecked(){
        return isChecked;
    }
    public void toggleChecked(){
        isChecked = !isChecked;
    }
}
