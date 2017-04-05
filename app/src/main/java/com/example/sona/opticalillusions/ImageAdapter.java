package com.example.sona.opticalillusions;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

final class ImageAdapter extends BaseAdapter {
    private final List<Item> images = new ArrayList<>();
    //private final LayoutInflater mInflater;
    private Context context;

    public ImageAdapter(Context c) {
        context = c;
        //mInflater = LayoutInflater.from(context);

        images.add(new Item ("Cafe wall", R.drawable.thumbcafewall));
        images.add(new Item ("Hering", R.drawable.thumbhering));
        images.add(new Item ("Ebbinghaus", R.drawable.thumbebbinghaus));
        images.add(new Item ("Color difference 1", R.drawable.thumbcolordifference01));
        images.add(new Item ("Color difference 2", R.drawable.thumbcolordifference02));
        images.add(new Item ("Hermann Grid", R.drawable.thumbhermanngrid));
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Item getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(20, 20, 20, 20);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(images.get(position).getDrawableId());
        return imageView;
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