package com.example.shoppingmall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;


public class MainRecyclerAdapter extends RecyclerView.Adapter<MainViewHolder> {

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

        View view = inflater.inflate(R.layout.home_product_card, parent, false);
        MainViewHolder viewHolder = new MainViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        if ( product.get("name").get(position).length() > 15 ) holder.name.setTextSize(15);
        holder.name.setText(product.get("name").get(position));
        holder.image.setImageResource(image[position]);
        holder.price.setText(product.get("price").get(position) + "원");

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, HomeProductDetail.class);
//
//                intent.putExtra("detail_name", product.get("name").get(position));
//                intent.putExtra("detail_image", image[position]);
//                intent.putExtra("detail_price", product.get("price").get(position) + "원");
//
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return product.get("name").size();
    }
}
