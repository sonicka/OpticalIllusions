package com.example.sona.opticalillusions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;
import android.widget.GridView;

public class FavouritesActivity extends AppCompatActivity {

    GridView gridView;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        gridView = (GridView) findViewById(R.id.gridView2);
        adapter = new ImageAdapter(this);
        gridView.setAdapter(adapter);
    }
}
