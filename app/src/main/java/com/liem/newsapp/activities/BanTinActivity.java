package com.liem.newsapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.liem.newsapp.R;
import com.liem.newsapp.adapter.TinTucAdaper;
import com.liem.newsapp.models.TinTuc;
import com.liem.newsapp.utils.XMLDOMParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BanTinActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<TinTuc> mListTinTuc;
    private TinTucAdaper mLinTucAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_tin);

        InitializeUI();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new getListTinTuc().execute(getIntent().getStringExtra("url"));
            }
        });

    }

    private void InitializeUI() {
        mRecyclerView = findViewById(R.id.activityBanTin_recyclerView);
        mListTinTuc = new ArrayList<>();
        mLinTucAdaper = new TinTucAdaper(this, mListTinTuc);

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayout);
        mRecyclerView.setAdapter(mLinTucAdaper);
    }

    class getListTinTuc extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            return getData(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeListItem = document.getElementsByTagName("item");
            NodeList nodeListDescripton = document.getElementsByTagName("description");
            for (int i = 0; i < nodeListItem.getLength(); i++) {
                Element element = (Element) nodeListItem.item(i);

                String title = parser.getValue(element, "title");
                String link = parser.getValue(element, "link");
                String img = "";
                String description = nodeListDescripton.item(i + 1).getTextContent();

                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher = p.matcher(description);
                if (matcher.find())
                    img = matcher.group(1);

                TinTuc tinTuc = new TinTuc(title, link, img);
                mListTinTuc.add(tinTuc);
            }
            mLinTucAdaper.notifyDataSetChanged();
            super.onPostExecute(s);
        }

        protected String getData(String theUrl) {
            StringBuilder content = new StringBuilder();
            try {
                // create a url object
                URL url = new URL(theUrl);
                // create a urlconnection object
                URLConnection urlConnection = url.openConnection();
                // wrap the urlconnection in a bufferedreader
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                // read from the urlconnection via the bufferedreader
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line + "\n");
                }
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return content.toString();
        }
    }
}