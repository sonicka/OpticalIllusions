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
 * Created by Soňa on 04-Apr-17.
 */

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    private boolean mBackClickOnce = false;
    private ArrayList<Illusion> list;


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
        list = new ArrayList<>();


        XmlResourceParser parser = getResources().getXml(R.xml.illusions);
        XmlParser mParser = new XmlParser();
        try {
            mParser.processData(parser);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }


        list = mParser.getList();
        Log.v("lalala", list.toString());
        Log.v("lalala", list.get(0).toString());
        Log.v("lalala", String.valueOf(list.size()));
        parser.close();


//        RealmHelper realmHelper = new RealmHelper(realm);
//        if (realm.isEmpty()) {
//            realmHelper.save(cafewall);
//            realmHelper.save(colordiff1);
//            realmHelper.save(colordiff2);
//            realmHelper.save(ebbinghaus);
//            realmHelper.save(hering);
//            realmHelper.save(hermanngrid);
//            realmHelper.save(illusorycontours);
//            realmHelper.save(impossiblestairs);
//            realmHelper.save(impossibletriangle);
//            realmHelper.save(jastrow);
//            realmHelper.save(motion1);
//            realmHelper.save(motion2);
//            realmHelper.save(mullerlyer);
//            realmHelper.save(neckercube);
//            realmHelper.save(oppelkundt);
//            realmHelper.save(poggendorf);
//            realmHelper.save(ponzo);
//            realmHelper.save(rubinvase);
//            realmHelper.save(verticalhorizontal);
//            realmHelper.save(zollner);
//        }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void onBackPressed() {
        if (mBackClickOnce) {
            finishAffinity();
        } else {
            mBackClickOnce = true;
            Toast.makeText(this, R.string.click_back_again_to_exit, Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBackClickOnce = false;
                }
            }, 2000);
        }
    }
}
