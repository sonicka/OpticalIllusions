package com.example.sona.opticalillusions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.GridView;

public class FavouritesActivity extends AppCompatActivity {

    GridView gridView;
    ImageAdapter adapter;
    Button removeButton;
    Button switchViewButton;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        gridView = (GridView) findViewById(R.id.gridView2);
        adapter = new ImageAdapter(this);
        gridView.setAdapter(adapter);

        removeButton = (Button)findViewById(R.id.buttonRemove);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO remove
            }
        });
        switchViewButton = (Button)findViewById(R.id.buttonSwitchToGrid);
        switchViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavouritesActivity.this, IllusionsGridActivity.class));
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
}
