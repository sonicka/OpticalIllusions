package com.example.sona.opticalillusions;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.sona.opticalillusions.R.id.listView;

public class IllusionsListActivity extends AppCompatActivity {

    Button favouritesButton;
    Button switchViewButton;
    Button searchButton;

    private ListView listView;
    ArrayList<Illusion> illusions = new ArrayList<>();
    Illusion cafewall = new Illusion("Cafe wall", R.drawable.cafewall);
    Illusion hering = new Illusion("Hering illusion", R.drawable.hering);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illusions_list);

        illusions.add(cafewall);
        illusions.add(hering);

        String[] items = {illusions.get(0).getName(), illusions.get(1).getName()};


        listView = (ListView) findViewById(R.id.id_list_view);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        listView.setAdapter(adapter);

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
}
