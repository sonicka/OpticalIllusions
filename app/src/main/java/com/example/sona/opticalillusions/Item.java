package com.example.sona.opticalillusions;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Soňa on 10-Apr-17.
 */

class Item implements Parcelable {
    public final int id;
    public final String name;
    public final String category;
    public final String description;
    public final int thumbnail;
    public final int picture;
    public final String animation;

    public Item(int id, String name, String category, String description, int thumbnail, int picture, String animation) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.thumbnail = thumbnail;
        this.picture = picture;
        this.animation = animation;
    }

    protected Item(Parcel in) {
        id = in.readInt();
        name = in.readString();
        category = in.readString();
        description = in.readString();
        thumbnail = in.readInt();
        picture = in.readInt();
        animation = in.readString();
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

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}