package com.example.shoppingmall.Fragment.HomeWidget;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingmall.R;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    Context context;

    private HashMap<String, ArrayList<String>> product;
    private int[] image = {
            R.drawable.c, R.drawable.java, R.drawable.python, R.drawable.cpp, R.drawable.c_sharp,
            R.drawable.visual_basic, R.drawable.javascript, R.drawable.php, R.drawable.r, R.drawable.sql,
            R.drawable.perl, R.drawable.groovy, R.drawable.ruby, R.drawable.go, R.drawable.matlab,
            R.drawable.swift, R.drawable.assembly_language, R.drawable.object_c, R.drawable.classic_visual_basic, R.drawable.pl_sql
    };

    public HomeRecyclerAdapter(HashMap<String, ArrayList<String>> product) {
        this.product = product;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       context = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.home_product_card, parent, false);
        HomeViewHolder viewHolder = new HomeViewHolder(view);

        return viewHolder;
    }

    // setImageResource의 접근 R.drawable.c이 아닌 int형으로 접근할 수 있게 해준다.
    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, final int position) {
        if ( product.get("name").get(position).length() > 15 ) holder.name.setTextSize(15);
        holder.name.setText(product.get("name").get(position));
        holder.image.setImageResource(image[position]);
        holder.price.setText(product.get("price").get(position) + "원");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View DetailView = LayoutInflater.from(context).inflate(R.layout.home_product_detail, null);

                TextView name1 = (TextView) DetailView.findViewById(R.id.product_name2);
                TextView price1 = (TextView) DetailView.findViewById(R.id.product_price2);
                ImageView image1 = (ImageView) DetailView.findViewById(R.id.product_image2);

                name1.setText(product.get("name").get(position));
                image1.setImageResource(image[position]);
                price1.setText(product.get("price").get(position) + "원");

                builder.setView(DetailView);
                builder.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return product.get("name").size();
    }
}
