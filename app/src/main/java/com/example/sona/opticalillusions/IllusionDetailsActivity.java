package com.example.sona.opticalillusions;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.Stack;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class IllusionDetailsActivity extends AppCompatActivity {

    private Realm realm;
    private RealmHelper realmHelper;
    private Illusion currentIllusion;
    private ImageView imageView;
    private TextView category;
    private TextView title;
    private ImageButton setFavourite;
    private TextView description;
    private VideoView videoView;
    private Stack stack;
    private GridElementAdapter adapter;
    private HorizontalGridView horizontalGridView;
    private boolean isVideoBeingTouched = false;
    private Handler handler;
    private DisplayMetrics display;
    private int width;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illusion_details);

        stack = new Stack();
        final Illusion illusion = getIntent().getExtras().getParcelable("item");
        currentIllusion = illusion;

        Realm.init(this);
        final RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);
        realmHelper = new RealmHelper(realm);

        Toolbar toolbar = (Toolbar) findViewById(R.id.top_toolbar_details);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        ImageView logo = (ImageView) findViewById(R.id.ib_logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IllusionDetailsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        title = (TextView) findViewById(R.id.tv_title);
        category = (TextView) findViewById(R.id.tv_category);
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/Giorgio.ttf");
        title.setTypeface(type);
        category.setTypeface(type);

        imageView = (ImageView) findViewById(R.id.iv_view_illusion);
        videoView = (VideoView) findViewById(R.id.vv_video);
        description = (TextView) findViewById(R.id.tv_description);

        handler = new Handler();

        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!isVideoBeingTouched) {
                    isVideoBeingTouched = true;
                    if (videoView.isPlaying()) {
                        videoView.pause();
                    } else {
                        videoView.start();
                    }
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            isVideoBeingTouched = false;
                        }
                    }, 100);
                }
                return true;
            }
        });

        display = this.getResources().getDisplayMetrics();
        width = display.widthPixels;

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl_view);
        //rl.setLayoutParams(new LinearLayout.LayoutParams(width, width));
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(width, width));
        videoView.setLayoutParams(new RelativeLayout.LayoutParams(width, width));

//        imageView.setLayoutParams(new LinearLayout.LayoutParams(width, width));
//        videoView.setLayoutParams(new LinearLayout.LayoutParams(width, width));

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.setDisplay(null);
                mp.reset();
                mp.setDisplay(videoView.getHolder());
                videoView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
            }
        });

        imageView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                imageView.setVisibility(View.GONE);
                description.setVisibility(View.VISIBLE);

                int myColor = Color.parseColor("#000000");  //todo
                Drawable backgroundColor = new ColorDrawable(myColor);
                backgroundColor.setAlpha(100);

                description.setBackground(backgroundColor);
                //description.setBackgroundResource(currentIllusion.getPicture());

                // description.getBackground().setAlpha(80);

                //description.getBackground().setAlpha(80);
            }

            @Override
            public void onClick() {
                if (haveNetworkConnection()) {
                    imageView.setVisibility(View.GONE);
                    videoView.setVisibility(View.VISIBLE);

                    Toast toast = Toast.makeText(IllusionDetailsActivity.this, R.string.animation_loading, Toast.LENGTH_SHORT);
                    toast.show();

                    videoView.setVideoPath(currentIllusion.getAnimation());
                    videoView.start();
                } else {    //todo http://stackoverflow.com/a/33193463/7813295
                    Toast.makeText(IllusionDetailsActivity.this, R.string.connect_to_internet, Toast.LENGTH_SHORT).show();
                }
            }
        });

        description.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                imageView.setVisibility(View.VISIBLE);
                imageView.setAlpha((float) 1.0);

                imageView.setImageResource(currentIllusion.getPicture());
                //imageView.setImageAlpha(255);
                description.setVisibility(View.GONE);
            }
        });

        ImageButton back = (ImageButton) findViewById(R.id.b_last_viewed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findViewById(R.id.gv_favourites_grid) != null) {
                    GridView gv = (GridView) findViewById(R.id.gv_favourites_grid);
                    gv.invalidateViews();
                }
                if (stack.isEmpty()) {
                    finish();
                } else {
                    updateActivity((Illusion) stack.pop());
                }
            }
        });

        ImageButton toAll = (ImageButton) findViewById(R.id.b_to_all);
        toAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                stack.clear();
            }
        });

        setFavourite = (ImageButton) findViewById(R.id.b_to_favourites);
        setFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realmHelper.setFavourite(realm, v, currentIllusion);
            }
        });

        horizontalGridView = (HorizontalGridView) findViewById(R.id.gv_small_preview);
        adapter = new GridElementAdapter(this, realm.where(Illusion.class).findAll());
        horizontalGridView.setAdapter(adapter);

        updateActivity(currentIllusion);
    }

    public void updateActivity(Illusion illusion) {
        currentIllusion = illusion;
        title.setText(illusion.getName());
        category.setText(illusion.getCategory());
        imageView.setImageResource(illusion.getPicture());

        if (illusion.isfavourite()) {
            setFavourite.setImageResource(R.drawable.ic_unfavourite);
        } else {
            setFavourite.setImageResource(R.drawable.ic_favourite);
        }
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (adapter.getItem(i).getName().equals(illusion.getName())) {
                horizontalGridView.smoothScrollToPosition(i);
                break;
            }
        }

        final ScrollView mScrollView = (ScrollView) findViewById(R.id.sv_scroller);
        mScrollView.setLayoutParams(new RelativeLayout.LayoutParams(width, width));

        //mScrollView.setLayoutParams(new LinearLayout.LayoutParams(width, width));
        description.setText(currentIllusion.getDescription());

        imageView.setVisibility(View.VISIBLE);
        description.setVisibility(View.GONE);
        videoView.stopPlayback();
        videoView.setVisibility(View.GONE);
    }

    public void addIllusionToStack() {
        stack.push(currentIllusion);
    }

    private boolean haveNetworkConnection() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = cm.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network ni : networks) {
                networkInfo = cm.getNetworkInfo(ni);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (cm != null) {
                NetworkInfo[] info = cm.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            Log.d("Network", "NETWORKNAME: " + anInfo.getTypeName());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
