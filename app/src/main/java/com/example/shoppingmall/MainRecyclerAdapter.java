package com.example.shoppingmall;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SimpleTimeZone;


public class MainRecyclerAdapter extends RecyclerView.Adapter<MainViewHolder> {

    FirebaseFirestore DB = FirebaseFirestore.getInstance();
    Map<String, Object> data = new HashMap<>();
    Context context;

    private HashMap<String, ArrayList<String>> product;
    private int[] image = {
            R.drawable.c, R.drawable.java, R.drawable.python, R.drawable.cpp, R.drawable.c_sharp,
            R.drawable.visual_basic, R.drawable.javascript, R.drawable.php, R.drawable.r, R.drawable.sql,
            R.drawable.perl, R.drawable.groovy, R.drawable.ruby, R.drawable.go, R.drawable.matlab,
            R.drawable.swift, R.drawable.assembly_language, R.drawable.object_c, R.drawable.classic_visual_basic, R.drawable.pl_sql
    };

    public MainRecyclerAdapter(HashMap<String, ArrayList<String>> product) {
        this.product = product;
    }


    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.main_product_card, parent, false);
        MainViewHolder viewHolder = new MainViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, final int position) {

        if ( product.get("name").get(position).length() > 15 ) holder.name.setTextSize(15);
        holder.name.setText(product.get("name").get(position));
        holder.image.setImageResource(image[position]);
        holder.price.setText(product.get("price").get(position) + "원");

        if (DB.collection("languages").document(product.get("name").get(position)) == null) {
            data.put("image", image[position]);
            data.put("price", product.get("price").get(position) + "원");
            data.put("favorite", false);
            data.put("buy", false);
            data.put("time", System.currentTimeMillis());

            DB.collection("languages").document(product.get("name").get(position)).set(data);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.linearLayout.setVisibility(View.VISIBLE);

                MainActivity.favorite_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.put("favorite", true);

                        DB.collection("languages").document(product.get("name").get(position)).update(data);

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);

                        builder.setTitle("장바구니로 이동").setMessage("장바구니로 이동하시겠습니까?");

                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.linearLayout.setVisibility(View.GONE);
                                Intent intent = new Intent(context, FavoriteActivity.class);
                                context.startActivity(intent);
                            }
                        });

                        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                MainActivity.linearLayout.setVisibility(View.GONE);
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });

                MainActivity.buy_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.linearLayout.setVisibility(View.GONE);
                        Intent intent = new Intent(context, BuyActivity.class);
                        context.startActivity(intent);
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return product.get("name").size();
    }
}
