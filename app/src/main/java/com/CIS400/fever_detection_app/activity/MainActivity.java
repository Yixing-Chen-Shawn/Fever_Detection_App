package com.CIS400.fever_detection_app.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.CIS400.fever_detection_app.R;
import com.CIS400.fever_detection_app.fragments.fitnessFragment;
import com.CIS400.fever_detection_app.fragments.heartRateFragment;
import com.CIS400.fever_detection_app.fragments.homeFragment;
import com.CIS400.fever_detection_app.fragments.meFragment;

import com.android.volley.RequestQueue;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private RequestQueue requestQueue;
    private ViewPager viewPager;

    private LinearLayout mTab1;
    private LinearLayout mTab2;
    private LinearLayout mTab3;
    private LinearLayout mTab4;

    private ImageButton mImg1;
    private ImageButton mImg2;
    private ImageButton mImg3;
    private ImageButton mImg4;

    private Fragment mFrag1;
    private Fragment mFrag2;
    private Fragment mFrag3;
    private Fragment mFrag4;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initViews(); //initialize layout n image button
        initEvents(); //initialize events
        selectTab(0); // select tab0 as default
    }

    private void initEvents(){
        mTab1.setOnClickListener(this);
        mTab2.setOnClickListener(this);
        mTab3.setOnClickListener(this);
        mTab4.setOnClickListener(this);
    }

    private void initViews(){
        //Initialize four layouts
        mTab1 = (LinearLayout) findViewById(R.id.id_tab1);
        mTab2 = (LinearLayout) findViewById(R.id.id_tab2);
        mTab3 = (LinearLayout) findViewById(R.id.id_tab3);
        mTab4 = (LinearLayout) findViewById(R.id.id_tab4);

        //initialize four ImageButtons
        mImg1 = (ImageButton) findViewById(R.id.id_tab1_img);
        mImg2 = (ImageButton) findViewById(R.id.id_tab2_img);
        mImg3 = (ImageButton) findViewById(R.id.id_tab3_img);
        mImg4 = (ImageButton) findViewById(R.id.id_tab4_img);
    }

    @Override
    public void onClick(View v){
        resetImgs();
        switch (v.getId()) {
            case R.id.id_tab1:
                selectTab(0);
                break;
            case R.id.id_tab2:
                selectTab(1);
                break;
            case R.id.id_tab3:
                selectTab(2);
                break;
            case R.id.id_tab4:
                selectTab(3);
                break;
        }
    }

    private void selectTab(int i){
        //Obtain FragmentManager Object
        FragmentManager manager = getSupportFragmentManager();
        //Obtain FragmentTransaction object
        FragmentTransaction transaction = manager.beginTransaction();
        //hide all the fragments first (hide+show > replace)
        hideFragments(transaction);
        switch (i) {
            case 0:
                mImg1.setImageResource(R.mipmap.ic_menu_home_on);
                //if the tab1 fragment is not instantiated, instantiate it and add into transaction
                if (mFrag1 == null) {
                    mFrag1 = new homeFragment();
                    transaction.add(R.id.id_content, mFrag1, "f1");
                } else {
                    transaction.show(mFrag1);
                }
                break;
            case 1:
                mImg2.setImageResource(R.mipmap.ic_menu_heart_on);
                if (mFrag2 == null) {
                    mFrag2 = new heartRateFragment();
                    transaction.add(R.id.id_content, mFrag2, "f2");
                } else {
                    transaction.show(mFrag2);
                }
                break;
            case 2:
                mImg3.setImageResource(R.mipmap.ic_menu_distance_on);
                if (mFrag3 == null) {
                    mFrag3 = new fitnessFragment();
                    transaction.add(R.id.id_content, mFrag3, "f3");
                } else {
                    transaction.show(mFrag3);
                }
                break;
            case 3:
                mImg4.setImageResource(R.mipmap.ic_menu_user_on);
                if (mFrag4 == null) {
                    mFrag4 = new meFragment();
                    transaction.add(R.id.id_content, mFrag4, "f4");
                } else {
                    transaction.show(mFrag4);
                }
                break;
        }
        //commit the transaction
        transaction.commit();
    }

    //hide all four fragments
    private void hideFragments(FragmentTransaction transaction) {
        if (mFrag1 != null) {
            transaction.hide(mFrag1);
        }
        if (mFrag2 != null) {
            transaction.hide(mFrag2);
        }
        if (mFrag3 != null) {
            transaction.hide(mFrag3);
        }
        if (mFrag4 != null) {
            transaction.hide(mFrag4);
        }
    }

    private void resetImgs() {
        mImg1.setImageResource(R.mipmap.ic_menu_home_off);
        mImg2.setImageResource(R.mipmap.ic_menu_heart_off);
        mImg3.setImageResource(R.mipmap.ic_menu_distance_off);
        mImg4.setImageResource(R.mipmap.ic_menu_user_off);
    }
}