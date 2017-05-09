package com.example.sona.opticalillusions;

import android.view.View;
import android.widget.ImageButton;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;

import io.realm.Case;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;

/**
 * Helper class to handle operations within Realm database.
 * Created by Soňa on 09-Apr-17.
 */

class RealmHelper {

    private Realm realm;

    /**
     * Constructor of RealmHelper.
     *
     * @param realm Realm database
     */
    RealmHelper(Realm realm) {
        this.realm = realm;
    }

    /**
     * Sends content of a list to Realm database.
     *
     * @param list list of data from xml file
     */
    void listToDb(ArrayList<Illusion> list) {
        realm.beginTransaction();
        for (Illusion i : list) {
            realm.copyToRealmOrUpdate(i);
        }
        realm.commitTransaction();
    }

    /**
     * Gets all illusions from Realm database.
     *
     * @return collection of all illusions
     */
    OrderedRealmCollection<Illusion> getAll() {
        return realm.where(Illusion.class).findAll();
    }

    /**
     * Gets all favourite illusions from Realm database.
     *
     * @return collection of favourite illusions
     */
    OrderedRealmCollection<Illusion> getFavourites() {
        return realm.where(Illusion.class).equalTo("isFavourite", true).findAll();
    }

    /**
     * Filters illusions in Realm database.
     *
     * @return filtered list of illusions
     */
    OrderedRealmCollection<Illusion> searchIllusions(CharSequence search) {
        return realm.where(Illusion.class)
                .contains("name", search.toString(), Case.INSENSITIVE)
                .or()
                .contains("category", search.toString(), Case.INSENSITIVE)
                .or()
                .contains("description", search.toString(), Case.INSENSITIVE)
                .findAll();
    }

    /**
     * Filters favourite illusions in Realm database.
     *
     * @return filtered list of illusions
     */
    OrderedRealmCollection<Illusion> searchInFavourites(CharSequence search) {
        OrderedRealmCollection<Illusion> illusions;
        if (search == null || search.length() == 0) {
            illusions = realm.where(Illusion.class).equalTo("isFavourite", true).findAll();

        } else {
            illusions = realm.where(Illusion.class)
                    .beginGroup()
                    .equalTo("isFavourite", true)
                    .endGroup()
                    .beginGroup()
                    .contains("name", search.toString(), Case.INSENSITIVE)
                    .or()
                    .contains("category", search.toString(), Case.INSENSITIVE)
                    .or()
                    .contains("description", search.toString(), Case.INSENSITIVE)
                    .endGroup()
                    .findAll();
        }
        return illusions;
    }

    /**
     * Favourite illusion sets to non-favourite and vice versa.
     *
     * @param realm    Realm db
     * @param v        current view
     * @param illusion current illusion
     */
    void setFavourite(Realm realm, final View v, final Illusion illusion) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (illusion.isfavourite()) {
                    ((ImageButton) v).setImageResource(R.drawable.ic_favourite);
                    illusion.setFavourite(false);
                } else {
                    ((ImageButton) v).setImageResource(R.drawable.ic_unfavourite);
                    illusion.setFavourite(true);
                }
                realm.copyToRealmOrUpdate(illusion);
            }
        });
    }
}
