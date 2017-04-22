package com.example.sona.opticalillusions;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sona.opticalillusions.model.Illusion;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by Soňa on 13-Apr-17.
 */

class GridElementAdapter extends RealmRecyclerViewAdapter<Illusion, RecyclerView.ViewHolder> {

    private Context context;
    private OrderedRealmCollection<Illusion> list;


    GridElementAdapter(Context context, OrderedRealmCollection<Illusion> list){
        super(context, list, true);
        this.context = context;
        this.list = list;
    }

    private static class SimpleViewHolder extends RecyclerView.ViewHolder {
        final CircleImageView image;

        SimpleViewHolder(View view) {
            super(view);
            image = (CircleImageView) view.findViewById(R.id.iv_small_preview);
        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(this.context).inflate(R.layout.illusion_small_preview, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Illusion illusion = getData().get(position);
        ImageView imageView = (ImageView) holder.itemView.findViewById(R.id.iv_small_preview);
        imageView.setImageResource(illusion.getThumbnail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewIllusionActivity) context).setOnlyOneItemInStack(false);
                ((ViewIllusionActivity) context).getStack().push(illusion);
                ((ViewIllusionActivity) context).setTopIllusion(illusion);
                ((ViewIllusionActivity) context).updateActivity(illusion);
                ((ViewIllusionActivity) context).setCurrentIllusion(illusion);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
}