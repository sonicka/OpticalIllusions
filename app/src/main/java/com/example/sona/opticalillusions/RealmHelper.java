package com.example.sona.opticalillusions;

import com.example.sona.opticalillusions.model.FavouriteIllusion;
import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Soňa on 09-Apr-17.
 */

public class RealmHelper {

    private Realm realm;

    RealmHelper(Realm realm) {
        this.realm = realm;
    }

    void save(final Illusion illusion) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(illusion);
            }
        });
    }

    public void save(final FavouriteIllusion illusion) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(illusion);
            }
        });
    }

    void saveItem (final Item item) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                FavouriteIllusion i = new FavouriteIllusion(item.id, item.name, item.category, item.description,
                        item.thumbnail, item.picture, item.animation);
                realm.copyToRealmOrUpdate(i);
            }
        });
    }

    ArrayList<FavouriteIllusion> dbFavouritesToList (RealmResults<FavouriteIllusion> results) {
        ArrayList<FavouriteIllusion> list = new ArrayList<>();
        for (FavouriteIllusion i : results) {
            list.add(i);
        }
        return list;
    }

    ArrayList<Illusion> dbToList (RealmResults<Illusion> results) {
        ArrayList<Illusion> list = new ArrayList<>();
        for (Illusion i : results) {
            list.add(i);
        }
        return list;
    }

    //READ
    public ArrayList<String> retrieve() {
        ArrayList<String> illusionNames = new ArrayList<>();
        RealmResults<Illusion> illusions = realm.where(Illusion.class).findAll();
        for(Illusion i : illusions) {
            illusionNames.add(i.getName());
        }
        return illusionNames;
    }
}
