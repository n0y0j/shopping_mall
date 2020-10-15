package com.example.shoppingmall;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainRecyclerAdapter extends RecyclerView.Adapter<MainViewHolder> {

    FirebaseFirestore DB = FirebaseFirestore.getInstance();
    Map<String, Object> data = new HashMap<>();
    Context context;

    private HashMap<String, ArrayList<String>> product;
    // 프로그래밍 언어들의 로고가 순위별로 정렬
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

        View view = inflater.inflate(R.layout.main_product_card, parent, false);
        MainViewHolder viewHolder = new MainViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MainViewHolder holder, final int position) {

        // 각 프로그래밍 언어의 CardView를 생성
        if ( product.get("name").get(position).length() > 15 ) holder.name.setTextSize(15);
        holder.name.setText(product.get("name").get(position));
        holder.image.setImageResource(image[position]);
        holder.price.setText(product.get("price").get(position) + "원");
        holder.favorite.setImageResource(R.drawable.unfavorite_icon);

        // Firestore에 Data가 있는지 확인하고 없으면 삽입
        // 중복 삽입을 방지
        final DocumentReference documentReference = DB.collection("languages").document(product.get("name").get(position));
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.get("image") == null) {
                    data.put("image", image[position]);
                    data.put("price", product.get("price").get(position) + "원");
                    data.put("favorite", false);
                    data.put("buy", false);
                    data.put("check", false);

                    DB.collection("languages").document(product.get("name").get(position)).set(data);
                }

                if (documentSnapshot.get("favorite") != null) {
                    if (documentSnapshot.get("favorite").toString() == "true") {
                        holder.favorite.setImageResource(R.drawable.favorite_icon);

                    }
                }
            }
        });

        // CardView 클릭 시
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.linearLayout.setVisibility(View.VISIBLE);

                MainActivity.favorite_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> data = new HashMap<>();
                        // favorite을 true로 업데이트
                        data.put("favorite", true);

                        holder.favorite.setImageResource(R.drawable.favorite_icon);

                        DB.collection("languages").document(holder.name.getText().toString()).update(data);

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);

                        builder.setTitle("장바구니로 이동").setMessage("장바구니로 이동하시겠습니까?");

                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.linearLayout.setVisibility(View.GONE);
                                Intent intent = new Intent(context, FavoriteActivity.class);
                                context.startActivity(intent);
                            }
                        });

                        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                MainActivity.linearLayout.setVisibility(View.GONE);
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });

                MainActivity.buy_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> data = new HashMap<>();
                        // buy을 true로 업데이트
                        data.put("buy", true);

                        DB.collection("languages").document(product.get("name").get(position)).update(data);

                        MainActivity.linearLayout.setVisibility(View.GONE);
                        Intent intent = new Intent(context, BuyActivity.class);
                        context.startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return product.get("name").size();
    }
}
