package com.example.sona.opticalillusions;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sona.opticalillusions.model.Illusion;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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

        if (realm.isEmpty()) {
            XmlResourceParser parser = getResources().getXml(R.xml.illusions);
            XmlParser mParser = new XmlParser(getApplicationContext());
            try {
                mParser.processData(parser);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            parser.close();
            listFromXml = mParser.getList();
            realmHelper.listToDb(listFromXml);
        }

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
