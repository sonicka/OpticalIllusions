package com.example.sona.opticalillusions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.realm.Case;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Soňa on 04-Apr-17.
 */

public class AllIllusionsActivity extends AppCompatActivity {

    private OrderedRealmCollection<Illusion> listIllusions;
    private LinkedHashMap<String, List<Illusion>> linkedMap;
    private GridView gridView;
    private Realm realm;
    private ImageAdapter imageAdapter;
    private ListAdapter adapter;
    private EditText et;
    private int previousGroup = -1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_illusions);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);

        listIllusions = realm.where(Illusion.class).findAll();

        Toolbar toolbar = (Toolbar) findViewById(R.id.top_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        final TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText(R.string.preview);
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/Giorgio.ttf");
        title.setTypeface(type);
        title.setPadding(0, 55, 0, 0);

        // GRIDVIEW

        imageAdapter = new ImageAdapter(this, listIllusions);
        gridView = (GridView) findViewById(R.id.gv_illusion_grid);
        gridView.setAdapter(imageAdapter);
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Illusion i = (Illusion) parent.getItemAtPosition(position);
                Intent intent = new Intent(AllIllusionsActivity.this, ViewIllusionActivity.class);
                intent.putExtra("item", i);
                startActivity(intent);
            }
        };
        gridView.setOnItemClickListener(onItemClickListener);

        // LISTVIEW

        adapter = new ListAdapter(this, fillMap(listIllusions));
        final ExpandableListView listView = (ExpandableListView) findViewById(R.id.id_list_view);
        listView.setAdapter(adapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                long packedPosition = ExpandableListView.getPackedPositionForChild(groupPosition, childPosition);
                int flatPosition = parent.getFlatListPosition(packedPosition);
                Illusion i = (Illusion) parent.getItemAtPosition(flatPosition);
                Intent intent = new Intent(AllIllusionsActivity.this, ViewIllusionActivity.class);
                intent.putExtra("item", i);
                startActivity(intent);
                return false;
            }
        });

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Boolean shouldExpand = (!listView.isGroupExpanded(groupPosition));
                listView.collapseGroup(previousGroup);

                if (shouldExpand) {
                    listView.expandGroup(groupPosition);
                    listView.setSelectionFromTop(groupPosition, 0);
                }
                previousGroup = groupPosition;
                return true;
            }
        });

        Toolbar bottomToolbar = (Toolbar) findViewById(R.id.all_bottom_toolbar);
        if (bottomToolbar != null) {
            setSupportActionBar(bottomToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        ImageView logo = (ImageView) findViewById(R.id.ib_logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllIllusionsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        ImageButton favouritesButton = (ImageButton) findViewById(R.id.b_left_button);
        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllIllusionsActivity.this, FavouritesActivity.class));
            }
        });

        final ImageButton switchViewButton = (ImageButton) findViewById(R.id.b_switch_view);
        switchViewButton.setImageResource(R.drawable.ic_list);
        switchViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gridView.getVisibility() == View.VISIBLE) {
                    gridView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    title.setText(R.string.categories);
                    switchViewButton.setImageResource(R.drawable.ic_grid);
                } else {
                    listView.setVisibility(View.GONE);
                    gridView.setVisibility(View.VISIBLE);
                    title.setText(R.string.preview);
                    switchViewButton.setImageResource(R.drawable.ic_list);
                }
                et.setText("");
            }
        });

        et = (EditText) findViewById(R.id.et_search);
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
                    listIllusions = realm.where(Illusion.class).contains("name", s.toString(), Case.INSENSITIVE)
                            .or()
                            .contains("category", s.toString(), Case.INSENSITIVE)
                            .or()
                            .contains("description", s.toString(), Case.INSENSITIVE)
                            .findAll();
                } else {
                    listIllusions = realm.where(Illusion.class).findAll();
                }

                if (gridView.getVisibility() == View.VISIBLE) {
                    imageAdapter = new ImageAdapter(AllIllusionsActivity.this, listIllusions);
                    gridView.setAdapter(imageAdapter);
                } else {
                    adapter = new ListAdapter(AllIllusionsActivity.this, fillMap(listIllusions));
                    listView.setAdapter(adapter);
                    if (!listIllusions.isEmpty()) {
                        listView.expandGroup(0, true);
                        previousGroup = 0;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final ImageButton searchButton = (ImageButton) findViewById(R.id.ib_search);
        searchButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                et.setEnabled(true);
                et.requestFocus();
            }
        });
    }

    public LinkedHashMap<String, List<Illusion>> fillMap(OrderedRealmCollection<Illusion> listIllusion) {
        linkedMap = new LinkedHashMap<>();
        List<String> headers = new ArrayList<>();
        headers.add("All Illusions");
        linkedMap.put("All Illusions", listIllusions);
        linkedMap.put("3D illusions", new ArrayList<Illusion>());
        linkedMap.put("Color Illusions", new ArrayList<Illusion>());
        linkedMap.put("Geometric Illusions", new ArrayList<Illusion>());
        linkedMap.put("Motion illusion", new ArrayList<Illusion>());

        for (Illusion i : listIllusions) {
            if (!headers.contains(i.getCategory())) {
                headers.add(i.getCategory());
                ArrayList<Illusion> list = new ArrayList<>();
                list.add(i);
                linkedMap.put(i.getCategory(), list);
            } else {
                linkedMap.get(i.getCategory()).add(i);
            }
        }
        List<String> toBeRemoved = new ArrayList<>();
        for (Map.Entry<String, List<Illusion>> entry : linkedMap.entrySet()) {
            if (entry.getValue().isEmpty()) {
                toBeRemoved.add(entry.getKey());
            }
        }
        for (String key : toBeRemoved) {
            linkedMap.remove(key);
        }

        return linkedMap;
    }

    public void openKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}