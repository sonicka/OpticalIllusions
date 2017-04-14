package com.example.sona.opticalillusions;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Soňa on 05-Apr-17.
 */

public class ListAdapter extends BaseExpandableListAdapter implements Filterable {

    private Context context;
    private List<String> headerList;
    private HashMap<String, List<Illusion>> listHashMap;
    //private ArrayList<Item> listItems = new ArrayList<>();
    private ArrayList<Illusion> allIllusions = new ArrayList<>();
    private ArrayList<Illusion> filteredIllusions = new ArrayList<>();
    private IllusionFilter filter = new IllusionFilter();

    public ListAdapter(Context context, List<String> headerList, HashMap<String, List<Illusion>> listHashMap, ArrayList<Illusion> allIllusions) {
        this.context = context;
        this.headerList = headerList;
        this.listHashMap = listHashMap;
        this.allIllusions = allIllusions;
    }

//    public ListAdapter(Context c, ArrayList<Illusion> list) {
//        context = c;
//        allIllusions = list;
//        filteredIllusions.addAll(allIllusions);
//        getFilter();
//    }
//
//    @Override
//    public int getCount() {
//        return allIllusions.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return allIllusions.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return allIllusions.indexOf(getItem(position));
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        if (convertView == null){
//            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//            convertView = inflater.inflate(R.layout.illusion_list_item, parent, false);
//        }
//
//        Illusion illusion = filteredIllusions.get(position);
//
//        ImageView imageViewItem = (ImageView) convertView.findViewById(R.id.iv_list_item);
//        TextView textViewItem = (TextView) convertView.findViewById(R.id.tv_list_item);
//        imageViewItem.setImageResource(illusion.getThumbnail());
//        textViewItem.setText(illusion.getName());
//
//        return convertView;
//    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new IllusionFilter();
        }
        return filter;
    }

    @Override
    public int getGroupCount() {
        return headerList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listHashMap.get(headerList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headerList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(headerList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.tw_list_header);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.illusion_list_item, null);
        }

        Illusion illusion = allIllusions.get(childPosition);

        ImageView imageViewItem = (ImageView) convertView.findViewById(R.id.iv_list_item);
        TextView textViewItem = (TextView) convertView.findViewById(R.id.tv_list_item);
        imageViewItem.setImageResource(illusion.getThumbnail());
        textViewItem.setText(illusion.getName());

        return convertView;




    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class IllusionFilter extends Filter {

        /**
         * Custom filter for illusion list
         * Filter content in illusion list according to the search text
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Illusion> tempList = new ArrayList<>();
                for (Illusion i : filteredIllusions) {
                    if (i.getName().toLowerCase().contains(constraint.toString().toLowerCase())
                            || i.getDescription().toLowerCase().contains(constraint.toString().toLowerCase())
                            || i.getCategory().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(i);
                    }
                }
                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = filteredIllusions.size();
                filterResults.values = filteredIllusions;
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
            filteredIllusions = (ArrayList<Illusion>) results.values;
            notifyDataSetChanged();
        }
    }
}
