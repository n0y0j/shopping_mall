package com.example.shoppingmall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;


import com.example.shoppingmall.Fragment.Buy;
import com.example.shoppingmall.Fragment.Favorite;
import com.example.shoppingmall.Fragment.Home;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    Buy buy;
    Favorite favorite;
    Home home;

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Actionbar를 이미 만들어둔 toolbar로 변경
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        buy = new Buy();
        favorite = new Favorite();
        home = new Home();

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(home, "Home");
        viewPagerAdapter.addFragment(favorite, "Favorite");
        viewPagerAdapter.addFragment(buy, "Buy");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.bottom_home_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.bottom_favorite_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.bottom_buy_icon);

    }
    



}