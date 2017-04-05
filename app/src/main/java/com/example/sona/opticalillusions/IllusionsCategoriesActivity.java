package com.example.sona.opticalillusions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class IllusionsCategoriesActivity extends AppCompatActivity {

    ListAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illusions_categories);
        adapter = new ListAdapter(this);
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
