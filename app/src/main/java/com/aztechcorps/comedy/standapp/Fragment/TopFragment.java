package com.aztechcorps.comedy.standapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aztechcorps.comedy.standapp.Adapter.GravitySnapHelper;
import com.aztechcorps.comedy.standapp.Adapter.HorizontalListAdapter;
import com.aztechcorps.comedy.standapp.Adapter.SnapRecyclerAdapter;
import com.aztechcorps.comedy.standapp.AllChannelActivity;
import com.aztechcorps.comedy.standapp.Misc.Item;
import com.aztechcorps.comedy.standapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView horizontalList;
    private RecyclerView recyclerView;
    private HorizontalListAdapter horizontalAdapter;
    private ArrayList<Item> items;

    public TopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TopFragment newInstance(String param1, String param2) {
        TopFragment fragment = new TopFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview =  inflater.inflate(R.layout.fragment_top, container, false);
        horizontalList = (RecyclerView)rootview.findViewById(R.id.horizontal_recycler1);
        recyclerView = (RecyclerView)rootview.findViewById(R.id.recycler_view);

        createApps();
        Button btn = (Button) rootview.findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AllChannelActivity.class));
            }
        });


        horizontalList.setHasFixedSize(true);

        //set horizontal LinearLayout as layout manager to creating horizontal list view
        LinearLayoutManager horizontalManager = new LinearLayoutManager(rootview.getContext(), LinearLayoutManager.HORIZONTAL, false);
        horizontalList.setLayoutManager(horizontalManager);
        horizontalAdapter = new HorizontalListAdapter(getActivity());
        horizontalList.setAdapter(horizontalAdapter);

        //snaphelper
        SnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
        snapHelper.attachToRecyclerView(recyclerView);

        // HORIZONTAL for Gravity START/END and VERTICAL for TOP/BOTTOM
        recyclerView.setLayoutManager(new LinearLayoutManager(rootview.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        SnapRecyclerAdapter adapter = new SnapRecyclerAdapter(rootview.getContext(), items);
        recyclerView.setAdapter(adapter);
        return rootview;
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
    private void createApps() {
        items = new ArrayList<>();
        items.add(new Item("Google+", R.drawable.google_plus));
        items.add(new Item("Facebook", R.drawable.facebook));
        items.add(new Item("LinkedIn", R.drawable.linkedin));
        items.add(new Item("Youtube", R.drawable.youtube));
        items.add(new Item("Instagram", R.drawable.instagram));
        items.add(new Item("Skype", R.drawable.skype));
        items.add(new Item("Twitter", R.drawable.twitter));
        items.add(new Item("Wikipedia", R.drawable.wikipedia));
        items.add(new Item("Whats app", R.drawable.what_apps));
        items.add(new Item("Pokemon Go", R.drawable.pokemon_go));
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

