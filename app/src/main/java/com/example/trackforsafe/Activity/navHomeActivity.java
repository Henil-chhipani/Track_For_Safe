package com.example.trackforsafe.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.trackforsafe.Adapter.VPAdapter;
import com.example.trackforsafe.Fragment.Fragment_dashBoard;
import com.example.trackforsafe.Fragment.Fragment_home;
import com.example.trackforsafe.Fragment.Fragment_news;
import com.example.trackforsafe.R;
import com.google.android.material.tabs.TabLayout;

public class navHomeActivity extends AppCompatActivity {
    public TabLayout tabLayout;
    public ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_home);

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);
        tabLayout.setupWithViewPager(viewPager);

        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new Fragment_home(), "Home");
        vpAdapter.addFragment(new Fragment_dashBoard(), "Dashboard");
        vpAdapter.addFragment(new Fragment_news(), "News");
        viewPager.setAdapter(vpAdapter);


    }

}