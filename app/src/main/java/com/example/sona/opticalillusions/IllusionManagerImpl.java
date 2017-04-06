package com.example.sona.opticalillusions;

import java.util.ArrayList;

/**
 * Created by Soňa on 06-Apr-17.
 */

public class IllusionManagerImpl implements IllusionManager{

    public Boolean addToFavourites (Illusion illusion) {
        if (illusion == null) {
            return false;
        } else {
            //TODO
            return true;
        }
    }

    public Boolean removeFromFavourites (Illusion illusion) {
        if (illusion == null) {
            return false;
        } else {
            //TODO
            return true;
        }
    }

    public ArrayList<Illusion> searchInName (String string) {
        ArrayList<Illusion> result = new ArrayList<>();
        if (string == null || string.isEmpty()) {
            return result;
        } else {
            //TODO
            return result;
        }
    }

    public ArrayList<Illusion> searchInDescription (String string) {
        ArrayList<Illusion> result = new ArrayList<>();
        if (string == null || string.isEmpty()) {
            return result;
        } else {
            //TODO
            return result;
        }
    }

    public ArrayList<Illusion> findAll () {
        ArrayList<Illusion> result = new ArrayList<>();
        return result;
    }
}
