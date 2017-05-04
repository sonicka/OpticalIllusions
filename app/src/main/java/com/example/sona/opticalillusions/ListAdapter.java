package com.example.sona.opticalillusions;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Soňa on 05-Apr-17.
 */

public class ListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> headerList;
    private LinkedHashMap<String, List<Illusion>> listLinkedMap;

    public ListAdapter(Context context, LinkedHashMap<String, List<Illusion>> listLinkedMap) {
        this.context = context;
        this.headerList = new ArrayList<>(listLinkedMap.keySet());
        this.listLinkedMap = listLinkedMap;
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
    public int getGroupCount() {
        return headerList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listLinkedMap.get(headerList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return new ArrayList<>(listLinkedMap.keySet()).get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listLinkedMap.get(headerList.get(groupPosition)).get(childPosition);
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
        Typeface type = Typeface.createFromAsset(context.getAssets(), "fonts/MyriadPro-Cond.otf");
        textView.setTypeface(type); //todo font
        textView.setTextColor(ContextCompat.getColor(context, R.color.green));
        textView.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.illusion_list_item, null);
        }

        Illusion illusion = (Illusion) getChild(groupPosition, childPosition);

        //ImageView imageViewItem = (ImageView) convertView.findViewById(R.id.iv_list_item);
        TextView textViewItem = (TextView) convertView.findViewById(R.id.tv_list_item);
        //imageViewItem.setImageResource(illusion.getThumbnail());

        new DownloadImageTask((ImageView) convertView.findViewById(R.id.iv_list_item))
                .execute(illusion.getThumbnail());

        textViewItem.setText(illusion.getName());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
