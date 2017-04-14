package com.example.sona.opticalillusions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ViewIllusionActivity extends AppCompatActivity {
    private ArrayList<Illusion> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_illusion);

        final Realm realm;
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);
        final RealmHelper helper = new RealmHelper(realm);

        ImageView logo = (ImageView) findViewById(R.id.ib_logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewIllusionActivity.this, MainActivity.class));
            }
        });

        final Illusion item = (Illusion) getIntent().getExtras().get("item");
        list = helper.dbToList(realm.where(Illusion.class).findAll());

        Log.v("HOHO", String.valueOf(list.size()));
        Log.v("HOHO", list.toString());

        try {
            TextView title = (TextView) findViewById(R.id.tv_title);
            title.setText(item.getName());

            TextView category = (TextView) findViewById(R.id.tv_category);
            category.setText(item.getCategory());

            ImageView imageView = (ImageView) findViewById(R.id.iv_view_illusion);
            imageView.setImageResource(item.getPicture());
        } catch (NullPointerException e) {
            //TODO
        }
        Button back = (Button) findViewById(R.id.b_last_viewed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button toAll = (Button) findViewById(R.id.b_all_illusions);
        toAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        Button addToFavourites = (Button) findViewById(R.id.b_to_favourites);
        addToFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.save(item);
            }
        });

        HorizontalGridView horizontalGridView = (HorizontalGridView) findViewById(R.id.gv_small_preview);
        GridElementAdapter adapter = new GridElementAdapter(this, realm.where(Illusion.class).findAll());
        horizontalGridView.setAdapter(adapter);


//        ImageAdapter adapter = new ImageAdapter(this, list);
//        GridView gridView = (GridView) findViewById(R.id.gv_small_preview);
//        gridView.setAdapter(adapter);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Illusion i = (Illusion) parent.getItemAtPosition(position);
//                Intent intent = new Intent(ViewIllusionActivity.this, ViewIllusionActivity.class);
//                intent.putExtra("item", i);
//                startActivity(intent);
//            }
//        });
    }
}
