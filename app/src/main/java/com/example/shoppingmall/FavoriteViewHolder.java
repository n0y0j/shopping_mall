package com.example.shoppingmall;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView price;
    ImageView image;

    public FavoriteViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.product_name);
        price = itemView.findViewById(R.id.product_price);
        image = itemView.findViewById(R.id.product_image);
    }
}
