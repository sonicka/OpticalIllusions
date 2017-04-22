package com.example.sona.opticalillusions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.List;
import java.util.Stack;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ViewIllusionActivity extends AppCompatActivity {
    private Illusion illusion;
    private Illusion currentIllusion;
    private Illusion topIllusion;
    private ImageView imageView;
    private TextView category;
    private TextView title;
    private Stack stack;
    private boolean onlyOneItemInStack = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_illusion);

        stack = new Stack();
        illusion = getIntent().getExtras().getParcelable("item");
        currentIllusion = illusion;
        stack.push(illusion);

        final Realm realm;
        Realm.init(this);
        final RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);

        ImageView logo = (ImageView) findViewById(R.id.ib_logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //todo
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        title = (TextView) findViewById(R.id.tv_title);
        title.setText(illusion.getName());

        category = (TextView) findViewById(R.id.tv_category);
        category.setText(illusion.getCategory());

        imageView = (ImageView) findViewById(R.id.iv_view_illusion);
        imageView.setImageResource(illusion.getPicture());

        Button back = (Button) findViewById(R.id.b_last_viewed);
        back.setText("back");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (findViewById(R.id.gv_favourites_grid) != null) {
                    GridView gv = (GridView) findViewById(R.id.gv_favourites_grid);
                    gv.invalidateViews();
                }

                if (stack.isEmpty() || onlyOneItemInStack) {
                    finish();
                } else {
                    if (stack.peek() == topIllusion) {
                        stack.pop();
                        updateActivity((Illusion) stack.pop());
                    } else {
                        updateActivity((Illusion) stack.pop());
                    }
                }
            }
        });

        Button toAll = (Button) findViewById(R.id.b_all_illusions);
        toAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                stack.clear();
            }
        });

        Log.v("FAV1", String.valueOf(illusion.isFavourite()));

        Button addToFavourites = (Button) findViewById(R.id.b_to_favourites);
        addToFavourites.setText("Favourite");
        addToFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //TODO


                if (FavouritesActivity.getGridView() != null && FavouritesActivity.getAdapter() != null) {
                    FavouritesActivity.getAdapter().notifyDataSetChanged();
                    FavouritesActivity.getGridView().invalidateViews();
                    FavouritesActivity.getGridView().setAdapter(FavouritesActivity.getAdapter());
                }

                Log.v("cau", currentIllusion.toString());
                realm.beginTransaction();

                if (!currentIllusion.isFavourite()) {
                    currentIllusion.setFavourite(true);
                } else {
                    currentIllusion.setFavourite(false);
                }

                Log.v("cau", currentIllusion.toString());
                realm.copyToRealmOrUpdate(currentIllusion);
                realm.commitTransaction();
                Log.v("FAV3", String.valueOf(currentIllusion.isFavourite()));
                List<Illusion> fav = realm.where(Illusion.class).equalTo("isFavourite", true).findAll();
                Log.v("FAVALL", String.valueOf(fav.size()));
                Log.v("FAVALL", fav.toString());
            }
        });

        HorizontalGridView horizontalGridView = (HorizontalGridView) findViewById(R.id.gv_small_preview);
        GridElementAdapter adapter = new GridElementAdapter(this, realm.where(Illusion.class).findAll());
        horizontalGridView.setAdapter(adapter);
    }

    public void updateActivity(Illusion illusion) {
        title.setText(illusion.getName());
        category.setText(illusion.getCategory());
        imageView.setImageResource(illusion.getPicture());
    }

    public Stack getStack() {
        return stack;
    }

    public void setOnlyOneItemInStack(boolean onlyOneItemInStack) {
        this.onlyOneItemInStack = onlyOneItemInStack;
    }

    public Illusion setTopIllusion(Illusion illusion) {
        return topIllusion = illusion;
    }

    public void setCurrentIllusion(Illusion currentIllusion) {
        this.currentIllusion = currentIllusion;
    }
}