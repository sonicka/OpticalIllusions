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
 * Created by Soňa on 09-Apr-17.
 */

public class RealmHelper {

    private Realm realm;

    public RealmHelper(Realm realm) {
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

    public void setFavourite(Realm realm, View v, Illusion illusion) {
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
