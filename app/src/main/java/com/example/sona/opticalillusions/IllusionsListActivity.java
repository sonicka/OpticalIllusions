package com.example.sona.opticalillusions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;

public class IllusionsListActivity extends AppCompatActivity implements ListViewAdapter.ListItemClickListener {

    private static final int NUM_LIST_ITEMS = 100;
    private ListViewAdapter adapter;
    private RecyclerView view;

    Button favouritesButton;
    Button switchViewButton;
    Button searchButton;

    ArrayList<Illusion> illusions = new ArrayList<>();
    Illusion cafewall = new Illusion("Cafe wall", R.drawable.cafewall);
    Illusion hering = new Illusion("Hering illusion", R.drawable.hering);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illusions_list);

        illusions.add(cafewall);
        illusions.add(hering);

        //String[] items = {illusions.get(0).getName(), illusions.get(1).getName()};

        view = (RecyclerView) findViewById(R.id.rv_illusion_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        view.setLayoutManager(layoutManager);
        view.setHasFixedSize(true);
        adapter = new ListViewAdapter(NUM_LIST_ITEMS, this);
        view.setAdapter(adapter);

        favouritesButton = (Button)findViewById(R.id.buttonFavourites);
        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IllusionsListActivity.this, FavouritesActivity.class));
            }
        });
        switchViewButton = (Button)findViewById(R.id.buttonSwitchToGrid);
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
    public void onListItemClick(int clickedItemIndex) {
        startActivity(new Intent(IllusionsListActivity.this, MainActivity.class));
    }
}