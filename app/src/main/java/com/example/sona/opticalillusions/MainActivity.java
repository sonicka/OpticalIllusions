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

        RealmResults<Illusion> list = realm.where(Illusion.class).findAll();

        Button startButton = (Button) findViewById(R.id.buttonStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, IllusionsGridActivity.class));
            }
        });
        Button infoButton = (Button) findViewById(R.id.buttonInfo);
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

    Illusion cafewall           = new Illusion(1, "Cafe wall illusion", "Geometric Illusions", "", R.drawable.thumb_cafe_wall, R.drawable.cafewall, "");
    Illusion colordiff1         = new Illusion(2, "Color difference 1", "Color Illusions", "", R.drawable.thumbcolordifference01, R.drawable.colordifference01, "");
    Illusion colordiff2         = new Illusion(3, "Color difference 2", "Color Illusions", "", R.drawable.thumbcolordifference02, R.drawable.colordifference02, "");
    Illusion ebbinghaus         = new Illusion(4, "Ebbinghaus illusion", "Geometric Illusions", "", R.drawable.thumbebbinghaus, R.drawable.ebbinghaus, "");
    Illusion hering             = new Illusion(5, "Hering illusion", "Geometric Illusions", "", R.drawable.thumbhering, R.drawable.hering, "");
    Illusion hermanngrid        = new Illusion(6, "Hermann grid illusion", "Geometric Illusions", "", R.drawable.thumbhermanngrid, R.drawable.hermanngrid, "");
    Illusion illusorycontours   = new Illusion(7, "Illusory contours", "Geometric Illusions", "", R.drawable.thumb_illusory_contours, R.drawable.illusory_contours01, "");
    Illusion impossiblestairs   = new Illusion(8, "Impossible stairs", "3D illusions", "", R.drawable.thumb_impossible_stairs, R.drawable.impossible_stairs, "");
    Illusion impossibletriangle = new Illusion(9, "Impossible triangle", "3D illusions", "", R.drawable.thumb_impossible_triangle, R.drawable.impossible_triangle, "");
    Illusion jastrow            = new Illusion(10, "Jastrow illusion", "Geometric Illusions", "", R.drawable.thumb_jastrow, R.drawable.jastrow01, "");
    Illusion motion1            = new Illusion(11, "Motion illusion 1", "Motion illusion", "", R.drawable.thumb_motion1, R.drawable.motion1, "");
    Illusion motion2            = new Illusion(12, "Motion illusion 2", "Motion illusion", "", R.drawable.thumb_motion2, R.drawable.motion2, "");
    Illusion mullerlyer         = new Illusion(13, "Muller-Lyer illusion", "Geometric Illusions", "", R.drawable.thumb_muller_lyer, R.drawable.muller_lyer, "");
    Illusion neckercube         = new Illusion(14, "Necker cube", "Geometric Illusions", "", R.drawable.thumb_necker_cube, R.drawable.necker_cube, "");
    Illusion oppelkundt         = new Illusion(15, "Oppel-Kundt illusion", "Geometric Illusions", "", R.drawable.thumb_oppel_kundt, R.drawable.oppel_kundt, "");
    Illusion poggendorf         = new Illusion(16, "Poggendorf illusion", "Geometric Illusions", "", R.drawable.thumb_poggendorf, R.drawable.poggendorf, "");
    Illusion ponzo              = new Illusion(17, "Ponzo illusion", "Geometric Illusions", "", R.drawable.thumb_ponzo, R.drawable.ponzo, "");
    Illusion rubinvase          = new Illusion(18, "Rubin vase", "3D illusions", "", R.drawable.thumb_rubin_vase, R.drawable.rubin_vase, "");
    Illusion verticalhorizontal = new Illusion(19, "Vertical-horizontal illusion", "Geometric Illusions", "", R.drawable.thumb_vertical_horizontal, R.drawable.vertical_horizontal, "");
    Illusion zollner            = new Illusion(20, "Zollner illusion", "Geometric Illusions", "", R.drawable.thumb_zollner, R.drawable.zollner, "");
}
