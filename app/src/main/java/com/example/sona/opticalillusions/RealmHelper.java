package com.example.sona.opticalillusions;

import android.view.View;
import android.widget.ImageButton;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Helper class to handle operations within Realm database.
 * Created by Soňa on 09-Apr-17.
 */

public class RealmHelper {

    private Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    /**
     * Sends content of a list to Realm database.
     * @param list list of data from xml file
     */
    void listToDb(ArrayList<Illusion> list) {
        realm.beginTransaction();
        for (Illusion i : list) {
            realm.copyToRealmOrUpdate(i);
        }
        realm.commitTransaction();
    }

    void save(final Illusion illusion) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(illusion);
            }
        });
    }

    ArrayList<Illusion> dbToList(RealmResults<Illusion> results) {
        ArrayList<Illusion> list = new ArrayList<>();
        for (Illusion i : results) {
            list.add(i);
        }
        return list;
    }

    //READ
    public ArrayList<Illusion> getAll() {
        ArrayList<Illusion> all = new ArrayList<>();
        RealmResults<Illusion> illusions = realm.where(Illusion.class).findAll();
        for (Illusion i : illusions) {
            all.add(i);
        }
        return all;
    }

    public ArrayList<Illusion> getFavourites() {
        ArrayList<Illusion> favourite = new ArrayList<>();
        RealmResults<Illusion> illusions = realm.where(Illusion.class).equalTo("isFavourite", true).findAll();
        for (Illusion i : illusions) {
            favourite.add(i);
        }
        return favourite;
    }

    public List<Illusion> searchIllusions(String search) {
        realm.beginTransaction();
        List<Illusion> illusions = realm.where(Illusion.class).contains("name", search, Case.INSENSITIVE)
                .findAllSortedAsync("name", Sort.ASCENDING);
        realm.commitTransaction();
        return illusions;
    }

    /**
     * Favourite illusion sets to non-favourite and vice versa.
     * @param realm Reealm db
     * @param v current view
     * @param illusion current illusion
     */
    void setFavourite(Realm realm, View v, Illusion illusion) {
        realm.beginTransaction();
        if (illusion.isFavourite()) {
            ((ImageButton) v).setImageResource(R.drawable.ic_favourite);
            illusion.setFavourite(false);
        } else {
            ((ImageButton) v).setImageResource(R.drawable.ic_unfavourite);
            illusion.setFavourite(true);
        }
        realm.copyToRealmOrUpdate(illusion);
        realm.commitTransaction();
    }
}
