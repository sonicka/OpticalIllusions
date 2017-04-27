package com.example.sona.opticalillusions;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;

/**
 * Created by Soňa on 06-Apr-17.
 */

public interface IllusionManager {
    Boolean addToFavourites(Illusion illusion);

    Boolean removeFromFavourites(Illusion illusion);

    ArrayList<Illusion> searchInName(String string);

    ArrayList<Illusion> searchInDescription(String string);
}
