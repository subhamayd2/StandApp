package com.aztechcorps.comedy.standapp;

import android.animation.Animator;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import com.aztechcorps.comedy.standapp.Adapter.ChannelCardAdapter;
import com.aztechcorps.comedy.standapp.Background.FetchChannelAsync;
import com.aztechcorps.comedy.standapp.Fragment.ChannelFragment;
import com.aztechcorps.comedy.standapp.Misc.ChannelCardDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AllChannelActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private ChannelCardAdapter mAdapter;
    private List<ChannelCardDetail> mList = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();
    ProgressBar channelSpinner;
    private int mShortAnimDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_channel);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setBackgroundColor(ContextCompat.getColor(getBaseContext(),R.color.green));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getBaseContext(),R.color.green));
        }

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else if(getActionBar() != null){
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mShortAnimDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference idsRef = database.getReference("channelIds");



        idsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot obj: dataSnapshot.getChildren()){
                    ids.add(obj.getKey());
                }

                FetchChannelAsync fetchChannelAsync = new FetchChannelAsync(AllChannelActivity.this);
                fetchChannelAsync.execute(ids);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
            mRecyclerView = (RecyclerView) findViewById(R.id.channelCardList);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), calculateNoOfColumns(getBaseContext())));
            mAdapter = new ChannelCardAdapter(getBaseContext(), mList);
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
        channelSpinner = (ProgressBar) findViewById(R.id.channelSpinner);
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
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }
}
