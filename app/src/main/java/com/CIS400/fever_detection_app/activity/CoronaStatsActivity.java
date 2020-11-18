package com.CIS400.fever_detection_app.activity;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.CIS400.fever_detection_app.R;
import com.CIS400.fever_detection_app.adapters.MyViewPagerAdapter;
import com.CIS400.fever_detection_app.fragments.globalListFragment;
import com.CIS400.fever_detection_app.fragments.stateListFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class CoronaStatsActivity extends BaseActivity  implements TabLayout.OnTabSelectedListener {

    private Toolbar toolbar;
    private ViewPager myViewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragments = new ArrayList<>();
    private MyViewPagerAdapter adapter;
    private String[] titles = {"US States","World"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corona_stats);
        init();
        initToolbar();
        myViewPager.setCurrentItem(0);
    }

    private void init(){
        tabLayout = findViewById(R.id.tabLayout);
        myViewPager = findViewById(R.id.vp_view);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (String tab:titles){
            tabLayout.addTab(tabLayout.newTab().setText(tab));
        }
        fragments.add(new stateListFragment());
        fragments.add(new globalListFragment());
        adapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        myViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(myViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu_corona, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchCountry:
                Fragment fragment = adapter.getCurrentFragment();
                if(fragment != null && fragment.isVisible() && fragment instanceof globalListFragment){
                    ((globalListFragment) fragment).initSearchCountry();
                }
                if(fragment != null && fragment.isVisible() && fragment instanceof stateListFragment){
                    ((stateListFragment)fragment).initSearchCountry();
                }
                break;
            case R.id.back:
                startActivity(new Intent(CoronaStatsActivity.this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar_home);
        toolbar.setTitle("Corona Statistics");
        setSupportActionBar(toolbar);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        myViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}