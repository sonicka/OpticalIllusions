package com.example.sona.opticalillusions;

import android.app.Activity;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.ArrayList;

public class IllusionsGridActivity extends Activity {

    protected GridView mGridView;
    protected ImageAdapter mAdapter;
    private ArrayList<Integer> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illusions_grid);

        images.add(R.drawable.thumbcafewall);
        images.add(R.drawable.thumbcolordifference01);
        images.add(R.drawable.thumbcolordifference02);


        /*gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(IllusionsGridActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });**/

        // Get the GridView layout
        mGridView = (GridView) findViewById(R.id.gridView);
        mAdapter = new ImageAdapter(this);
        mGridView.setAdapter(mAdapter);
    }


    class ImageAdapter extends BaseAdapter {

        private Context mContext;

        public ImageAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            int imageID = 0;

            // Want the width/height of the items
            // to be 120dp
            int wPixel = dpToPx(120);
            int hPixel = dpToPx(120);

            if (convertView == null) {
                // If convertView is null then inflate the appropriate layout file
                convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item, null);
            } else {

            }

            imageView = (ImageView) convertView.findViewById(R.id.imageView);

            // Set height and width constraints for the image view
            imageView.setLayoutParams(new LinearLayout.LayoutParams(wPixel, hPixel));

            // Set the content of the image based on the provided URI
            imageView.setImageURI(
                    Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, "" + imageID)
            );

            // Image should be cropped towards the center
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // Set Padding for images
            imageView.setPadding(8, 8, 8, 8);

            // Crop the image to fit within its padding
            imageView.setCropToPadding(true);

            return convertView;
        }

        // Convert DP to PX
        // Source: http://stackoverflow.com/a/8490361
        public int dpToPx(int dps) {
            final float scale = getResources().getDisplayMetrics().density;
            int pixels = (int) (dps * scale + 0.5f);

            return pixels;
        }
    }
}
