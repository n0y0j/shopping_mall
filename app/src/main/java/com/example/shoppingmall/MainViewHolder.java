package com.example.shoppingmall;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MainViewHolder extends RecyclerView.ViewHolder{

    CardView cardView;
    TextView name;
    TextView price;
    ImageView image;
    ImageView favorite;

    public MainViewHolder(@NonNull View itemView) {
        super(itemView);

        cardView = itemView.findViewById(R.id.main_cardview);
        name = itemView.findViewById(R.id.product_name);
        price = itemView.findViewById(R.id.product_price);
        image = itemView.findViewById(R.id.product_image);
        favorite = itemView.findViewById(R.id.product_favorite);
    }



}
