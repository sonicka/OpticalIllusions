package com.example.sona.opticalillusions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ViewIllusionActivity extends AppCompatActivity {

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

        final Item item = (Item) getIntent().getExtras().get("item");

        try {
            TextView title = (TextView) findViewById(R.id.tv_title);
            title.setText(item.name);

            TextView category = (TextView) findViewById(R.id.tv_category);
            category.setText(item.category);

            ImageView imageView = (ImageView) findViewById(R.id.iv_view_illusion);
            imageView.setImageResource(item.picture);
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
                helper.saveItem(item);
            }
        });
    }
}
