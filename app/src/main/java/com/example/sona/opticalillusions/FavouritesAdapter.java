package com.example.sona.opticalillusions;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.sona.opticalillusions.model.FavouriteIllusion;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Soňa on 09-Apr-17.
 */

final class FavouritesAdapter extends BaseAdapter {
    private final ArrayList<Item> listItems = new ArrayList<>();
    private Context context;

    public FavouritesAdapter(Context c, List<FavouriteIllusion> list) {
        context = c;

        for (FavouriteIllusion i : list) {
            listItems.add(new Item(i.getName(), i.getThumbnail()));
        }
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Item getItem(int i) {
        return listItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.illusion_grid_item, parent, false);
        }

        Item item = listItems.get(position);

        ImageView imageViewItem = (ImageView) convertView.findViewById(R.id.iv_grid_item);
        imageViewItem.setImageResource(item.drawableId);

        return convertView;
    }

    private static class Item {
        public final String name;
        public final int drawableId;

        Item(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }
    }
}