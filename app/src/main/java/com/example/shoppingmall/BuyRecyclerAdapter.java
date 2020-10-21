package com.example.shoppingmall;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

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
    int sum = 0;

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
        holder.check.setChecked(false);

        // item의 checkBox가 true인지 false인지 확인하여 총 값을 계산한다.
        // true라면 item의 price를 총 값에 더하고, true->false로 변환 시 뺀다
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Map<String, Object> data = new HashMap<>();
                if (isChecked) {
                    data.put("check", true);
                    DB.collection("languages").document(holder.name.getText().toString()).update(data);

                    String productPrice = holder.price.getText().toString().replace("원", "");
                    int price = Integer.parseInt(productPrice);

                    sum += price;
                    BuyActivity.totalPrice.setText(Integer.toString(sum) + "원");
                }
                else {
                    data.put("check", false);
                    DB.collection("languages").document(holder.name.getText().toString()).update(data);

                    String productPrice = holder.price.getText().toString().replace("원", "");
                    int price = Integer.parseInt(productPrice);

                    sum -= price;
                    BuyActivity.totalPrice.setText(Integer.toString(sum) + "원");
                }
            }
        });

        // 삭제버튼 클릭 item 삭제
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
