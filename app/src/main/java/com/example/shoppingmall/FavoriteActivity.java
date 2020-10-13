package com.example.shoppingmall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FavoriteActivity extends AppCompatActivity {

    FirebaseFirestore DB = FirebaseFirestore.getInstance();
    HashMap<String, ArrayList<String>> product = getProduct();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                RecyclerView recyclerView = findViewById(R.id.favorite_recyclerview);
                FavoriteRecyclerAdapter adapter = new FavoriteRecyclerAdapter(product);
                LinearLayoutManager manager = new GridLayoutManager(getApplicationContext(),1);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(manager);
            }
        },2000);

        Button backButton = findViewById(R.id.back_btn);
        Button buyButton = findViewById(R.id.favorite_buy_btn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> data = new HashMap<>();
                // buy를 true로 업데이트
                data.put("buy", true);

                for (int i=0; i < product.get("name").size(); i++) {
                    DB.collection("languages").document(product.get("name").get(i)).update(data);
                }

                Intent intent = new Intent(getApplicationContext(), BuyActivity.class);
                startActivity(intent);
            }
        });
    }

    private HashMap<String, ArrayList<String>> getProduct() {
        final HashMap<String, ArrayList<String>> product = new HashMap<String, ArrayList<String>>();
        final ArrayList<String> name = new ArrayList<String>();
        final ArrayList<String> price = new ArrayList<String>();
        final ArrayList<String> image = new ArrayList<String>();

        DB.collection("languages").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        if (document.getData().get("favorite").toString() == "true") {
                            String product_name = document.getId();
                            String product_price = document.getData().get("price").toString();
                            String product_image = document.getData().get("image").toString();

                            name.add(product_name);
                            price.add(product_price);
                            image.add(product_image);

                            Log.d("TAG", document.getId() + " = > " + document.getData().get("image"));
                        }
                    }
                    product.put("name", name);
                    product.put("price", price);
                    product.put("image", image);

                }
            }
        });


        return product;
    }

}
