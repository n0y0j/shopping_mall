package com.example.shoppingmall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;


import com.example.shoppingmall.Fragment.Buy;
import com.example.shoppingmall.Fragment.Favorite;
import com.example.shoppingmall.Fragment.Home;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    FragmentTransaction fragmentTransaction;
    BubbleNavigationLinearView bubbleNavigationLinearView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Actionbar를 이미 만들어둔 toolbar로 변경
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bubbleNavigationLinearView = findViewById(R.id.bottom_nav);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new Home());
        fragmentTransaction.commit();

        bubbleNavigationLinearView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {

                switch (position) {
                    case 0:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, new Home());
                        fragmentTransaction.commit();
                        break;
                    case 1:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, new Favorite());
                        fragmentTransaction.commit();
                        break;
                    case 2:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, new Buy());
                        fragmentTransaction.commit();
                        break;
                }
            }
        });

    }
}