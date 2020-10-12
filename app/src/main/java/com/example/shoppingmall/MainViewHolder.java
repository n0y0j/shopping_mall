package com.example.shoppingmall;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainViewHolder extends RecyclerView.ViewHolder{
    TextView name;
    TextView price;
    ImageView image;
    ImageView favorite;

    public MainViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.product_name);
        price = itemView.findViewById(R.id.product_price);
        image = itemView.findViewById(R.id.product_image);
        favorite = itemView.findViewById(R.id.product_favorite);
    }

}
