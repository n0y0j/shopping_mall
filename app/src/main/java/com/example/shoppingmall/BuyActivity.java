package com.example.shoppingmall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

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

public class BuyActivity extends AppCompatActivity {

    FirebaseFirestore DB = FirebaseFirestore.getInstance();
    HashMap<String, ArrayList<String>> product = getProduct();
    RecyclerView recyclerView;
    public static TextView totalPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        Button backButton = findViewById(R.id.back_btn);
        Button buyButton = findViewById(R.id.buy_buy_btn);
        totalPrice = findViewById(R.id.total_price);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView = findViewById(R.id.buy_recyclerview);
                BuyRecyclerAdapter adapter = new BuyRecyclerAdapter(product);
                LinearLayoutManager manager = new GridLayoutManager(getApplicationContext(),1);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(manager);
            }
        },1000);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<product.get("name").size(); i++) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("buy", false);
                    data.put("check", false);

                    DB.collection("languages").document(product.get("name").get(i)).update(data);
                }

                finish();
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<product.get("name").size(); i++) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("buy", false);
                    data.put("check", false);

                    DB.collection("languages").document(product.get("name").get(i)).update(data);
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "구매가 완료되었습니다", Toast.LENGTH_SHORT).show();
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

                        if (document.getData().get("buy").toString() == "true") {
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
