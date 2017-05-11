package com.example.sona.opticalillusions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sona.opticalillusions.model.Illusion;

import java.lang.reflect.Field;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import me.grantland.widget.AutofitTextView;

/**
 * This activity shows a grid of illusions set as favourite.
 * Created by Soňa on 04-Apr-17.
 */

public class FavouritesActivity extends AppCompatActivity {

    private Realm realm;
    private OrderedRealmCollection<Illusion> favouriteIllusions;
    private RealmHelper helper;
    private Illusion draggedIllusion;
    private GridView gridView;
    private ImageAdapter adapter;
    private ImageButton removeButton;
    private EditText editTextSearch;
    private ImageButton searchButton;
    private Typeface type;
    private int toolbarHeight;
    private int contentHeight;
    private int itemSize;
    private int categoryHeight;
    private int nameSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);
        helper = new RealmHelper(realm);

        favouriteIllusions = helper.getFavourites();

        DisplayMetrics display = this.getResources().getDisplayMetrics();
        int width = display.widthPixels;
        int height = display.heightPixels;
        toolbarHeight = (int) (height/8.4873);
        contentHeight = height-(2*toolbarHeight);
        itemSize = width/3;
        categoryHeight = height/8;
        nameSize = categoryHeight/2;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        ImageView logo = (ImageView) findViewById(R.id.ib_logo);
        setCustomParams(logo, toolbarHeight, toolbarHeight);
        int p = toolbarHeight/10;
        logo.setPadding(p,p,p,p);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavouritesActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        AutofitTextView title = (AutofitTextView) findViewById(R.id.tv_title);
        setCustomParams(title, width-4*toolbarHeight/3, toolbarHeight);
        title.setText(R.string.favourites);
        title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Giorgio.ttf"));
        title.setGravity(Gravity.CENTER);

        gridView = (GridView) findViewById(R.id.gv_favourites_grid);
        adapter = new ImageAdapter(this, favouriteIllusions, itemSize);
        gridView.setAdapter(adapter);
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Illusion i = (Illusion) parent.getItemAtPosition(position);
                Intent intent = new Intent(FavouritesActivity.this, IllusionDetailsActivity.class);
                intent.putExtra("item", i);
                startActivity(intent);
            }
        };
        gridView.setOnItemClickListener(onItemClickListener);
    //    gridView.setLayoutParams(new LinearLayout.LayoutParams(width, contentHeight));
        //gridView.setColumnWidth(itemSize+itemSize/3);

        AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Illusion i = (Illusion) parent.getItemAtPosition(position);
                draggedIllusion = i;

                if (Build.VERSION.SDK_INT >= 24) {
                    ClipData data = ClipData.newPlainText("", i.getName());
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDragAndDrop(data, shadowBuilder, view, 0);
                } else {
                    showDeleteDialog(R.string.delete_text2);
                }
                return true;
            }
        };

        gridView.setOnItemLongClickListener(onItemLongClickListener);

        Toolbar bottomToolbar = (Toolbar) findViewById(R.id.favourites_bottom_toolbar);
        if (bottomToolbar != null) {
            setSupportActionBar(bottomToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        setCustomParams(bottomToolbar, width, toolbarHeight);

        removeButton = (ImageButton) findViewById(R.id.b_left_button);
        setCustomParams(removeButton, toolbarHeight, toolbarHeight);
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
                if (!favouriteIllusions.isEmpty()) {
                    showDeleteDialog(R.string.delete_text);
                } else {
                    Toast.makeText(FavouritesActivity.this, R.string.no_illusions_to_delete, Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton switchViewButton = (ImageButton) findViewById(R.id.b_switch_view);
        setCustomParams(switchViewButton, toolbarHeight, toolbarHeight);
        switchViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editTextSearch = (EditText) findViewById(R.id.et_search);
        editTextSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && searchButton.isPressed()) {
                    try {
                        Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
                        f.setAccessible(true);
                        f.set(editTextSearch, R.drawable.color_cursor);
                    } catch (Exception ignored) {}
                    openKeyboard(v);
                }
                if (v.getId() == R.id.et_search && !hasFocus && !editTextSearch.isPressed()) {
                    editTextSearch.setEnabled(false);
                    hideKeyboard(v);
                }
            }
        });

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter = new ImageAdapter(FavouritesActivity.this, helper.searchInFavourites(s), itemSize);
                gridView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        searchButton = (ImageButton) findViewById(R.id.ib_search);
        setCustomParams(searchButton, toolbarHeight, toolbarHeight);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!favouriteIllusions.isEmpty()) {
                    editTextSearch.setEnabled(true);
                    editTextSearch.setFocusableInTouchMode(true);
                    editTextSearch.clearFocus();
                    editTextSearch.requestFocus();
                } else {
                    Toast.makeText(FavouritesActivity.this, R.string.no_illusions_to_filter, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Shows alert dialog when attempted to delete favourite illusions.
     * @param deleteMode selected / all illusions to be deleted
     */
    private void showDeleteDialog(final int deleteMode) {
        removeButton.setImageResource(R.drawable.ic_delete_open);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(FavouritesActivity.this, R.style.DialogLight);
        View mView = getLayoutInflater().inflate(R.layout.dialog_box, null);

        final TextView title = (TextView) mView.findViewById(R.id.tv_delete_title);
        title.setTypeface(type);
        title.setTextColor(ContextCompat.getColor(FavouritesActivity.this, R.color.black));

        final TextView text = (TextView) mView.findViewById(R.id.tv_delete_text);
        text.setText(deleteMode);
        text.setTextColor(ContextCompat.getColor(FavouritesActivity.this, R.color.black));

        ImageButton delete = (ImageButton) mView.findViewById(R.id.b_delete_yes);
        ImageButton cancel = (ImageButton) mView.findViewById(R.id.b_delete_cancel);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        dialog.show();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.beginTransaction();
                if (deleteMode == R.string.delete_text) {
                    for (Illusion i : realm.where(Illusion.class).equalTo("isFavourite", true).findAll()) {
                        i.setFavourite(false);
                    }
                } else {
                    draggedIllusion.setFavourite(false);

                }
                realm.commitTransaction();
                removeButton.setImageResource(R.drawable.ic_delete);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                removeButton.setImageResource(R.drawable.ic_delete);
            }
        });
    }


    //// TODO: 10-May-17
    public void setCustomParams(View v, int width, int height) {
        v.requestLayout();
        v.getLayoutParams().width = width;
        v.getLayoutParams().height = height;
    }

    /**
     * Opens keyboard when needed.
     *
     * @param view current view
     */
    public void openKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * Closes keyboard when no longer needed.
     *
     * @param view current view
     */
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Overrides default method in order to clear focus of an EditText when tapped outside of it.
     *
     * @param event current motion event
     * @return true/false
     */
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
