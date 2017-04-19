package com.example.sona.opticalillusions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sona.opticalillusions.model.Illusion;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IllusionDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IllusionDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IllusionDetailsFragment extends Fragment {

    public IllusionDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_illusion_details, container, false);

        ImageView logo = (ImageView) v.findViewById(R.id.ib_logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        AllIllusionsActivity activity = (AllIllusionsActivity) getActivity();

        final Object item = activity.getIntent().getExtras().get("item");
        final String getClass = activity.getIntent().getStringExtra("class");

        final Illusion illusion = (Illusion) item;
        //ArrayList<Illusion> list;
        //list = helper.dbToList(realm.where(Illusion.class).findAll());

        //Log.v("HOHO", String.valueOf(list.size()));
        //Log.v("HOHO", list.toString());

        TextView title = (TextView) v.findViewById(R.id.tv_title);
        title.setText(illusion.getName());

        TextView category = (TextView) v.findViewById(R.id.tv_category);
        category.setText(illusion.getCategory());

        ImageView imageView = (ImageView) v.findViewById(R.id.iv_view_illusion);
        imageView.setImageResource(illusion.getPicture());

        Button back = (Button) v.findViewById(R.id.b_last_viewed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///finish();
            }
        });

        Button toAll = (Button) v.findViewById(R.id.b_all_illusions);
        toAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO or illusionlistactivity or favouritesactivity
                //startActivity(new Intent(ViewIllusionActivity.this, AllIllusionsActivity.class));
            }
        });


        Realm.init(activity);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm realm = Realm.getInstance(config);

        final RealmHelper helper = new RealmHelper(realm);

        Button addToFavourites = (Button) v.findViewById(R.id.b_to_favourites);
        addToFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!illusion.isFavourite()) {
                    illusion.setFavourite(true);
                } else {
                    illusion.setFavourite(false);
                }
            }
        });

        HorizontalGridView horizontalGridView = (HorizontalGridView) v.findViewById(R.id.gv_small_preview);
        GridElementAdapter adapter = new GridElementAdapter(getActivity(), realm.where(Illusion.class).findAll());
        horizontalGridView.setAdapter(adapter);

        return v;
    }
















    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IllusionDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IllusionDetailsFragment newInstance(String param1, String param2) {
        IllusionDetailsFragment fragment = new IllusionDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
