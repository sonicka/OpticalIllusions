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

import java.util.Stack;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ViewIllusionActivity extends AppCompatActivity {
    private Illusion illusion;
    private Illusion topIllusion;
    private ImageView imageView;
    private TextView category;
    private TextView title;
    private Stack<Illusion> stack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_illusion);

        stack = new Stack();
        illusion = getIntent().getExtras().getParcelable("item");
        stack.push(illusion);

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
            public void onClick(View v) { //todo
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //ArrayList<Illusion> list;
        //list = helper.dbToList(realm.where(Illusion.class).findAll());

        //Log.v("HOHO", String.valueOf(list.size()));
        //Log.v("HOHO", list.toString());

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
                if (stack.isEmpty()) {
                    finish();
                } else {
                    if (stack.peek() == topIllusion) {
                        stack.pop();
                        updateActivity(stack.pop());
                    } else {
                        updateActivity(stack.pop());
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

        Log.v("FAV", String.valueOf(illusion.isFavourite()));


        Button addToFavourites = (Button) findViewById(R.id.b_to_favourites);
        addToFavourites.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!illusion.isFavourite()) {
                    illusion.setFavourite(true);
                    Log.v("FAV", String.valueOf(illusion.isFavourite()));
                } else {
                    illusion.setFavourite(false);
                }
                Log.v("FAV", String.valueOf(illusion.isFavourite()));

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

    public Stack<Illusion> getStack() {
        return stack;
    }

    public Illusion setTopIllusion(Illusion illusion) {
        return topIllusion = illusion;
    }
}