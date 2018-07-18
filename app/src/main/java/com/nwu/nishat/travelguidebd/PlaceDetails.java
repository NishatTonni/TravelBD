package com.nwu.nishat.travelguidebd;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

public class PlaceDetails extends AppCompatActivity {

    public String placeName;
    public String placeImageURL;
    public String placeDescription;

    public Bundle bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        Intent intent = getIntent();
        bd = intent.getExtras();

        TabLayout tabLayout = findViewById(R.id.htab_tabs);

        tabLayout.addTab(tabLayout.newTab().setText("About"));
        tabLayout.addTab(tabLayout.newTab().setText("Map"));
        tabLayout.addTab(tabLayout.newTab().setText("Route"));

        View root = tabLayout.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.cardview_light_background));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }

        final ViewPager viewPager = findViewById(R.id.htab_viewpager);

        ImageView placeImageView = findViewById(R.id.htab_header);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        // TODO
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if(bd != null){
            placeName = (String) bd.get("name");
            placeImageURL = (String) bd.get("image");
            placeDescription = (String) bd.get("description");
        }

        Glide.with(this)
                .load(placeImageURL)
                .into(placeImageView);

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager());

        DetailsFragment detailsFragment = new DetailsFragment(
                ContextCompat.getColor(this, R.color.cardview_light_background));
        detailsFragment.setArguments(bd);
        adapter.addFrag(detailsFragment, "About");

        MapsFragment mapsFragment = new MapsFragment();
        mapsFragment.setArguments(bd);
        adapter.addFrag(mapsFragment, "Map");

        RouteFragment routeFragment = new RouteFragment();
        routeFragment.setArguments(bd);
        adapter.addFrag( routeFragment, "Route");




        viewPager.setAdapter(adapter);
    }

}

