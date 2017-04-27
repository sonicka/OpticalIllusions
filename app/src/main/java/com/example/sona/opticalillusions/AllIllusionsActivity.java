package com.example.sona.opticalillusions;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Soňa on 04-Apr-17.
 */

public class AllIllusionsActivity extends AppCompatActivity {

    private Illusion currentIllusion;
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

//        final RealmHelper realmHelper = new RealmHelper(realm);
        final OrderedRealmCollection<Illusion> listIllusions = realm.where(Illusion.class).findAll();

        Toolbar toolbar = (Toolbar) findViewById(R.id.top_toolbar);
        Log.v("lolol", toolbar.toString());
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText(R.string.title_activity_illusions_grid);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Giorgio.ttf");
        title.setTypeface(type);
        title.setPadding(0,55,0,0);


        // GRIDVIEW

        final ImageAdapter imageAdapter = new ImageAdapter(this, listIllusions);
        final GridView gridView = (GridView) findViewById(R.id.gv_illusion_grid);
        gridView.setAdapter(imageAdapter);
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Illusion i = (Illusion) parent.getItemAtPosition(position);
                currentIllusion = i;
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

        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousGroup) {
                    listView.collapseGroup(previousGroup);
                }
                previousGroup = groupPosition;
            }
        });


        Toolbar bottomToolbar = (Toolbar) findViewById(R.id.bottom_toolbar);
        Log.v("jujuju", bottomToolbar.toString());
        if (bottomToolbar != null) {
            setSupportActionBar(bottomToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        ImageButton favouritesButton = (ImageButton) findViewById(R.id.b_left_button);
        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllIllusionsActivity.this, FavouritesActivity.class));
            }
        });

        final ImageButton switchViewButton = (ImageButton) findViewById(R.id.b_switch_view);
        switchViewButton.setImageResource(R.drawable.ic_list);
        switchViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gridView.getVisibility() == View.VISIBLE) {
                    gridView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    setTitle("Optical Illusions Categories");
                    switchViewButton.setImageResource(R.drawable.ic_grid);
                } else {
                    listView.setVisibility(View.GONE);
                    gridView.setVisibility(View.VISIBLE);
                    setTitle("Optical Illusions Preview");
                    switchViewButton.setImageResource(R.drawable.ic_list);

                }
            }
        });

        final SearchView searchView = (SearchView) findViewById(R.id.sv_search);
//        int searchImgId = getResources().getIdentifier("android:id/search_button", null, null);
//        ImageView v = (ImageView) searchView.findViewById(searchImgId);
//        v.setImageResource(R.drawable.ic_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        if (searchPlate != null) {
            searchPlate.setBackgroundColor(Color.TRANSPARENT);
        }

        final ImageButton searchButton = (ImageButton) findViewById(R.id.ib_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //TODO =(
                searchButton.setVisibility(View.GONE);
                searchView.setVisibility(View.VISIBLE);
                searchView.requestFocus();
                searchView.setFocusableInTouchMode(true);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(searchView, InputMethodManager.SHOW_FORCED);
            }
        });
//        searchButton.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.equals(ACTION_BUTTON_PRESS)) {
//
//                }
//                return false;
//            }
//        });



/*
        //TODO filter
        //searchView = (SearchView) findViewById(R.id.sv_search);
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

    /*private void getCurrentSearchIllusions(BaseAdapter adapter) {
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