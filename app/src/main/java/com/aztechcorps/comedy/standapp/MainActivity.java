package com.aztechcorps.comedy.standapp;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.aztechcorps.comedy.standapp.Adapter.ViewPagerAdapter;
import com.aztechcorps.comedy.standapp.Fragment.ChannelFragment;
import com.aztechcorps.comedy.standapp.Fragment.EventFragment;
import com.aztechcorps.comedy.standapp.Fragment.TopFragment;

public class MainActivity extends AppCompatActivity
        implements TopFragment.OnFragmentInteractionListener,
        ChannelFragment.OnFragmentInteractionListener,
        EventFragment.OnFragmentInteractionListener{

    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private String[] colors = {"Top", "Channels", "Events"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        tabLayout.setupWithViewPager(viewPager);

        setSupportActionBar(toolbar);

        /*for (String color : colors) {
            tabLayout.addTab(tabLayout.newTab().setText(color));
        }*/

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int colorFrom = ((ColorDrawable) toolbar.getBackground()).getColor();
                int colorTo = getColorForTab(tab.getPosition());

                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                colorAnimation.setDuration(300);
                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        int color = (int) animator.getAnimatedValue();

                        toolbar.setBackgroundColor(color);
                        tabLayout.setBackgroundColor(color);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(color);
                        }
                    }

                });
                colorAnimation.start();
                //toolbar.setTitle(colors[tab.getPosition()].toUpperCase());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    public int getColorForTab(int position) {
        if (position == 0) return ContextCompat.getColor(this, R.color.colorPrimary);
        else if (position == 1) return ContextCompat.getColor(this, R.color.green);
        else return ContextCompat.getColor(this, R.color.gray);
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        TopFragment topFragment = new TopFragment();
        ChannelFragment channelFragment = new ChannelFragment();
        EventFragment eventFragment = new EventFragment();

        adapter.addFragment(topFragment, "TOP");
        adapter.addFragment(channelFragment, "CHANNELS");
        adapter.addFragment(eventFragment, "EVENTS");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
