package com.example.sona.opticalillusions;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;

/**
 * Created by Soňa on 04-Apr-17.
 */

final class ImageAdapter extends BaseAdapter implements Filterable {
    private final ArrayList<Item> listItems = new ArrayList<>();
    private Context context;

    private IllusionFilter illusionFilter;
    private ArrayList<Illusion> filteredList;

    public ImageAdapter (Context c, ArrayList<Illusion> list) {
        context = c;
        for (Illusion i : list) {
            listItems.add(new Item(i.getId(), i.getName(), i.getCategory(), i.getDescription(),
                    i.getThumbnail(), i.getPicture(), i.getAnimation()));
        }
        this.filteredList = list;
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Illusion getItem(int i) {
        return filteredList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.illusion_grid_item, parent, false);
        }

        Item item = listItems.get(position);

        ImageView imageViewItem = (ImageView) convertView.findViewById(R.id.iv_grid_item);
        imageViewItem.setImageResource(item.thumbnail);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (illusionFilter == null) {
            illusionFilter = new IllusionFilter();
        }
        return illusionFilter;
    }


    private class IllusionFilter extends Filter {

        /**
         * Custom filter for friend list
         * Filter content in friend list according to the search text
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Illusion> tempList = new ArrayList<>();
                for (Illusion i : filteredList) {
                    if (i.getName().toLowerCase().contains(constraint.toString().toLowerCase())
                            || i.getDescription().toLowerCase().contains(constraint.toString().toLowerCase())
                            || i.getCategory().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(i);
                    }
                }
                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = filteredList.size();
                filterResults.values = filteredList;
            }
            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<Illusion>) results.values;
            notifyDataSetChanged();
        }
    }
}