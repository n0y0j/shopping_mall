package com.example.shoppingmall;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuyRecyclerAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {

    Button deleteButton;
    FirebaseFirestore DB = FirebaseFirestore.getInstance();
    private HashMap<String, ArrayList<String>> product;
    Context context;

    BuyRecyclerAdapter(HashMap<String, ArrayList<String>> product) {
        this.product = product;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.favorite_product_card, parent, false);
        FavoriteViewHolder viewHolder = new FavoriteViewHolder(view);

        deleteButton = view.findViewById(R.id.favorite_delete_btn);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteViewHolder holder, final int position) {
        if ( product.get("name").get(position).length() > 15 ) holder.name.setTextSize(15);
        holder.name.setText(product.get("name").get(position));
        holder.image.setImageResource(Integer.parseInt(product.get("image").get(position)));
        holder.price.setText(product.get("price").get(position));

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> data = new HashMap<>();
                data.put("buy", false);

                DB.collection("languages").document(holder.name.getText().toString()).update(data);

                // Buy에 삭제한 내용을 바로 반영하기 위해 재생성
                ((Activity)context).recreate();

            }
        });
    }

    @Override
    public int getItemCount() {
        return product.get("name").size();
    }
}
