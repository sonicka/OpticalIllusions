package com.example.sona.opticalillusions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Soňa on 04-Apr-17.
 */

public class IllusionsGridActivity extends AppCompatActivity {
    Button favouritesButton;
    Button switchViewButton;
    Button searchButton;
    private ImageAdapter adapter;
    private GridView gridView;
    private Realm realm;
    private ArrayList<Illusion> listIllusions = new ArrayList<>();
    private AdapterView.OnItemClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illusions_grid);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);


        RealmResults<Illusion> list = realm.where(Illusion.class).findAll();
        Log.v("ROFL", String.valueOf(list.size()));

        for (Illusion i : list) {
            listIllusions.add(i);
        }

        gridView = new GridView(this);
        gridView = (GridView) findViewById(R.id.gv_illusion_grid);
        adapter = new ImageAdapter(this, listIllusions, listener);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(new Intent(IllusionsGridActivity.this, ViewIllusionActivity.class));
            }
        });

        favouritesButton = (Button)findViewById(R.id.buttonFavourites);
        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IllusionsGridActivity.this, FavouritesActivity.class));
            }
        });
        switchViewButton = (Button)findViewById(R.id.buttonSwitchToList);
        switchViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IllusionsGridActivity.this, IllusionsListActivity.class));
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

//    @Override
//    public void onListItemClick(int clickedItemIndex) {
//        startActivity(new Intent(IllusionsGridActivity.this, MainActivity.class));
//    }
}