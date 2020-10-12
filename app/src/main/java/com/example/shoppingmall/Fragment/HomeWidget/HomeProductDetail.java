package com.example.shoppingmall.Fragment.HomeWidget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.shoppingmall.Fragment.Buy;
import com.example.shoppingmall.Fragment.Home;
import com.example.shoppingmall.MainActivity;
import com.example.shoppingmall.R;

public class HomeProductDetail extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_product_detail);

        TextView detail_name = findViewById(R.id.detail_product_name);
        TextView detail_price = findViewById(R.id.detail_product_price);
        ImageView detail_image = findViewById(R.id.detail_product_image);

        Button buy_btn = findViewById(R.id.product_buy_btn);
        Button favorite_btn = findViewById(R.id.product_favorite_btn);

        Intent intent = getIntent();

        detail_name.setText(intent.getExtras().getString("detail_name"));
        detail_image.setImageResource(intent.getExtras().getInt("detail_image"));
        detail_price.setText(intent.getExtras().getString("detail_price"));

//        buy_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MainActivity m = new MainActivity();
//                m.replaceFragment(new Buy());
//            }
//        });
    }
}
