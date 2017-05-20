package com.example.sona.opticalillusions;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sona.opticalillusions.model.Illusion;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 * Adapter used to fill the grid of illusions.
 * Created by Soňa on 04-Apr-17.
 */

final class ImageAdapter extends RealmBaseAdapter<Illusion> {
    private Context context;
    private int size;

    /**
     * Constructor of the image adapter.
     *
     * @param context   of the app
     * @param illusions list of illusions
     * @param size      of the element
     */
    ImageAdapter(Context context, OrderedRealmCollection<Illusion> illusions, int size) {
        super(illusions);
        this.context = context;
        this.size = size;
    }

    /**
     * Returns view of a certain image.
     *
     * @param position    in grid
     * @param convertView view
     * @param parent      view group
     * @return view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.illusion_grid_item, parent, false);
        }

        Illusion item = getItem(position);

        ImageView imageViewItem = (ImageView) convertView.findViewById(R.id.iv_grid_item);
        imageViewItem.setImageResource(item.getThumbnail());
        imageViewItem.requestLayout();
        imageViewItem.getLayoutParams().width = size;
        imageViewItem.getLayoutParams().height = size;

        return convertView;
    }
}