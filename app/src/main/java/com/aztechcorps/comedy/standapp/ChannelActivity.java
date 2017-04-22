package com.aztechcorps.comedy.standapp;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.aztechcorps.comedy.standapp.Adapter.ViewPagerAdapter;
import com.aztechcorps.comedy.standapp.Fragment.ChannelPlaylistFragment;
import com.aztechcorps.comedy.standapp.Fragment.ChannelVideoFragment;

public class ChannelActivity extends AppCompatActivity
        implements ChannelPlaylistFragment.OnFragmentInteractionListener,
        ChannelVideoFragment.OnFragmentInteractionListener{

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private int tabLayoutColor;
    private ViewPager viewPager;

    private ImageView channelBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        appBarLayout = (AppBarLayout) findViewById(R.id.channel_appbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.channel_tabs);
        viewPager = (ViewPager) findViewById(R.id.channel_viewpager);

        channelBanner = (ImageView) findViewById(R.id.channel_banner);

        Bitmap banner = ((BitmapDrawable) channelBanner.getDrawable()).getBitmap();

        Palette.generateAsync(banner, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                tabLayoutColor = palette.getDominantColor(ContextCompat.getColor(getBaseContext() ,R.color.colorPrimary));
                tabLayout.setBackgroundColor(tabLayoutColor);
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();
                }

                int colorFrom, colorTo;
                int mAnimateDuration = 700;

                if(scrollRange + verticalOffset == 0){
                    //tabLayout.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));

                    colorFrom = ((ColorDrawable) tabLayout.getBackground()).getColor();
                    colorTo = ContextCompat.getColor(getBaseContext(), R.color.colorPrimary);

                    ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                    colorAnimation.setDuration(mAnimateDuration);
                    colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animator) {
                            int color = (int) animator.getAnimatedValue();
                            tabLayout.setBackgroundColor(color);
                        }

                    });
                    colorAnimation.start();

                } else {
                    //tabLayout.setBackgroundColor(tabLayoutColor);

                    colorFrom = ((ColorDrawable) tabLayout.getBackground()).getColor();
                    colorTo = tabLayoutColor;

                    ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                    colorAnimation.setDuration(mAnimateDuration);
                    colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animator) {
                            int color = (int) animator.getAnimatedValue();
                            tabLayout.setBackgroundColor(color);
                        }

                    });
                    colorAnimation.start();
                }
            }
        });



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
