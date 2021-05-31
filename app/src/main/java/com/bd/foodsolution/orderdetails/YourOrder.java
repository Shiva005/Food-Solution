package com.bd.foodsolution.orderdetails;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bd.foodsolution.Adapter.YourOrderAdapter;
import com.bd.foodsolution.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class YourOrder extends AppCompatActivity {
    private RecyclerView recyclerView;
    private YourOrderAdapter adapter;
    private ProgressBar mProgressBar;
    private DatabaseReference mDataBaseRe;
    private List<OrderGetterSetter> mList;
    private Toolbar toolbar;
    private ConnectivityManager connectivityManager;
    boolean connected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_order);

        recyclerView = findViewById(R.id.recyclerViewPapers_id);
        mProgressBar = findViewById(R.id.progressbar_papersId);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        toolbar = findViewById(R.id.toolbar_papers_view);
        setSupportActionBar(toolbar);
        setTitle("Your Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mList = new ArrayList<>();

        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;

        if (connected == true) {
            try {
                mDataBaseRe = FirebaseDatabase.getInstance("https://food-solution-a5cbb-default-rtdb.firebaseio.com/").getReference("Order");
                FetchOrders();
            } catch (Exception e) {

            }
        } else {
            Snackbar.make(findViewById(R.id.SnackIdd), "Check Internet Connection !!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }

    private void FetchOrders() {
        mDataBaseRe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot pstSnapshot : dataSnapshot.getChildren()) {
                    OrderGetterSetter upload = pstSnapshot.getValue(OrderGetterSetter.class);
                    mList.add(upload);
                }
                adapter = new YourOrderAdapter(getApplicationContext(), mList);
                recyclerView.setAdapter(adapter);
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
