package com.example.sona.opticalillusions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
/**
 * Created by Soňa on 04-Apr-17.
 */

public class IllusionsGridActivity extends AppCompatActivity implements GridViewAdapter.GridItemClickListener {
    Button favouritesButton;
    Button switchViewButton;
    Button searchButton;
    private GridViewAdapter adapter;
    private static final int NUM_GRID_ITEMS = 100;
    private RecyclerView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illusions_grid);

        //gridView = new GridView(this);
       // gridView = (GridView) findViewById(R.id.gridView);
       // adapter = new ImageAdapter(this);
       // gridView.setAdapter(adapter);
        view = (RecyclerView) findViewById(R.id.rv_illusion_grid);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        view.setLayoutManager(layoutManager);
        view.setHasFixedSize(true);
        adapter = new GridViewAdapter(NUM_GRID_ITEMS, this);
        view.setAdapter(adapter);

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

    @Override
    public void onListItemClick(int clickedItemIndex) {
        startActivity(new Intent(IllusionsGridActivity.this, MainActivity.class));
    }
}