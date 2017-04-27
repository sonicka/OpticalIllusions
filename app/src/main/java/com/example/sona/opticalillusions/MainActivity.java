package com.example.sona.opticalillusions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.sona.opticalillusions.model.Illusion;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Soňa on 04-Apr-17.
 */

public class MainActivity extends AppCompatActivity {

    private Realm realm;

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
        if (realm.isEmpty()) {
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
        }

        RealmResults<Illusion> list = realm.where(Illusion.class).findAll();

        Log.v("LIST", list.toString());

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

    //    private void refreshViews () {
    //        RealmResults<Illusion> r = realm.where(Illusion.class).findAll();
    //    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    Illusion cafewall           = new Illusion("Cafe wall illusion", "Geometric Illusions", "", R.drawable.thumb_cafe_wall, R.drawable.cafewall, "http://marekscholtz.esy.es/cafe_wall.mp4", false);
    Illusion colordiff1         = new Illusion("Color difference 1", "Color Illusions", "", R.drawable.thumbcolordifference01, R.drawable.colordifference01, "", false);
    Illusion colordiff2         = new Illusion("Color difference 2", "Color Illusions", "", R.drawable.thumbcolordifference02, R.drawable.colordifference02, "", false);
    Illusion ebbinghaus         = new Illusion("Ebbinghaus illusion", "Geometric Illusions", "", R.drawable.thumbebbinghaus, R.drawable.ebbinghaus, "", false);
    Illusion hering             = new Illusion("Hering illusion", "Geometric Illusions", "", R.drawable.thumbhering, R.drawable.hering, "", false);
    Illusion hermanngrid        = new Illusion("Hermann grid illusion", "Geometric Illusions", "", R.drawable.thumbhermanngrid, R.drawable.hermanngrid, "", false);
    Illusion illusorycontours   = new Illusion("Illusory contours", "Geometric Illusions", "", R.drawable.thumb_illusory_contours, R.drawable.illusory_contours01, "", false);
    Illusion impossiblestairs   = new Illusion("Impossible stairs", "3D illusions", "", R.drawable.thumb_impossible_stairs, R.drawable.impossible_stairs, "", false);
    Illusion impossibletriangle = new Illusion("Impossible triangle", "3D illusions", "", R.drawable.thumb_impossible_triangle, R.drawable.impossible_triangle, "https://drive.google.com/open?id=0BxwduOqqcuAMa0kxYnZuQUphdkE", false);
    Illusion jastrow            = new Illusion("Jastrow illusion", "Geometric Illusions", "", R.drawable.thumb_jastrow, R.drawable.jastrow01, "", false);
    Illusion motion1            = new Illusion("Motion illusion 1", "Motion illusion", "", R.drawable.thumb_motion1, R.drawable.motion1, "", false);
    Illusion motion2            = new Illusion("Motion illusion 2", "Motion illusion", "", R.drawable.thumb_motion2, R.drawable.motion2, "", false);
    Illusion mullerlyer         = new Illusion("Müller-Lyer illusion", "Geometric Illusions", "", R.drawable.thumb_muller_lyer, R.drawable.muller_lyer, "", false);
    Illusion neckercube         = new Illusion("Necker cube", "Geometric Illusions", "", R.drawable.thumb_necker_cube, R.drawable.necker_cube, "", false);
    Illusion oppelkundt         = new Illusion("Oppel-Kundt illusion", "Geometric Illusions", "", R.drawable.thumb_oppel_kundt, R.drawable.oppel_kundt, "", false);
    Illusion poggendorf         = new Illusion("Poggendorf illusion", "Geometric Illusions", "", R.drawable.thumb_poggendorf, R.drawable.poggendorf, "", false);
    Illusion ponzo              = new Illusion("Ponzo illusion", "Geometric Illusions", "", R.drawable.thumb_ponzo, R.drawable.ponzo, "", false);
    Illusion rubinvase          = new Illusion("Rubin vase", "3D illusions", "", R.drawable.thumb_rubin_vase, R.drawable.rubin_vase, "", false);
    Illusion verticalhorizontal = new Illusion("Vertical-horizontal illusion", "Geometric Illusions", "", R.drawable.thumb_vertical_horizontal, R.drawable.vertical_horizontal, "", false);
    Illusion zollner            = new Illusion("Zöllner illusion", "Geometric Illusions", "", R.drawable.thumb_zollner, R.drawable.zollner, "", false);
}
