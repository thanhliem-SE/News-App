package com.liem.newsapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.liem.newsapp.R;
import com.liem.newsapp.adapter.DanhMucAdaper;
import com.liem.newsapp.models.DanhMuc;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    private DanhMucAdaper mDanhMucAdapter;
    private ArrayList<DanhMuc> mListDanhMuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUI();

    }

    private void initializeUI() {
        recyclerView = findViewById(R.id.activityMain_RecyclerView);
        mListDanhMuc = getListDanhMuc();
        mDanhMucAdapter = new DanhMucAdaper(this, mListDanhMuc);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mDanhMucAdapter);

    }

    private ArrayList<DanhMuc> getListDanhMuc() {
        ArrayList<DanhMuc> listDanhMuc = new ArrayList<>();
        listDanhMuc.add(new DanhMuc("Nổi bật", "https://vnexpress.net/rss/tin-noi-bat.rss"));
        listDanhMuc.add(new DanhMuc("Mới nhất", "https://vnexpress.net/rss/tin-moi-nhat.rss"));
        listDanhMuc.add(new DanhMuc("Thế giới", "https://vnexpress.net/rss/the-gioi.rss"));
        listDanhMuc.add(new DanhMuc("Startup", "https://vnexpress.net/rss/startup.rss"));
        listDanhMuc.add(new DanhMuc("Giải trí", "https://vnexpress.net/rss/giai-tri.rss"));
        listDanhMuc.add(new DanhMuc("Thể thao", "https://vnexpress.net/rss/the-thao.rss"));
        listDanhMuc.add(new DanhMuc("Pháp luật", "https://vnexpress.net/rss/phap-luat.rss"));
        listDanhMuc.add(new DanhMuc("Giáo dục", "https://vnexpress.net/rss/giao-duc.rss"));
        listDanhMuc.add(new DanhMuc("Sức khỏe", "https://vnexpress.net/rss/suc-khoe.rss"));
        listDanhMuc.add(new DanhMuc("Đời sống", "https://vnexpress.net/rss/doi-song.rss"));
        listDanhMuc.add(new DanhMuc("Khoa học", "https://vnexpress.net/rss/khoa-hoc.rss"));
        listDanhMuc.add(new DanhMuc("Kinh doanh", "https://vnexpress.net/rss/kinh-doanh.rss"));
        listDanhMuc.add(new DanhMuc("Tâm sự", "https://vnexpress.net/rss/tam-su.rss"));
        listDanhMuc.add(new DanhMuc("Số hóa", "https://vnexpress.net/rss/so-hoa.rss"));
        listDanhMuc.add(new DanhMuc("Du lịch", "https://vnexpress.net/rss/du-lich.rss"));
        return listDanhMuc;
    }

}