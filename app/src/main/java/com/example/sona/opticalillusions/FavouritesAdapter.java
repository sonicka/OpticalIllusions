package com.example.sona.opticalillusions;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Soňa on 09-Apr-17.
 */

final class FavouritesAdapter extends BaseAdapter {
    private ArrayList<Illusion> illusions = new ArrayList<>();
    private Context context;

    public FavouritesAdapter(Context c, ArrayList<Illusion> illusions) {
        context = c;
        this.illusions = illusions;
    }

    @Override
    public int getCount () {
        return illusions.size();
    }

    @Override
    public Illusion getItem (int i){
        return illusions.get(i);
    }

    @Override
    public long getItemId ( int i){
        return 0;
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent){

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.illusion_grid_item, parent, false);
        }

        Illusion illusion = illusions.get(position);

        CircleImageView imageViewItem = (CircleImageView) convertView.findViewById(R.id.iv_grid_item);
        imageViewItem.setImageResource(illusion.getThumbnail());
        Log.v("BZ", illusion.toString());

        convertView.setOnLongClickListener(onLongClickListener);


        if(illusions.get(position).isChecked()){
            //show the overlay view that suggests the item is selected
        }
        else{
            //hide the overlay view
        }

        return convertView;
    }

    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public boolean onLongClick(View v) {    //TODO drag item
            ClipData data = ClipData.newPlainText("","");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data,shadowBuilder,v,0);

            return true;
        }
    };

    public void refresh(ArrayList<Illusion> items)
    {
        this.illusions = items;
        notifyDataSetChanged();
    }

//    public static View.OnDragListener dragListener = new View.OnDragListener() {
//        @Override
//        public boolean onDrag(View v, DragEvent event) {
//            int dragEvent = event.getAction();
//
//            switch (dragEvent) {
//                case DragEvent.ACTION_DRAG_ENTERED:
//                    final View view = (View) event.getLocalState();
//                    int id = view.getId();
//                    Illusion illusion = getItem(id);
//                    illusion.setFavourite();
//                    break;
//                case DragEvent.ACTION_DRAG_EXITED:
//                    break;
//                case DragEvent.ACTION_DRAG_ENDED:
//                    break;
//            }
//            return true;
//        }
//    };


}