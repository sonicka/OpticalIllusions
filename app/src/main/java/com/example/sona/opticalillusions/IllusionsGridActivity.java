package com.example.sona.opticalillusions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

public class IllusionsGridActivity extends AppCompatActivity {
    protected GridView gridView;
    protected ImageAdapter adapter;
    Button favouritesButton;
    Button switchViewButton;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illusions_grid);

        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new ImageAdapter(this);
        gridView.setAdapter(adapter);

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
}