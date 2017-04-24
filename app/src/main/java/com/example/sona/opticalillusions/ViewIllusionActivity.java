package com.example.sona.opticalillusions;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.Stack;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ViewIllusionActivity extends AppCompatActivity {
    private Illusion currentIllusion;
    private ImageView imageView;
    private TextView category;
    private TextView title;
    private ImageButton addToFavourites;
    private Stack stack;
    private GridElementAdapter adapter;
    private HorizontalGridView horizontalGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_illusion);

        stack = new Stack();
        final Illusion illusion = getIntent().getExtras().getParcelable("item");
        currentIllusion = illusion;

        final Realm realm;
        Realm.init(this);
        final RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Log.v("lolol", toolbar.toString());
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }



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
        category = (TextView) findViewById(R.id.tv_category);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Giorgio.ttf");
        title.setTypeface(type);
        category.setTypeface(type);

        final VideoView videoView = (VideoView) findViewById(R.id.vv_video);

        imageView = (ImageView) findViewById(R.id.iv_view_illusion);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView.getVisibility() == View.VISIBLE) {
                    imageView.setVisibility(View.GONE);
                    videoView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.VISIBLE);
                    videoView.setVisibility(View.GONE);
                }
            }
        });

        final TextView description = (TextView) findViewById(R.id.tv_description);

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (imageView.getVisibility() == View.VISIBLE || videoView.getVisibility() == View.VISIBLE) {
                    imageView.setVisibility(View.GONE);
                    videoView.setVisibility(View.GONE);
                    description.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.VISIBLE);
                    videoView.setVisibility(View.GONE);
                }
                return true;
            }
        });

        ImageButton back = (ImageButton) findViewById(R.id.b_last_viewed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (findViewById(R.id.gv_favourites_grid) != null) {
                    GridView gv = (GridView) findViewById(R.id.gv_favourites_grid);
                    gv.invalidateViews();
                }

                if (stack.isEmpty()) {
                    finish();
                } else {
                    updateActivity((Illusion) stack.pop());
                }
            }
        });

        ImageButton toAll = (ImageButton) findViewById(R.id.b_all_illusions);
        toAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                stack.clear();
            }
        });

        Log.v("FAV1", String.valueOf(illusion.isFavourite()));

        addToFavourites = (ImageButton) findViewById(R.id.b_to_favourites);
        addToFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //TODO

                realm.beginTransaction();
                if (currentIllusion.isFavourite()) {
                    ((ImageButton) v).setImageResource(R.drawable.ic_favourite);
                    currentIllusion.setFavourite(false);
                } else {
                    ((ImageButton) v).setImageResource(R.drawable.ic_unfavourite);
                    currentIllusion.setFavourite(true);
                }
                realm.copyToRealmOrUpdate(currentIllusion);
                realm.commitTransaction();
            }
        });

        horizontalGridView = (HorizontalGridView) findViewById(R.id.gv_small_preview);
        adapter = new GridElementAdapter(this, realm.where(Illusion.class).findAll());
        horizontalGridView.setAdapter(adapter);

        updateActivity(currentIllusion);

    }

    public void updateActivity(Illusion illusion) {
        currentIllusion = illusion;
        title.setText(illusion.getName());
        category.setText(illusion.getCategory());
        imageView.setImageResource(illusion.getPicture());
        if (illusion.isFavourite()) {
            addToFavourites.setImageResource(R.drawable.ic_unfavourite);
        } else {
            addToFavourites.setImageResource(R.drawable.ic_favourite);
        }
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (adapter.getItem(i).getName().equals(illusion.getName())) {
                horizontalGridView.smoothScrollToPosition(i);
                break;
            }
        }
    }

    public void addIllusionToStack() {
        stack.push(currentIllusion);
    }
}