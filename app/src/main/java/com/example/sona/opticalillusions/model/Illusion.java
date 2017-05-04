package com.example.sona.opticalillusions.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Objects of this class store information about illusions.
 * Created by Soňa on 07-Apr-17.
 */

public class Illusion extends RealmObject implements Parcelable {

    @PrimaryKey
    private String name;
    private String category;
    private String description;
    private int thumbnail;
    private int picture;
    private String animation;
    private boolean isFavourite;

    public Illusion() {
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

    public Illusion(String name, String category, String description, String animation, boolean isFavourite) {
        this.name = name;
        this.category = category;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnailAndPicture(int thumbnail, int picture) {
        this.thumbnail = thumbnail;
        this.picture = picture;
    }

    public int getPicture() {
        return picture;
    }

    public String getAnimation() {
        return animation;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    @Override
    public String toString() {
        return "Illusion{" + ", name='" + name + '\'' + ", category='" + category + '\'' +
                ", description='" + description + '\'' + ", thumbnail='" + thumbnail + '\'' +
                ", picture=" + picture + ", animation='" + animation + '\'' +
                "is favourite? " + isFavourite + '\'' + '}';
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
}
