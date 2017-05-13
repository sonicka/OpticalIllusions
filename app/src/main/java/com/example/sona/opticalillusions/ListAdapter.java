package com.example.sona.opticalillusions;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import me.grantland.widget.AutofitTextView;

/**
 * Adapter used to fill the list of illusions.
 * Created by Soňa on 05-Apr-17.
 */

class ListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> headerList;
    private LinkedHashMap<String, List<Illusion>> listLinkedMap;

    /**
     * Constructor for the adapter.
     * @param context context
     * @param listLinkedMap map
     */
    ListAdapter(Context context, LinkedHashMap<String, List<Illusion>> listLinkedMap) {
        this.context = context;
        this.headerList = new ArrayList<>(listLinkedMap.keySet());
        this.listLinkedMap = listLinkedMap;
    }

    /**
     * Returns the number of categories.
     * @return int
     */
    @Override
    public int getGroupCount() {
        return headerList.size();
    }

    /**
     * Returns the number of illusions within given category.
     * @param groupPosition int
     * @return int
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return listLinkedMap.get(headerList.get(groupPosition)).size();
    }

    /**
     * Returns list of illusions within given category.
     * @param groupPosition int
     * @return ArrayList
     */
    @Override
    public Object getGroup(int groupPosition) {
        return new ArrayList<>(listLinkedMap.keySet()).get(groupPosition);
    }

    /**
     * Returns the illusion within given category on given position.
     * @param groupPosition int
     * @param childPosition int
     * @return Illusion
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listLinkedMap.get(headerList.get(groupPosition)).get(childPosition);
    }

    /**
     * Returns category ID.
     * @param groupPosition int
     * @return int
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * Returns illusion ID.
     * @param childPosition int
     * @return int
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * Define objects not to have stable IDs.
     * @return false
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Sets up the view of groups within the ListView.
     * @param groupPosition int
     * @param isExpanded boolean
     * @param convertView view
     * @param parent viewgroup
     * @return view
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }
        AutofitTextView textView = (AutofitTextView) convertView.findViewById(R.id.tw_list_header);
        textView.requestLayout();
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/gudea-regular.ttf"));
        textView.setTextColor(ContextCompat.getColor(context, R.color.green));
        textView.setText(headerTitle);

        return convertView;
    }

    /**
     * Sets up the view of an illusion within each category.
     * @param groupPosition int
     * @param childPosition int
     * @param isLastChild boolean
     * @param convertView view
     * @param parent viewgroup
     * @return view
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.illusion_list_item, null);
        }

        Illusion illusion = (Illusion) getChild(groupPosition, childPosition);
        ImageView imageViewItem = (ImageView) convertView.findViewById(R.id.iv_list_item);
        AutofitTextView textViewItem = (AutofitTextView) convertView.findViewById(R.id.tv_list_item);
        imageViewItem.setImageResource(illusion.getThumbnail());
        textViewItem.setText(illusion.getName());

        return convertView;
    }

    /**
     * Returns true/false if the child is selectable.
     * @param groupPosition group position
     * @param childPosition child position
     * @return true/false
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
