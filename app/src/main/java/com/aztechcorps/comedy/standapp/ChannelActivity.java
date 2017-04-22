package com.aztechcorps.comedy.standapp;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.aztechcorps.comedy.standapp.Adapter.ViewPagerAdapter;
import com.aztechcorps.comedy.standapp.Fragment.ChannelPlaylistFragment;
import com.aztechcorps.comedy.standapp.Fragment.ChannelVideoFragment;

public class ChannelActivity extends AppCompatActivity
        implements ChannelPlaylistFragment.OnFragmentInteractionListener,
        ChannelVideoFragment.OnFragmentInteractionListener{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.channel_tabs);
        viewPager = (ViewPager) findViewById(R.id.channel_viewpager);

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else if(getActionBar() != null){
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        ChannelVideoFragment channelVideoFragment = new ChannelVideoFragment();
        ChannelPlaylistFragment channelPlaylistFragment = new ChannelPlaylistFragment();

        adapter.addFragment(channelVideoFragment, "VIDEOS");
        adapter.addFragment(channelPlaylistFragment, "PLAYLISTS");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
