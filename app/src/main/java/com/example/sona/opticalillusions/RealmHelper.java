package com.example.sona.opticalillusions;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Soňa on 09-Apr-17.
 */

public class RealmHelper {

    private Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    //WRITE
    public void save(final Illusion illusion) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Illusion i = realm.copyToRealmOrUpdate(illusion);
            }
        });
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
