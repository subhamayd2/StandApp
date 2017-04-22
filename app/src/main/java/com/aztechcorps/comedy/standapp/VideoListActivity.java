package com.aztechcorps.comedy.standapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class VideoListActivity extends AppCompatActivity {

    private RecyclerView videoCardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        videoCardList = (RecyclerView) findViewById(R.id.videoCardList);
    }
}
