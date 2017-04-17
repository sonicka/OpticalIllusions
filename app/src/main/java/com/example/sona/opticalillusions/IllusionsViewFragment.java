package com.example.sona.opticalillusions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;

import com.example.sona.opticalillusions.model.Illusion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IllusionsViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IllusionsViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IllusionsViewFragment extends Fragment {

    private static final String ILLUSIONLIST = "illusionlist";

    ArrayList<Illusion> listIllusions = new ArrayList<>();

    public IllusionsViewFragment() {
    }

    public static IllusionsViewFragment newInstance(ArrayList<Illusion> listIllusions) {
        IllusionsViewFragment fragment = new IllusionsViewFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ILLUSIONLIST, listIllusions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listIllusions = getArguments().getParcelableArrayList(ILLUSIONLIST);
        }




    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_illusions_view, container, false);

        View view;

        ImageAdapter imageAdapter = new ImageAdapter(this, listIllusions);
        final GridView gridView = (GridView) findViewById(R.id.gv_illusion_grid);
        gridView.setAdapter(imageAdapter);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Illusion i = (Illusion) parent.getItemAtPosition(position);
//                //Intent intent = new Intent(AllIllusionsActivity.this, ViewIllusionActivity.class);
//                Intent intent = new Intent();
//                intent.putExtra("item", i);
//                intent.putExtra("class","Illusion");
//                //startActivity(intent);
//
//                Fragment fr = new Fragment();
//                fr.setArguments(getIntent().getBundleExtra("item"));
//                //fr.setArguments(getIntent().getBundleExtra("class"));
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment, fr);
//                fragmentTransaction.commit();
//            }
//        });

        // LISTVIEW

        LinkedHashMap<String, List<Illusion>> linkedMap = new LinkedHashMap<>();
        List<String> headers = new ArrayList<>();
        headers.add("All Illusions");
        linkedMap.put("All Illusions", listIllusions);
        linkedMap.put("3D illusions", null);
        linkedMap.put("Color Illusions", null);
        linkedMap.put("Geometric Illusions", null);
        linkedMap.put("Motion illusion", null);

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
        Log.v("hi", String.valueOf(linkedMap.size()));
        Log.v("hi", String.valueOf(linkedMap.keySet()));
        Log.v("hi", String.valueOf(linkedMap.get("3D illusions")));
        Log.v("hi", String.valueOf(linkedMap.get("3D illusions").size()));
        Log.v("hi", String.valueOf(linkedMap.get("Geometric Illusions").size()));
        Log.v("hi", String.valueOf(linkedMap.get("Color Illusions").size()));
        Log.v("hi", String.valueOf(linkedMap.get("Motion illusion").size()));
        Log.v("hi", String.valueOf(linkedMap.get("All Illusions").size()));

        final ListAdapter adapter = new ListAdapter(this, linkedMap);
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
                intent.putExtra("class","Illusion");
                startActivity(intent);
                return false;

            }

        });

        Button favouritesButton = (Button) findViewById(R.id.b_favourites);
        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllIllusionsActivity.this, FavouritesActivity.class));
            }
        });

        Button switchViewButton = (Button) findViewById(R.id.b_switch_to_list);
        switchViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gridView.getVisibility() == View.VISIBLE) {
                    gridView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                } else {
                    listView.setVisibility(View.GONE);
                    gridView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    }












    private OnFragmentInteractionListener mListener;






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
