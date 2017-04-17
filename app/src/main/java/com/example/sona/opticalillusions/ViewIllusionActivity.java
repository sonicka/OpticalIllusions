package com.example.sona.opticalillusions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sona.opticalillusions.model.FavouriteIllusion;
import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ViewIllusionActivity extends AppCompatActivity {
    private ArrayList<Object> list;
    final Object item = getIntent().getExtras().get("item");
    final String getClass = getIntent().getStringExtra("class");

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

//        final Object item = getIntent().getExtras().get("item");
//        final String getClass = getIntent().getStringExtra("class");


        if (getClass.equals("Illusion")) {
            Illusion illusion = (Illusion) item;
            //ArrayList<Illusion> list;
            //list = helper.dbToList(realm.where(Illusion.class).findAll());

            //Log.v("HOHO", String.valueOf(list.size()));
            //Log.v("HOHO", list.toString());

            TextView title = (TextView) findViewById(R.id.tv_title);
            title.setText(illusion.getName());

            TextView category = (TextView) findViewById(R.id.tv_category);
            category.setText(illusion.getCategory());

            ImageView imageView = (ImageView) findViewById(R.id.iv_view_illusion);
            imageView.setImageResource(illusion.getPicture());
        } else if (getClass.equals("FavouriteIllusion")) {
            FavouriteIllusion illusion = (FavouriteIllusion) item;
            //ArrayList<FavouriteIllusion> list;
            //list = helper.dbFavouritesToList(realm.where(FavouriteIllusion.class).findAll());

//            Log.v("HOHO", String.valueOf(list.size()));
//            Log.v("HOHO", list.toString());

            TextView title = (TextView) findViewById(R.id.tv_title);
            title.setText(illusion.getName());

            TextView category = (TextView) findViewById(R.id.tv_category);
            category.setText(illusion.getCategory());

            ImageView imageView = (ImageView) findViewById(R.id.iv_view_illusion);
            imageView.setImageResource(illusion.getPicture());
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
                //TODO or illusionlistactivity or favouritesactivity
                startActivity(new Intent(ViewIllusionActivity.this, AllIllusionsActivity.class));
            }
        });

        Button addToFavourites = (Button) findViewById(R.id.b_to_favourites);
        addToFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getClass.equals("Illusion")) {
                    helper.save((Illusion) item);
                } else if (getClass.equals("FavouriteIllusion")) {
                    helper.removeFromFavourites((FavouriteIllusion) item);
                }
            }
        });

        HorizontalGridView horizontalGridView = (HorizontalGridView) findViewById(R.id.gv_small_preview);
        GridElementAdapter adapter = new GridElementAdapter(this, realm.where(Illusion.class).findAll());
        horizontalGridView.setAdapter(adapter);
    }

    public Object getIllusionObject() {
        return item;
    }

    public String getIllusionClass() {
        return getClass;
    }

}