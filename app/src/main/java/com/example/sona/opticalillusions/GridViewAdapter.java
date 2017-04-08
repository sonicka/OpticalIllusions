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

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.GridViewHolder> {

    private static final String TAG = GridViewAdapter.class.getSimpleName();
    final private GridItemClickListener onClickListener;
    private int numberOfGridItems;

    public interface GridItemClickListener {
        void onListItemClick (int clickedItemIndex);
    }

    public GridViewAdapter(int numberOfGridItems, GridItemClickListener listener) {
        this.numberOfGridItems = numberOfGridItems;
        onClickListener = listener;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForGridItem = R.layout.illusion_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForGridItem, parent, shouldAttachToParentImmediately);
        GridViewHolder viewHolder = new GridViewAdapter.GridViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GridViewAdapter.GridViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numberOfGridItems;
    }


    class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView gridItemView;
        TextView viewHolderIndex;

        public GridViewHolder(View itemView) {
            super(itemView);

            gridItemView = (TextView) itemView.findViewById(R.id.tv_grid_item);
            viewHolderIndex = (TextView) itemView.findViewById(R.id.tv_view_holder_instance);
            itemView.setOnClickListener(this);
        }

        void bind (int gridIndex) {
            gridItemView.setText(String.valueOf(gridIndex));
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClick(clickedPosition);
        }
    }
}