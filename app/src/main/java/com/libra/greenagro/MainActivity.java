package com.libra.greenagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static ProductDao productDao;
    private CustomAdapter adapter;
    private RecyclerView recyclerViewview;
    private List<Product> pd_list;
    private SearchView searchView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewview = (RecyclerView) findViewById(R.id.recycler_view);
        searchView = findViewById(R.id.searchView);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);


        //DB=============================================================
        ProductDB database = Room.databaseBuilder(getApplicationContext(), ProductDB.class,"Product_DB")
                .fallbackToDestructiveMigration() //스키마 버전 변경 가능
                .allowMainThreadQueries() //Main Thread에서 DB에 IO 가능하게 함
                .build();

        productDao = database.productDao();

        //리사이클러뷰===============================================================
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerViewview.setLayoutManager(llm);
        pd_list = productDao.gettAll();

        adapter = new CustomAdapter(pd_list);
        recyclerViewview.setAdapter(adapter);

        //===========================================================================

        //검색기능
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Product> list = new ArrayList<>();
                list = productDao.getByName("%"+s+"%");
                adapter.setList(list);
                return true;
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_add:
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pd_list = productDao.gettAll();
        adapter.setList(pd_list);
    }
}