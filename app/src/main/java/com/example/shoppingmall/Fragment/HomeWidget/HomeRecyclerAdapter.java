package com.example.shoppingmall.Fragment.HomeWidget;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingmall.R;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private HashMap<String, ArrayList<String>> product;

    public HomeRecyclerAdapter(HashMap<String, ArrayList<String>> product) {
        this.product = product;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       Context context = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.home_product_card, parent, false);
        HomeViewHolder viewHolder = new HomeViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.name.setText(product.get("name").get(position));
        holder.price.setText(product.get("price").get(position));

    }

    @Override
    public int getItemCount() {
        return product.get("name").size();
    }
}
