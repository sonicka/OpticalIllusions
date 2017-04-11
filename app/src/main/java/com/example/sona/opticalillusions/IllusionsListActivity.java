package com.example.sona.opticalillusions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class IllusionsListActivity extends AppCompatActivity {

    private Realm realm;
    private Button searchButton;

    private ArrayList<Illusion> illusions = new ArrayList<>();

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

        RealmHelper realmHelper = new RealmHelper(realm);
        ArrayList<Illusion> listIllusions = realmHelper.dbToList(realm.where(Illusion.class).findAll());

        ListView listView = (ListView) findViewById(R.id.id_list_view);
        listView.setAdapter(new ListAdapter(this, listIllusions));
        listView.setOnItemClickListener(new OnIllusionClickListener(this));

        Button favouritesButton = (Button) findViewById(R.id.b_favourites);
        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IllusionsListActivity.this, FavouritesActivity.class));
            }
        });

        Button switchViewButton = (Button) findViewById(R.id.b_switch_to_grid);
        switchViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IllusionsListActivity.this, IllusionsGridActivity.class));
            }
        });

        SearchView searchView = (SearchView) findViewById(R.id.sv_search_list);
        final ListAdapter adapter = new ListAdapter(this, illusions);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
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

    @Override //TODO naco?
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}