package com.example.sona.opticalillusions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.SearchView;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Soňa on 04-Apr-17.
 */

public class AllIllusionsActivity extends AppCompatActivity {

    private Realm realm;
    private SearchView searchView;
    private ArrayList<Illusion> filteredIllusions;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_illusions);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);

        final RealmHelper realmHelper = new RealmHelper(realm);
        final ArrayList<Illusion> listIllusions = realmHelper.dbToList(realm.where(Illusion.class).findAll());

        // GRIDVIEW

        final ImageAdapter imageAdapter = new ImageAdapter(this, listIllusions);
        final GridView gridView = (GridView) findViewById(R.id.gv_illusion_grid);
        gridView.setAdapter(imageAdapter);
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Illusion i = (Illusion) parent.getItemAtPosition(position);
                Intent intent = new Intent(AllIllusionsActivity.this, ViewIllusionActivity.class);
                intent.putExtra("item", i);
                startActivity(intent);
            }
        };
        gridView.setOnItemClickListener(onItemClickListener);

        // LISTVIEW

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
        final ExpandableListView listView = (ExpandableListView) findViewById(R.id.id_list_view);
        listView.setAdapter(adapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                long packedPosition = ExpandableListView.getPackedPositionForChild(groupPosition, childPosition);
                int flatPosition = parent.getFlatListPosition(packedPosition);
                Illusion i = (Illusion) parent.getItemAtPosition(flatPosition);
                Intent intent = new Intent(AllIllusionsActivity.this, ViewIllusionActivity.class);
                intent.putExtra("item", i);
                startActivity(intent);
                return false;
            }
        });

        Button favouritesButton = (Button) findViewById(R.id.b_favourites);
        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllIllusionsActivity.this, FavouritesActivity.class));
            }
        });

        Button switchViewButton = (Button) findViewById(R.id.b_switch_to_list);
        switchViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gridView.getVisibility() == View.VISIBLE) {
                    gridView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    setTitle("Optical Illusions Categories");
                } else {
                    listView.setVisibility(View.GONE);
                    gridView.setVisibility(View.VISIBLE);
                    setTitle("Optical Illusions Preview");
                }
            }
        });

        //TODO filter
    /*    SearchView searchView = (SearchView) findViewById(R.id.sv_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getCurrentSearchIllusions((BaseAdapter) imageAdapter);
                return true;
            }
        });
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }

    private void getCurrentSearchIllusions(BaseAdapter adapter) {
        String search = searchView.getQuery().toString().toUpperCase();
        RealmHelper helper = new RealmHelper(realm);

        if (search.isEmpty()) {
            filteredIllusions.clear();
            filteredIllusions.addAll(helper.getAll());
            adapter.notifyDataSetChanged();
        } else {

            filteredIllusions.clear();
            filteredIllusions.addAll(helper.searchIllusions(search));
            adapter.notifyDataSetChanged();
        }*/
    }
}