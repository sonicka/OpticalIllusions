package com.example.sona.opticalillusions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

//TODO ONITEMCLICKLISTENER

public class IllusionsListActivity extends AppCompatActivity {

    private Realm realm;
    Button favouritesButton;
    Button switchViewButton;
    Button searchButton;

    private ListView listView;
    private ListAdapter adapter;
    ArrayList<Illusion> illusions = new ArrayList<>();

    Illusion cafewall = new Illusion("Cafe wall", R.drawable.cafewall);
    Illusion hering = new Illusion("Hering illusion", R.drawable.hering);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illusions_list);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);


        RealmResults<Illusion> list = realm.where(Illusion.class).findAll();

        for (Illusion i : list) {
            illusions.add(i);
        }

        adapter = new ListAdapter(this, illusions);
        listView = (ListView) findViewById(R.id.id_list_view);
        listView.setAdapter(adapter);

        favouritesButton = (Button) findViewById(R.id.buttonFavourites);
        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IllusionsListActivity.this, FavouritesActivity.class));
            }
        });
        switchViewButton = (Button) findViewById(R.id.buttonSwitchToGrid);
        switchViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IllusionsListActivity.this, IllusionsGridActivity.class));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}