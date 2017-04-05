package com.example.sona.opticalillusions;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class IllusionsGridActivity extends Activity {
    protected GridView gridView;
    protected ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illusions_grid);

        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new ImageAdapter(this);
        gridView.setAdapter(adapter);
    }
}