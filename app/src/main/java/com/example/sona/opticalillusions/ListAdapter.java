package com.example.sona.opticalillusions;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soňa on 05-Apr-17.
 */

public class ListAdapter extends BaseAdapter {

    private final List<Item> images = new ArrayList<>();
    private Context context;

    public ListAdapter(Context c) {
        context = c;
        //mInflater = LayoutInflater.from(context);

        images.add(new Item("Cafe wall", R.drawable.thumbcafewall));
        images.add(new Item("Hering", R.drawable.thumbhering));
        images.add(new Item("Ebbinghaus", R.drawable.thumbebbinghaus));
        images.add(new Item("Color difference 1", R.drawable.thumbcolordifference01));
        images.add(new Item("Color difference 2", R.drawable.thumbcolordifference02));
        images.add(new Item("Hermann Grid", R.drawable.thumbhermanngrid));
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


    private static class Item {
        public final String name;
        public final int drawableId;

        Item(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }

        public int getDrawableId() {
            return drawableId;
        }
    }
}
