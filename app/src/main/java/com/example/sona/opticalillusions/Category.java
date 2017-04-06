package com.example.sona.opticalillusions;

/**
 * Created by Soňa on 06-Apr-17.
 */

public enum Category {

    COLOR("Color illusion"),
    GEOMETRIC("Geometric illusion"),
    THREEDEE("3D illusion"),
    FICTIONAL("Fictional illusion");

    private String stringValue;
    Category(String toString) {
        stringValue = toString;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
