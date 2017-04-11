package com.example.sona.opticalillusions;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;

/**
 * Created by Soňa on 05-Apr-17.
 */

public class ListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Item> listItems = new ArrayList<>();

    public ListAdapter(Context c, ArrayList<Illusion> list) {
        context = c;

        for (Illusion i : list) {
            listItems.add(new Item(i.getId(), i.getName(), i.getCategory(), i.getDescription(),
                    i.getThumbnail(), i.getPicture(), i.getAnimation()));
        }
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.illusion_list_item, parent, false);
        }

        Item item = listItems.get(position);

        ImageView imageViewItem = (ImageView) convertView.findViewById(R.id.iv_list_item);
        TextView textViewItem = (TextView) convertView.findViewById(R.id.tv_list_item);
        imageViewItem.setImageResource(item.thumbnail);
        textViewItem.setText(item.name);

        return convertView;
    }
}
