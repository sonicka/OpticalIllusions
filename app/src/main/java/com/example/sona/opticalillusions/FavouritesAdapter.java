package com.example.sona.opticalillusions;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Soňa on 09-Apr-17.
 */

final class FavouritesAdapter extends BaseAdapter {
    private ArrayList<Illusion> illusions = new ArrayList<>();
    private Context context;

    public FavouritesAdapter(Context c, ArrayList<Illusion> illusions) {
        context = c;
        this.illusions = illusions;
    }

    @Override
    public int getCount () {
        return illusions.size();
    }

    @Override
    public Illusion getItem (int i){
        return illusions.get(i);
    }

    @Override
    public long getItemId ( int i){
        return 0;
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent){

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.illusion_grid_item, parent, false);
        }

        Illusion illusion = illusions.get(position);

        CircleImageView imageViewItem = (CircleImageView) convertView.findViewById(R.id.iv_grid_item);
        imageViewItem.setImageResource(illusion.getThumbnail());
        Log.v("BZ", illusion.toString());

        return convertView;
    }
}