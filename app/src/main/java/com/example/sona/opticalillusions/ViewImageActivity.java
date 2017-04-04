package com.example.sona.opticalillusions;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by Soňa on 04-Apr-17.
 */

public class ViewImageActivity extends AppCompatActivity {

    ImageView iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewimage);
        //Intent i = getIntent();
        //File f = (File) i.getExtras().getParcelable("img");
        String f = getIntent().getStringExtra("img");
        iv2.findViewById(R.id.imageView2);
        iv2.setImageURI(Uri.parse(f));
    }

}
