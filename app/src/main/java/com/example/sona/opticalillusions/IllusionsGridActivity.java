package com.example.sona.opticalillusions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Soňa on 04-Apr-17.
 */

public class IllusionsGridActivity extends Activity /*implements SearchView.OnQueryTextListener */{
    private Button searchButton;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illusions_grid);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm realm = Realm.getInstance(config);

        RealmHelper realmHelper = new RealmHelper(realm);
        ArrayList<Illusion> listIllusions = realmHelper.dbToList(realm.where(Illusion.class).findAll());

        imageAdapter = new ImageAdapter(this, listIllusions);
        GridView gridView = (GridView) findViewById(R.id.gv_illusion_grid);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new OnIllusionClickListener(this));

        Button favouritesButton = (Button) findViewById(R.id.b_favourites);
        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IllusionsGridActivity.this, FavouritesActivity.class));
            }
        });

        Button switchViewButton = (Button) findViewById(R.id.b_switch_to_list);
        switchViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IllusionsGridActivity.this, IllusionsListActivity.class));
            }
        });

//        TODO search initialization
//          SearchView searchView = (SearchView) findViewById(R.id.sv_search);
//            searchView = new SearchView(this);
//          SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//          searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
          //searchView.setIconifiedByDefault(false);
//        SearchManager searchManager = (SearchManager)
//                getSystemService(Context.SEARCH_SERVICE);
//        searchMenuItem = menu.findItem(R.id.search);
//        searchView = (SearchView) new SearchView(R.id.sv_search);
//
//        searchView.setSearchableInfo(searchManager.
//                getSearchableInfo(getComponentName()));
//        searchView.setSubmitButtonEnabled(true);
//        searchView.setOnQueryTextListener(this);

    }

/*    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        imageAdapter.getFilter().filter(newText);

        // use to enable search view popup text
//        if (TextUtils.isEmpty(newText)) {
//            friendListView.clearTextFilter();
//        }
//        else {
//            friendListView.setFilterText(newText.toString());
//        }

        return true;
    }*/
}