package com.example.sona.opticalillusions;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Soňa on 04-Apr-17.
 */

public class FavouritesActivity extends AppCompatActivity {

    private Realm realm;
    private Illusion draggedIllusion;
    private static GridView gridView;
    private static ImageAdapter adapter;
    private ArrayList<Illusion> checkedIllusions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText(R.string.favourites);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Giorgio.ttf");
        title.setTypeface(type);
        title.setPadding(0,55,0,0);

        checkedIllusions = new ArrayList<>();

        gridView = (GridView) findViewById(R.id.gv_favourites_grid);
        adapter = new ImageAdapter(this, realm.where(Illusion.class).equalTo("isFavourite", true).findAll());
        gridView.setAdapter(adapter);
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Illusion i = (Illusion) parent.getItemAtPosition(position);
                Intent intent = new Intent(FavouritesActivity.this, ViewIllusionActivity.class);
                intent.putExtra("item", i);
                startActivity(intent);
            }
        };
        gridView.setOnItemClickListener(onItemClickListener);

        AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Illusion i = (Illusion) parent.getItemAtPosition(position);
                draggedIllusion = i;

                ClipData data = ClipData.newPlainText("", i.getName());
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDragAndDrop(data,shadowBuilder,view,0);

                realm.beginTransaction();
               // listIllusions.get(position).toggleChecked();
                i.toggleChecked();
                checkedIllusions.add(i);
                realm.commitTransaction();
                //listIllusions.clear();
                //adapter.refresh(listIllusions);

//                if (!arrayList.contains(view)) {
//                    arrayList.add(view);
//                    view.setSelected(true);
//                } else {
//                    arrayList.remove(view);
//                    view.setSelected(false);
//                }
                Log.v("lul", checkedIllusions.toString());

                return false;
            }
        };

        gridView.setOnItemLongClickListener(onItemLongClickListener);
       /* gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
        gridView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.setTitle("Select Items");
                mode.setSubtitle("One item selected");
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                int selectCount = gridView.getCheckedItemCount();
                switch (selectCount) {
                    case 1:
                        mode.setSubtitle("One item selected");
                        break;
                    default:
                        mode.setSubtitle("" + selectCount + " items selected");
                        break;
                }
            }
        });
        gridView.setDrawSelectorOnTop(true);*/
        //gridView.setSelector(getResources().getDrawable(R.drawable.gridview_selector));

        Toolbar bottonToolbar = (Toolbar) findViewById(R.id.bottom_toolbar);
        if (bottonToolbar != null) {
            setSupportActionBar(bottonToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        final ImageButton removeButton = (ImageButton) findViewById(R.id.b_left_button);
        removeButton.setImageResource(R.drawable.ic_delete);
        removeButton.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int dragEvent = event.getAction();

                switch (dragEvent) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        removeButton.setImageResource(R.drawable.ic_delete_open);
                        break;
                    case DragEvent.ACTION_DROP:
                        realm.beginTransaction();
                        draggedIllusion.setFavourite(false);
                        realm.copyToRealmOrUpdate(draggedIllusion);
                        realm.commitTransaction();
                        draggedIllusion = null;
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        removeButton.setImageResource(R.drawable.ic_delete);
                        break;
                }
                return true;
            }
        });
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog diaBox = AskOption();
                diaBox.show();
            }
        });

        ImageButton switchViewButton = (ImageButton) findViewById(R.id.b_switch_view);
        switchViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        //SearchView search = (SearchView) menu.findItem(R.id.sv_search).getActionView();

        /*searchButton = (Button)findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });*/
    }

//    @Override
//    public void onResume() {  // After a pause OR at startup
//        super.onResume();
//        adapter.notifyDataSetChanged();
//        gridView.invalidateViews();
//        gridView.setAdapter(adapter);

//        ViewGroup vg = (ViewGroup) findViewById (R.id.favourite_layout);
//        vg.invalidate();
//    }


    public static GridView getGridView() {
        return gridView;
    }

    public static ImageAdapter getAdapter() {
        return adapter;
    }

    private AlertDialog AskOption() {

        AlertDialog deleteDialogBox =new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you want to delete all favourite illusions?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        realm.beginTransaction();
                        for (Illusion i : realm.where(Illusion.class).equalTo("isFavourite", true).findAll()) {
                            i.setFavourite(false);
                        }
                        realm.commitTransaction();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return deleteDialogBox;
    }
}
