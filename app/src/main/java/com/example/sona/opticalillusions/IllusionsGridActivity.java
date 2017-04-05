package com.example.sona.opticalillusions;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.GridView;

public class IllusionsGridActivity extends Activity {
    protected GridView gridView;
    protected ImageAdapter adapter;
    protected BottomNavigationView bottomNavigationView;
    protected Fragment favourites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illusions_grid);

        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new ImageAdapter(this);
        gridView.setAdapter(adapter);
        bottomNavigationView = new BottomNavigationView(this);
        favourites = new Fragment(); //TODO no idea how this works
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_favourites) {
                    //TODO show fragment list of favourites
                } else if (id == R.id.action_change_to_list) {
                    startActivity(new Intent(IllusionsGridActivity.this, IllusionsCategoriesActivity.class)); //TODO doing nothing lol
                } else {
                    //TODO search among all illusions
                }
                // handle desired action here
                // One possibility of action is to replace the contents above the nav bar
                // return true if you want the item to be displayed as the selected item
                return false;
            }
        });
    }
}