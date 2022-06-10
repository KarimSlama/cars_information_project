package com.example.cars_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AccessDatabase database;
    private Toolbar toolbar;
    private FloatingActionButton btnFloating;
    private RecyclerViewAdapter adapter;

    //This request code for add process
    public static final int ADD_CAR_REQUEST_CODE = 1;
    //But this for edit
    public static final int EDIT_CAR_REQUEST_CODE = 1;
    public static final String CAR_KEY = "car_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        btnFloating = findViewById(R.id.btnFloating);

        database = AccessDatabase.getInstance(this);
        database.openDatabase();
        ArrayList<Cars> carsList = database.getAllCars();
        database.closeDatabase();
        adapter = new RecyclerViewAdapter(carsList, new OnRecyclerViewListener() {
            @Override
            public void onItemClickListener(int car_id) {
                Intent intent = new Intent(getBaseContext(), CarViewActivity.class);
                //I use putExtra() to send the car_id
                intent.putExtra(CAR_KEY, car_id);
                startActivityForResult(intent, EDIT_CAR_REQUEST_CODE);
            }//end onItemClickListener()
        });
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        btnFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), CarViewActivity.class);
                startActivityForResult(intent, ADD_CAR_REQUEST_CODE);
            }//end onClick()
        });

    }//end onCreate()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_menu).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                querySearch(query);
                return false;
            }//end onQueryTextSubmit()

            @Override
            public boolean onQueryTextChange(String newText) {
                querySearch(newText);
                return false;
            }//end onQueryTextChange
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                database.openDatabase();
                ArrayList<Cars> allCars = database.getAllCars();
                database.closeDatabase();
                adapter.setCarsList(allCars);
                adapter.notifyDataSetChanged();
                return false;
            }//end onClose()
        });
        return true;
    }//end onCreateOptionsMenu()

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }//end onOptionsItemSelected()

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CAR_REQUEST_CODE || requestCode == EDIT_CAR_REQUEST_CODE) {
            database.openDatabase();
            ArrayList<Cars> carsList = database.getAllCars();
            database.closeDatabase();
            adapter.setCarsList(carsList);
            adapter.notifyDataSetChanged();
        }//end if()
    }//end onActivityResult()

    public void querySearch(String query) {
        database.openDatabase();
        ArrayList<Cars> carsList = database.searchCars(query);
        database.closeDatabase();
        adapter.setCarsList(carsList);
        adapter.notifyDataSetChanged();
    }//end cars()
}//end class