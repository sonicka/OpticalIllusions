package com.example.sona.opticalillusions;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class SearchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        handleIntent(getIntent());
    }

    public void onNewIntent (Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    public void onListItemClick (ListView listView, View view, int positon, long id) {
        // TODO detail activity for clicked entry
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    private void doSearch(String query) {
        // get a Cursor, prepare the ListAdapter
        // and set it
        // TODO
    }
}
