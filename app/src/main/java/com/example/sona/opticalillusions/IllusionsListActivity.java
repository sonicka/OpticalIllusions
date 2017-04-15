package com.example.sona.opticalillusions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class IllusionsListActivity extends AppCompatActivity {

    private Realm realm;

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

        LinkedHashMap<String, List<Illusion>> linkedMap = new LinkedHashMap<>();
        List<String> headers = new ArrayList<>();
        headers.add("All Illusions");
        linkedMap.put("All Illusions", listIllusions);
        linkedMap.put("3D illusions", null);
        linkedMap.put("Color Illusions", null);
        linkedMap.put("Geometric Illusions", null);
        linkedMap.put("Motion illusion", null);

        for (Illusion i : listIllusions) {
            if (!headers.contains(i.getCategory())) {
                headers.add(i.getCategory());
                ArrayList<Illusion> list = new ArrayList<>();
                list.add(i);
                linkedMap.put(i.getCategory(), list);
            } else {
                linkedMap.get(i.getCategory()).add(i);
            }
        }
        Log.v("hi", String.valueOf(linkedMap.size()));
        Log.v("hi", String.valueOf(linkedMap.keySet()));
        Log.v("hi", String.valueOf(linkedMap.get("3D illusions")));
        Log.v("hi", String.valueOf(linkedMap.get("3D illusions").size()));
        Log.v("hi", String.valueOf(linkedMap.get("Geometric Illusions").size()));
        Log.v("hi", String.valueOf(linkedMap.get("Color Illusions").size()));
        Log.v("hi", String.valueOf(linkedMap.get("Motion illusion").size()));
        Log.v("hi", String.valueOf(linkedMap.get("All Illusions").size()));


        final ListAdapter adapter = new ListAdapter(this, linkedMap);
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.id_list_view);
        listView.setAdapter(adapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                long packedPosition = ExpandableListView.getPackedPositionForChild(groupPosition, childPosition);
                int flatPosition = parent.getFlatListPosition(packedPosition);
                Illusion i = (Illusion) parent.getItemAtPosition(flatPosition);
                Intent intent = new Intent(IllusionsListActivity.this, ViewIllusionActivity.class);
                intent.putExtra("item", i);
                startActivity(intent);
                return false;

            }

        });

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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });
    }

    @Override //TODO naco?
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

}