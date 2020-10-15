package com.example.shoppingmall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

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
    Button buyButton;
    Button homeButton;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView = findViewById(R.id.favorite_recyclerview);
                FavoriteRecyclerAdapter adapter = new FavoriteRecyclerAdapter(product);
                LinearLayoutManager manager = new GridLayoutManager(getApplicationContext(),1);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(manager);
            }
        },2000);

        Button backButton = findViewById(R.id.back_btn);
        buyButton = findViewById(R.id.favorite_buy_btn);
        homeButton = findViewById(R.id.favorite_home_btn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( recyclerView.getChildCount() != 0 ) {
                    for (int i=0; i<recyclerView.getChildCount(); i++) {
                        View card = recyclerView.getChildAt(i);
                        CheckBox buyCheck = card.findViewById(R.id.check);
                        TextView productTemp = card.findViewById(R.id.product_name);
                        String productName = productTemp.getText().toString();


                        if (buyCheck.isChecked() == true) {
                            Map<String, Object> data = new HashMap<>();
                            // buy를 true로 업데이트
                            data.put("buy", true);
                            DB.collection("languages").document(productName).update(data);

                        }
                    }
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

//                            Log.d("TAG", document.getId() + " = > " + document.getData().get("image"));
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
