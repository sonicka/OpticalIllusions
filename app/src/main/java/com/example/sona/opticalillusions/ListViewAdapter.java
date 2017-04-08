package com.example.sona.opticalillusions;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Soňa on 07-Apr-17.
 */

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ListViewHolder> {

    private static final String TAG = ListViewAdapter.class.getSimpleName();
    final private ListItemClickListener onClickListener;
    private int numberOfListItems;

    public interface ListItemClickListener {
        void onListItemClick (int clickedItemIndex);
    }

//    private LayoutInflater mInflater;
//    private Realm mRealm;
//    private RealmResults<Illusion> mResults;
//
//    public ListViewAdapter(Context context, Realm realm, RealmResults<Illusion> results) {
//        mRealm = realm;
//        mInflater = LayoutInflater.from(context);
//        mResults = results;
//    }
//
//    public Illusion getItem(int position) {
//        return mResults.get(position);
//    }

    public ListViewAdapter(int numberOfListItems, ListItemClickListener listener) {
        this.numberOfListItems = numberOfListItems;
        onClickListener = listener;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.illusion_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        ListViewHolder viewHolder = new ListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numberOfListItems;
    }


    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView listItemView;
        TextView viewHolderIndex;

        public ListViewHolder(View itemView) {
            super(itemView);

            listItemView = (TextView) itemView.findViewById(R.id.tv_list_item);
            viewHolderIndex = (TextView) itemView.findViewById(R.id.tv_view_holder_instance);
            itemView.setOnClickListener(this);
        }

        void bind (int listIndex) {
            listItemView.setText(String.valueOf(listIndex));
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClick(clickedPosition);
        }
    }
}
