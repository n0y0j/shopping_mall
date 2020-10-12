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

    public MainViewHolder(@NonNull View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.product_name);
        price = (TextView) itemView.findViewById(R.id.product_price);
        image = (ImageView) itemView.findViewById(R.id.product_image);
    }

}
