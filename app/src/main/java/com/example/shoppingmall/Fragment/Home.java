package com.example.shoppingmall.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppingmall.Fragment.HomeWidget.HomeRecyclerAdapter;
import com.example.shoppingmall.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Home extends Fragment {

    HashMap<String, ArrayList<String>> product = getProduct();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 상품 객체 생성

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.home_recyclerview);
        LinearLayoutManager manager = new GridLayoutManager(getActivity(), 2);

        HomeRecyclerAdapter adapter = new HomeRecyclerAdapter(product);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        return view;
    }

    // AsyncTask<doInBackground()의 변수 종류, onProgressUpdate()에서 사용할 변수 종류, onPostExecute()에서 사용할 변수종류>
    //           execute시 사용하는 인자의 변수 종류, 변하는 값의 변수 종류, return 값의 변수 종류
    private class CodeCrawling extends AsyncTask<String, Void, ArrayList<String>> {

        // 백그라운드 작업이 시작되기 전 호출
        // 메소드 준비작업을 하는 장소
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // 실제 동작하는 부분
        @Override
        protected ArrayList<String> doInBackground(String... url) {
            // 크롤링한 데이터를 저장하고 return할 리스트
            ArrayList<String> items = new ArrayList<String>();

            try {
                // TIOBE 사이트의 HTML 정보를 가져옴
                Document doc = Jsoup.connect(url[0]).get();
                // table -> tbodt -> tr 태그의 정보를 저장
                Elements elements = doc.select("table tbody tr");

                // 많이 사용되는 언어의 1위부터 15위까지의 정보만 저장
                int count = 0;
                for(Element elem : elements) {
                    if (count < 20) {
                        Elements item_temp = elem.select("td");
                        // 프로그래밍 언어의 이름과 Ratings을 스트링으로 저장
                        // String을 Slicing하여 사용할 정보만 저장함
                        String name = item_temp.eq(3).toString().replace("<td>", "").replace("</td>", "");
                        String ratings = item_temp.eq(4).toString().replace("<td>", "").replace("</td>", "")
                                .replace(".", "").replace("%", "");
                        count++;
                        items.add(name);
                        items.add(ratings);
                    }
                    else break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return items;
        }


        // 백그라운드 작업이 완료 후 결과값을 얻는다.
        @Override
        protected void onPostExecute(ArrayList<String> aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private HashMap<String, ArrayList<String>> getProduct() {
        HashMap<String, ArrayList<String>> product = new HashMap<String, ArrayList<String>>();
        ArrayList<String> item = new ArrayList<String>();

        ArrayList<String> name = new ArrayList<String>();
        ArrayList<String> price = new ArrayList<String>();

        CodeCrawling c1 = new CodeCrawling();

        try {
            // CodeCrawling 객체를 생성하고 값을 받아옴
            // 프로그래밍 언어의 이름들을 저장하고 있는 ArrayList<String>
            item = c1.execute("https://www.tiobe.com/tiobe-index/").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       // 상품의 가격들을 랜덤으로 생성
        for (int i=0; i<item.size(); i+=2) {
            name.add(item.get(i));
            int product_price = Integer.parseInt(item.get(i+1)) * 100;

            price.add(Integer.toString(product_price));
        }

        product.put("name", name);
        product.put("price", price);

        return product;
    }


}