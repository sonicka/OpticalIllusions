package com.example.sona.opticalillusions;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sona.opticalillusions.model.Illusion;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Main activity ran when app is opened.
 * Created by Soňa on 04-Apr-17.
 */

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    private boolean backClickOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);

        RealmHelper realmHelper = new RealmHelper(realm);
        ArrayList<Illusion> listFromXml;

        XmlResourceParser parser = getResources().getXml(R.xml.illusions);
        XmlParser mParser = new XmlParser();
        try {
            mParser.processData(parser);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        parser.close();

        listFromXml = mParser.getList();
        Log.v("lalala", listFromXml.toString());
        Log.v("lalala", listFromXml.get(0).toString());
        Log.v("lalala", String.valueOf(listFromXml.size()));


        setPictures(listFromXml);
        realmHelper.listToDb(listFromXml);

        RealmResults<Illusion> list = realm.where(Illusion.class).findAll();

        Log.v("LISTIK", list.toString());

        ImageButton startButton = (ImageButton) findViewById(R.id.buttonStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AllIllusionsActivity.class));
            }
        });
        ImageButton infoButton = (ImageButton) findViewById(R.id.buttonInfo);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InfoActivity.class));
            }
        });

        if (new File(config.getPath()).exists()) {
            Log.v("DATABASE", ">>>>>DB EXISTS YAYYY<<<<<");
        } else {
            Log.e("DATABASE", "<<<<<<DB DOES NOT EXIST BOOOO>>>>>");
        }
    }

    private void setPictures(ArrayList<Illusion> listFromXml) {
        for (Illusion i : listFromXml) {
            switch (i.getName()) {
                case "Cafe Wall Illusion":
                    i.setThumbnailAndPicture(R.drawable.thumb_cafewall, R.drawable.big_cafewall);
                    break;
                case "Color Difference 1":
                    i.setThumbnailAndPicture(R.drawable.thumb_colordifference1, R.drawable.big_colordifference1);
                    break;
                case "Color Difference 2":
                    i.setThumbnailAndPicture(R.drawable.thumb_colordifference2, R.drawable.big_colordifference2);
                    break;
                case "Ebbinghaus Illusion":
                    i.setThumbnailAndPicture(R.drawable.thumb_ebbinghaus, R.drawable.big_ebbinghaus);
                    break;
                case "Hering Illusion":
                    i.setThumbnailAndPicture(R.drawable.thumb_hering, R.drawable.big_hering);
                    break;
                case "Hermann Grid Illusion":
                    i.setThumbnailAndPicture(R.drawable.thumb_hermanngrid, R.drawable.big_hermanngrid);
                    break;
                case "Illusory Contours":
                    i.setThumbnailAndPicture(R.drawable.thumb_illusorycontours, R.drawable.big_illusorycontours);
                    break;
                case "Impossible Stairs":
                    i.setThumbnailAndPicture(R.drawable.thumb_stairs, R.drawable.big_stairs);
                    break;
                case "Impossible Triangle":
                    i.setThumbnailAndPicture(R.drawable.thumb_triangle, R.drawable.big_triangle);
                    break;
                case "Jastrow Illusion":
                    i.setThumbnailAndPicture(R.drawable.thumb_jastrow, R.drawable.big_jastrow);
                    break;
                case "Motion Illusion":
                    i.setThumbnailAndPicture(R.drawable.thumb_motion, R.drawable.big_motion);
                    break;
                case "Munker Illusion":
                    i.setThumbnailAndPicture(R.drawable.thumb_munker, R.drawable.big_munker);
                    break;
                case "Müller-Lyer Illusion":
                    i.setThumbnailAndPicture(R.drawable.thumb_mullerlyer, R.drawable.big_mullerlyer);
                    break;
                case "Necker Cube":
                    i.setThumbnailAndPicture(R.drawable.thumb_neckercube, R.drawable.big_neckercube);
                    break;
                case "Oppel-Kundt Illusion":
                    i.setThumbnailAndPicture(R.drawable.thumb_oppelkundt, R.drawable.big_oppelkundt);
                    break;
                case "Poggendorf Illusion":
                    i.setThumbnailAndPicture(R.drawable.thumb_poggendorf, R.drawable.big_poggendorf);
                    break;
                case "Ponzo Illusion":
                    i.setThumbnailAndPicture(R.drawable.thumb_ponzo, R.drawable.big_ponzo);
                    break;
                case "Rubin Vase Illusion":
                    i.setThumbnailAndPicture(R.drawable.thumb_rubinvase, R.drawable.big_rubinvase);
                    break;
                case "Vertical-horizontal Illusion":
                    i.setThumbnailAndPicture(R.drawable.thumb_verticalhorizontal, R.drawable.big_verticalhorizontal);
                    break;
                case "Zollner Illusion":
                    i.setThumbnailAndPicture(R.drawable.thumb_zollner, R.drawable.big_zollner);
                    break;
            }
        }
    }

    /**
     * Closes Realm database.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    /**
     * Shows toast when back pressed in the main activity and closes app on double press of back button.
     */
    @Override
    public void onBackPressed() {
        if (backClickOnce) {
            finishAffinity();
        } else {
            backClickOnce = true;
            Toast.makeText(this, R.string.click_back_again_to_exit, Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backClickOnce = false;
                }
            }, 2000);
        }
    }
}
