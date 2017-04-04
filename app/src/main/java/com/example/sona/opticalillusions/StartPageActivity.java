package com.example.sona.opticalillusions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Created by Soňa on 04-Apr-17.
 */

public class StartPageActivity extends Activity {

    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button)findViewById(R.id.buttonStart);
        startButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(StartPageActivity.this, GridViewActivity.class));
            }
        });
        //Here MainActivity.this is a Current Class Reference (context)
    }
}
