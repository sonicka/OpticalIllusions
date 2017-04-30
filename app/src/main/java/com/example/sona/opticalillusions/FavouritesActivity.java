package com.example.sona.opticalillusions;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;

import io.realm.Case;
import io.realm.OrderedRealmCollection;
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
    private OrderedRealmCollection<Illusion> favouriteIllusions;

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
        favouriteIllusions = realm.where(Illusion.class).equalTo("isFavourite", true).findAll();
        adapter = new ImageAdapter(this, favouriteIllusions);
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

        Toolbar bottomToolbar = (Toolbar) findViewById(R.id.bottom_toolbar);
        if (bottomToolbar != null) {
            setSupportActionBar(bottomToolbar);
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

        ImageView logo = (ImageView) findViewById(R.id.ib_logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavouritesActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        ImageButton switchViewButton = (ImageButton) findViewById(R.id.b_switch_view);
        switchViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final EditText et = (EditText) findViewById(R.id.et_search);
        final ImageButton searchButton = (ImageButton) findViewById(R.id.ib_search);
        searchButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                et.setEnabled(true);
                et.requestFocus();
            }
        });

        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    openKeyboard(v);
                }
                if (v.getId() == R.id.et_search && !hasFocus) {
                    et.setEnabled(false);
                    hideKeyboard(v);
                }
            }
        });

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0) {
                    favouriteIllusions = realm.where(Illusion.class).equalTo("isFavourite", true)
                            .contains("name", s.toString(), Case.INSENSITIVE)
                            .or()
                            .contains("category", s.toString(), Case.INSENSITIVE)
                            .or()
                            .contains("description", s.toString(), Case.INSENSITIVE).findAll();
                } else {
                    favouriteIllusions = realm.where(Illusion.class).equalTo("isFavourite", true).findAll();
                }
                adapter = new ImageAdapter(FavouritesActivity.this, favouriteIllusions);
                gridView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

       // SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

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

    public void openKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

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
