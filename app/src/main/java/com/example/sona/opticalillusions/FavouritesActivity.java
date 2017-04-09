package com.example.sona.opticalillusions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.sona.opticalillusions.model.FavouriteIllusion;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class FavouritesActivity extends AppCompatActivity {

    Button removeButton;
    Button switchViewButton;
    Button searchButton;
    private GridView gridView;
    private Realm realm2;
    private RealmHelper realmHelper2 = new RealmHelper(realm2);
    private ArrayList<FavouriteIllusion> listIllusions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("ROFL", "activity started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm2 = Realm.getInstance(config);

        FavouriteIllusion test = new FavouriteIllusion(1, "Cafe wall illusion", "", "", R.drawable.thumb_cafe_wall, R.drawable.cafewall, "");
        Log.v("HUEHUE", test.toString());
        realm2.beginTransaction();
        realm2.copyToRealmOrUpdate(test);
        realm2.commitTransaction();
        //realmHelper2.save(test);
        Log.v("HUEHUE", "pizza");
        Log.v("HUEHUE", realm2.where(FavouriteIllusion.class).toString());

        RealmResults<FavouriteIllusion> list = realm2.where(FavouriteIllusion.class).findAll();

        for (FavouriteIllusion i : list) {
            listIllusions.add(i);
        }

        gridView = new GridView(this);
        gridView = (GridView) findViewById(R.id.gv_favourites_grid);
        gridView.setAdapter(new FavouritesAdapter(this, listIllusions));

        removeButton = (Button)findViewById(R.id.buttonRemove);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO remove
            }
        });
        switchViewButton = (Button)findViewById(R.id.buttonSwitchToGrid);
        switchViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavouritesActivity.this, IllusionsGridActivity.class));
            }
        });
        /*searchButton = (Button)findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IllusionsGridActivity.this, .class));
            }
        });*/
    }
}
