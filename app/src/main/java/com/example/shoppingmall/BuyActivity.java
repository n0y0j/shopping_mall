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

public class BuyActivity extends AppCompatActivity {

    FirebaseFirestore DB = FirebaseFirestore.getInstance();
    HashMap<String, ArrayList<String>> product = getProduct();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        Button backButton = findViewById(R.id.back_btn);
        Button buyButton = findViewById(R.id.buy_buy_btn);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView = findViewById(R.id.buy_recyclerview);
                BuyRecyclerAdapter adapter = new BuyRecyclerAdapter(product);
                LinearLayoutManager manager = new GridLayoutManager(getApplicationContext(),1);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(manager);

                // CheckBox의 Check 여부를 확인하여 가격을 바꿈
                // ViewHolder를 만드는데 시간이 소요되기 때문에 조금 지연시킨 후 실행
                // 그렇지 않고 바로 실행하면 null
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final TextView totalPrice = findViewById(R.id.total_price);

                        if ( recyclerView.getChildCount() != 0 ) {
                            final int[] sum = {0};
                            for (int i=0; i<recyclerView.getChildCount(); i++) {
                                View card = recyclerView.getChildAt(i);
                                final CheckBox buyCheck = card.findViewById(R.id.check);
                                TextView productTemp = card.findViewById(R.id.product_price);
                                final String productPrice = productTemp.getText().toString().replace("원", "");
                                Log.d("asd", productPrice);


                                buyCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            int price = Integer.parseInt(productPrice);
                                            Log.d("asd", Integer.toString(price));
                                            sum[0] += price;
                                            totalPrice.setText(Integer.toString(sum[0]));
                                        }
                                        else {
                                            int price = Integer.parseInt(productPrice);
                                            sum[0] -= price;
                                            totalPrice.setText(Integer.toString(sum[0]));
                                        }
                                    }
                                });
                            }
                        }
                    }
                }, 100);


            }
        },2000);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
