package com.example.sona.opticalillusions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.sona.opticalillusions.model.FavouriteIllusion;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class FavouritesActivity extends AppCompatActivity {

    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm realm = Realm.getInstance(config);

        RealmHelper realmHelper = new RealmHelper(realm);
        ArrayList<FavouriteIllusion> listIllusions = realmHelper.dbFavouritesToList(realm.where(FavouriteIllusion.class).findAll());

        GridView gridView = (GridView) findViewById(R.id.gv_favourites_grid);
        gridView.setAdapter(new FavouritesAdapter(this, listIllusions));
        gridView.setOnItemClickListener(new OnIllusionClickListener(this));

        Button removeButton = (Button) findViewById(R.id.buttonRemove);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO remove
            }
        });

        Button switchViewButton = (Button) findViewById(R.id.b_switch_to_grid);
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
