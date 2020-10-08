package com.example.shoppingmall.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.DocumentsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppingmall.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class Home extends Fragment {

    final StringBuilder builder = new StringBuilder();
    String[] items = new String[10];

    // AsyncTask<doInBackground()의 변수 종류, onProgressUpdate()에서 사용할 변수 종류, onPostExecute()에서 사용할 변수종류>
    private class CodeCrawling extends AsyncTask<String, Void, Void> {

        // 백그라운드 작업이 시작되기 전 호출
        // 메소드 준비작업을 하는 장소
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // 실제 동작하는 부분
        @Override
        protected Void doInBackground(String... url) {

            try {
                Document doc = Jsoup.connect(url[0]).get();
                Elements elements = doc.select("table tbody tr");

                Log.d("TAG", doc.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        // 사용자에게 진행을 알릴때 사용
        @Override
        protected void onProgressUpdate(Void... values) {
        }

        // 백그라운드 작업이 완료 후 결과값을 얻는다.
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        CodeCrawling c1 = new CodeCrawling();

        c1.execute("https://www.tiobe.com/tiobe-index/");





        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}