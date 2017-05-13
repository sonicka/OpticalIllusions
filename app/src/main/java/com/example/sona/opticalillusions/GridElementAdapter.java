package com.example.sona.opticalillusions;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sona.opticalillusions.model.Illusion;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Adapter used to fill the horizontal grid of illusions.
 * Created by Soňa on 13-Apr-17.
 */

class GridElementAdapter extends RealmRecyclerViewAdapter<Illusion, RecyclerView.ViewHolder> {

    private Context context;
    private OrderedRealmCollection<Illusion> list;
    private int size;

    /**
     * GridElementAdapter constructor
     * @param context of app
     * @param list of illusions
     * @param size of the element
     */
    GridElementAdapter(Context context, OrderedRealmCollection<Illusion> list, int size) {
        super(list, true);
        this.context = context;
        this.list = list;
        this.size = size;
    }

    /**
     * Class representing one item in a grid.
     */
    private static class SimpleViewHolder extends RecyclerView.ViewHolder {
        final com.mikhaellopez.circularimageview.CircularImageView image;

        SimpleViewHolder(View view) {
            super(view);
            image = (com.mikhaellopez.circularimageview.CircularImageView) view.findViewById(R.id.iv_small_preview);
        }
    }

    /**
     * Creates new view of an item.
     * @param parent view group
     * @param viewType view type
     * @return void
     */
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(this.context).inflate(R.layout.illusion_small_preview, parent, false);
        return new SimpleViewHolder(view);
    }

    /**
     * Sets parameters of the view of an item.
     * @param holder holder
     * @param position in grid
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Illusion illusion = getData().get(position);
        ImageView imageView = (ImageView) holder.itemView.findViewById(R.id.iv_small_preview);
        imageView.setImageResource(illusion.getThumbnail());
        imageView.requestLayout();
        imageView.getLayoutParams().height = size;
        imageView.getLayoutParams().width = size;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IllusionDetailsActivity) context).addIllusionToStack();
                ((IllusionDetailsActivity) context).updateActivity(illusion);
            }
        });
    }

    /**
     * Returns item id.
     * @param position in grid
     * @return long
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Returns number of items in grid.
     * @return int
     */
    @Override
    public int getItemCount() {
        return this.list.size();
    }
}
