package com.example.lg.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private final String[] items = {"프로필", "마이페이지", "채팅"};
    private ListView mainlist;
    private DrawerLayout drawerLayout;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    Button btn, write;
    TextView title, date, start, fin, check;
    Toolbar tool_bar;

    ArrayList<RecyclerItem> item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        drawerLayout = findViewById(R.id.drawer);

        tool_bar = findViewById(R.id.toolbar);
        setSupportActionBar(tool_bar); // 툴바를 액션바로 대체

        getSupportActionBar().setTitle("Main");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        mainlist = (ListView) findViewById(R.id.main_list);
        mainlist.setAdapter(adapter);

        btn = findViewById(R.id.btn);
        write = findViewById(R.id.write);

        btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        write.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), InfoWrite.class);
                startActivity(myIntent); // 액티비티 연결하는 코드
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

        item = new ArrayList<>();

        db.collection("board")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            item.add(new RecyclerItem(document.getString("title"), document.getString("time"), "SP", "FP"));
                            mAdapter = new MyAdapter(item);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    }
                });

        /*
        db.collection("board")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot task,
                                        @Nullable FirebaseFirestoreException e) {
                        for (QueryDocumentSnapshot document : task) {
                            if (document.get("name") != null) {
                                item.add(new RecyclerItem(document.getString("title"), document.getString("time"), "SP", "FP"));
                            }
                        }
                    }
                });
         */

 
    }
}


