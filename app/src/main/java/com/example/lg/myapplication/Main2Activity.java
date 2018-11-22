package com.example.lg.myapplication;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private final String[] items = {"프로필", "마이페이지", "채팅"};
    private ListView mainlist;
    private DrawerLayout drawerLayout;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    Button btn;
    TextView title, date, start, fin;
    Toolbar tool_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        drawerLayout = findViewById(R.id.drawer);

        tool_bar = findViewById(R.id.toolbar);
        setSupportActionBar(tool_bar); // 툴바를 액션바로 대체

        getSupportActionBar().setTitle("Main");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        mainlist = (ListView) findViewById(R.id.main_list);
        mainlist.setAdapter(adapter);

        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        mainlist.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                switch (position) {
                    case 0 :
                        Intent myIntent = new Intent(getApplicationContext(), profile.class);
                        startActivity(myIntent);
                    case 1:
                    case 2 :
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
                drawer.closeDrawer(Gravity.RIGHT);
            }
        });

        Spinner spinner = (Spinner)findViewById(R.id.spinner);

        title = (TextView)findViewById(R.id.title);
        date = (TextView)findViewById(R.id.date);
        start = (TextView)findViewById(R.id.start);
        fin = (TextView)findViewById(R.id.fin);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<RecyclerItem> item = new ArrayList<>();
        item.add(new RecyclerItem("제목입니다.", "12/31", "인천", "대전"));
        item.add(new RecyclerItem("학교 같이 다니실 분~", "9/4", "서울", "인천"));

        mAdapter = new MyAdapter(item);
        mRecyclerView.setAdapter(mAdapter);
    }
}


