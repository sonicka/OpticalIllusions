package com.example.sona.opticalillusions;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Soňa on 10-Apr-17.
 */

class OnIllusionClickListener implements AdapterView.OnItemClickListener {
    private Context c;

    OnIllusionClickListener(Context c) {
        this.c = c;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
        Item i = (Item) parent.getItemAtPosition(position);
        Intent intent = new Intent(c, ViewIllusionActivity.class);
        intent.putExtra("item", i);
        c.startActivity(intent);
    }
}
