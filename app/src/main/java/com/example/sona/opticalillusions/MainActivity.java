package com.example.sona.opticalillusions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.sona.opticalillusions.model.Illusion;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Soňa on 04-Apr-17.
 */

public class MainActivity extends AppCompatActivity {

    //ArrayList<Illusion> allIllusions;
    private RealmHelper realmHelper;
    private Realm realm;
    Button startButton;
    Button infoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Realm database initialization
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);

        realmHelper = new RealmHelper(realm);
        realmHelper.save(cafewall);
        realmHelper.save(colordiff1);
        realmHelper.save(colordiff2);
        realmHelper.save(ebbinghaus);
        realmHelper.save(hering);
        realmHelper.save(hermanngrid);
        realmHelper.save(illusorycontours);
        realmHelper.save(impossiblestairs);
        realmHelper.save(impossibletriangle);
        realmHelper.save(jastrow);
        realmHelper.save(motion1);
        realmHelper.save(motion2);
        realmHelper.save(mullerlyer);
        realmHelper.save(neckercube);
        realmHelper.save(oppelkundt);
        realmHelper.save(poggendorf);
        realmHelper.save(ponzo);
        realmHelper.save(rubinvase);
        realmHelper.save(verticalhorizontal);
        realmHelper.save(zollner);

//        realm.beginTransaction();
//        realm.copyToRealmOrUpdate(cafewall);
//        realm.copyToRealmOrUpdate(colordiff1);
//        realm.copyToRealmOrUpdate(colordiff2);
//        realm.copyToRealmOrUpdate(ebbinghaus);
//        realm.copyToRealmOrUpdate(hering);
//        realm.copyToRealmOrUpdate(hermanngrid);
//        realm.copyToRealmOrUpdate(illusorycontours);
//        realm.copyToRealmOrUpdate(impossiblestairs);
//        realm.copyToRealmOrUpdate(impossibletriangle);
//        realm.copyToRealmOrUpdate(jastrow);
//        realm.copyToRealmOrUpdate(motion1);
//        realm.copyToRealmOrUpdate(motion2);
//        realm.copyToRealmOrUpdate(mullerlyer);
//        realm.copyToRealmOrUpdate(neckercube);
//        realm.copyToRealmOrUpdate(oppelkundt);
//        realm.copyToRealmOrUpdate(poggendorf);
//        realm.copyToRealmOrUpdate(ponzo);
//        realm.copyToRealmOrUpdate(rubinvase);
//        realm.copyToRealmOrUpdate(verticalhorizontal);
//        realm.copyToRealmOrUpdate(zollner);
//        realm.commitTransaction();

        RealmResults<Illusion> list = realm.where(Illusion.class).findAll();
        Log.v("ADDED", String.valueOf(list.size()));
        Log.v("ADDED", list.toString());

        startButton = (Button) findViewById(R.id.buttonStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, IllusionsListActivity.class));
            }
        });
        infoButton = (Button) findViewById(R.id.buttonInfo);
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
    //    private void refreshViews () {
//        RealmResults<Illusion> r = realm.where(Illusion.class).findAll();
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    Illusion cafewall           = new Illusion(1, "Cafe wall illusion", "", "", R.drawable.thumb_cafe_wall, R.drawable.cafewall, "");
    Illusion colordiff1         = new Illusion(2, "Color difference 1", "", "", R.drawable.thumbcolordifference01, R.drawable.colordifference01, "");
    Illusion colordiff2         = new Illusion(3, "Color difference 2", "", "", R.drawable.thumbcolordifference02, R.drawable.colordifference02, "");
    Illusion ebbinghaus         = new Illusion(4, "Ebbinghaus illusion", "", "", R.drawable.thumbebbinghaus, R.drawable.ebbinghaus, "");
    Illusion hering             = new Illusion(5, "Hering illusion", "", "", R.drawable.thumbhering, R.drawable.hering, "");
    Illusion hermanngrid        = new Illusion(6, "Hermann grid illusion", "", "", R.drawable.thumbhermanngrid, R.drawable.hermanngrid, "");
    Illusion illusorycontours   = new Illusion(7, "Illusory contours", "", "", R.drawable.thumb_illusory_contours, R.drawable.illusory_contours01, "");
    Illusion impossiblestairs   = new Illusion(8, "Impossible stairs", "", "", R.drawable.thumb_impossible_stairs, R.drawable.impossible_stairs, "");
    Illusion impossibletriangle = new Illusion(9, "Impossible triangle", "", "", R.drawable.thumb_impossible_triangle, R.drawable.impossible_triangle, "");
    Illusion jastrow            = new Illusion(10, "Jastrow illusion", "", "", R.drawable.thumb_jastrow, R.drawable.jastrow01, "");
    Illusion motion1            = new Illusion(11, "Motion illusion 1", "", "", R.drawable.thumb_motion1, R.drawable.motion1, "");
    Illusion motion2            = new Illusion(12, "Motion illusion 2", "", "", R.drawable.thumb_motion2, R.drawable.motion2, "");
    Illusion mullerlyer         = new Illusion(13, "Muller-Lyer illusion", "", "", R.drawable.thumb_muller_lyer, R.drawable.muller_lyer, "");
    Illusion neckercube         = new Illusion(14, "Necker cube", "", "", R.drawable.thumb_necker_cube, R.drawable.necker_cube, "");
    Illusion oppelkundt         = new Illusion(15, "Oppel-Kundt illusion", "", "", R.drawable.thumb_oppel_kundt, R.drawable.oppel_kundt, "");
    Illusion poggendorf         = new Illusion(16, "Poggendorf illusion", "", "", R.drawable.thumb_poggendorf, R.drawable.poggendorf, "");
    Illusion ponzo              = new Illusion(17, "Ponzo illusion", "", "", R.drawable.thumb_ponzo, R.drawable.ponzo, "");
    Illusion rubinvase          = new Illusion(18, "Rubin vase", "", "", R.drawable.thumb_rubin_vase, R.drawable.rubin_vase, "");
    Illusion verticalhorizontal = new Illusion(19, "Vertical-horizontal illusion", "", "", R.drawable.thumb_vertical_horizontal, R.drawable.vertical_horizontal, "");
    Illusion zollner            = new Illusion(20, "Zollner illusion", "", "", R.drawable.thumb_zollner, R.drawable.zollner, "");

}
