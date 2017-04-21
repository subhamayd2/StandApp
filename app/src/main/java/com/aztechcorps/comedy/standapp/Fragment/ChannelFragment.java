package com.aztechcorps.comedy.standapp.Fragment;

import android.animation.Animator;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.aztechcorps.comedy.standapp.Adapter.ChannelCardAdapter;
import com.aztechcorps.comedy.standapp.Background.FetchChannelAsync;
import com.aztechcorps.comedy.standapp.Misc.ChannelCardDetail;
import com.aztechcorps.comedy.standapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChannelFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChannelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChannelFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private ChannelCardAdapter mAdapter;
    private List<ChannelCardDetail> mList = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();
    View view;
    ProgressBar channelSpinner;
    private int mShortAnimDuration;

    private OnFragmentInteractionListener mListener;

    public ChannelFragment() {
        // Required empty public constructor
    }


    public static ChannelFragment newInstance(String param1, String param2) {
        ChannelFragment fragment = new ChannelFragment();
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

        mShortAnimDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference idsRef = database.getReference("channelIds");



        idsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot obj: dataSnapshot.getChildren()){
                    ids.add(obj.getKey());
                }

                FetchChannelAsync fetchChannelAsync = new FetchChannelAsync(ChannelFragment.this);
                fetchChannelAsync.execute(ids);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        view = inflater.inflate(R.layout.fragment_channel, container, false);

        return view;
    }

    public void setupAdapter(Object obj){
        try {
            JSONObject rootObj = new JSONObject(obj.toString());
            JSONArray items = rootObj.getJSONArray("items");
            for(int i = 0; i < items.length(); i++){
                JSONObject itemObj = (JSONObject) items.get(i);
                JSONObject snippet = itemObj.getJSONObject("snippet");
                String image = (snippet.getJSONObject("thumbnails")).getJSONObject("medium").getString("url");
                mList.add(new ChannelCardDetail(snippet.getString("title"), snippet.getString("country"), image));
            }
            mRecyclerView = (RecyclerView) view.findViewById(R.id.channelCardList);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), calculateNoOfColumns(getContext())));
            mAdapter = new ChannelCardAdapter(getContext(), mList);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });

            crossfade();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void crossfade(){
        channelSpinner = (ProgressBar) view.findViewById(R.id.channelSpinner);
        mRecyclerView.setAlpha(0f);
        mRecyclerView.setVisibility(View.VISIBLE);

        mRecyclerView.animate()
                .alpha(1f)
                .setDuration(mShortAnimDuration)
                .setListener(null);

        channelSpinner.animate()
                .alpha(0f)
                .setDuration(mShortAnimDuration)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {  }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        channelSpinner.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {  }

                    @Override
                    public void onAnimationRepeat(Animator animator) { }
                });
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


    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }
}
