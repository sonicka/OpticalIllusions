package com.example.sona.opticalillusions;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Soňa on 04-Apr-17.
 */

public class FavouritesActivity extends AppCompatActivity {

    private static GridView gridView;
    private static ImageAdapter adapter;
    private Button removeButton;
    private ArrayList<Illusion> checkedIllusions;
    private boolean isButtonLongPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm realm = Realm.getInstance(config);

        RealmHelper realmHelper = new RealmHelper(realm);
        final ArrayList<Illusion> listIllusions = realmHelper.dbToList(realm.where(Illusion.class).equalTo("isFavourite", true).findAll());
        Log.v("PAMPAM", String.valueOf(listIllusions.size()));
        Log.v("PAMPAM", listIllusions.toString());

        checkedIllusions = new ArrayList<>();

        gridView = (GridView) findViewById(R.id.gv_favourites_grid);
        adapter = new ImageAdapter(this, listIllusions);
        gridView.setAdapter(adapter);
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("huhu", "item clicked");
                Log.v("huhu", parent.toString());
                Log.v("huhu", String.valueOf(position));
                Log.v("huhu", String.valueOf(id));
                Illusion i = (Illusion) parent.getItemAtPosition(position);
                Intent intent = new Intent(FavouritesActivity.this, ViewIllusionActivity.class);
                intent.putExtra("item", i);
                startActivity(intent);
            }
        };
        gridView.setOnItemClickListener(onItemClickListener);
        //gridView.setOnTouchListener(touchListener);


        AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Illusion i = (Illusion) parent.getItemAtPosition(position);

                ClipData data = ClipData.newPlainText("","");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDragAndDrop(data,shadowBuilder,view,0);
                removeButton.setText(R.string.remove);
                isButtonLongPressed = true;

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




        removeButton = (Button) findViewById(R.id.buttonRemove);
        //removeButton.setOnTouchListener(touchListener);
        removeButton.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int dragEvent = event.getAction();

                switch (dragEvent) {
                    case DragEvent.ACTION_DRAG_ENTERED:
//                    final View view = (View) event.getLocalState();
//                    int id = view.getId();
                        removeButton.setText("Remove this");
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
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
                realm.beginTransaction();
                for (Illusion i : listIllusions) {
                    i.setFavourite(false);
                }
                v.refreshDrawableState();
                realm.commitTransaction();
                adapter.notifyDataSetChanged(); //ain't do shit
                gridView.invalidateViews();
                Log.v("juju", listIllusions.toString());
            }
        });

        Button switchViewButton = (Button) findViewById(R.id.b_switch_to_grid);
        switchViewButton.setText("Switch view");
        switchViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //startActivity(new Intent(FavouritesActivity.this, AllIllusionsActivity.class));
            }
        });

        /*searchButton = (Button)findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllIllusionsActivity.this, .class));
            }
        });*/
    }

//
//    public View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
//        @RequiresApi(api = Build.VERSION_CODES.N)
//        @Override
//        public boolean onLongClick(View v) {
//            ClipData data = ClipData.newPlainText("","");
//            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
//            v.startDragAndDrop(data,shadowBuilder,v,0);
//            return true;
//        }
//    };

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



//    private View.OnTouchListener touchListener = new View.OnTouchListener() {
//
//        @Override
//        public boolean onTouch(View pView, MotionEvent pEvent) {
//            if(pEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                removeButton.setText(R.string.remove_this);
//            } else if (pEvent.getAction() == MotionEvent.ACTION_UP) {
//                removeButton.setText(R.string.remove);
//            }
//            return true;
//        }
//    };

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to delete all favourite illusions?")
                //.setIcon(R.drawable.delete)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code

                        dialog.dismiss();
                    }

                })



                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }




}
