package com.example.shoppingmall;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {

    FirebaseFirestore DB = FirebaseFirestore.getInstance();
    private HashMap<String, ArrayList<String>> product;
    Context context;




    public FavoriteRecyclerAdapter(HashMap<String, ArrayList<String>> product) {
        this.product = product;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.favorite_product_card, parent, false);
        FavoriteViewHolder viewHolder = new FavoriteViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        holder.name.setText(product.get("name").get(position));
        holder.image.setImageResource(Integer.getInteger(product.get("image").get(position)));
        holder.price.setText(product.get("price").get(position) + "원");
    }

    @Override
    public int getItemCount() {
        return product.get("name").size();
    }
}
