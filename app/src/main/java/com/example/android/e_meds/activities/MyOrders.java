package com.example.android.e_meds.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.android.e_meds.R;
import com.example.android.e_meds.adapter.CartAdapter;
import com.example.android.e_meds.adapter.OrderAdapter;
import com.example.android.e_meds.models.CartModel;
import com.example.android.e_meds.models.MyOrderModel;
import com.example.android.e_meds.roomdb.MedicineDatabase;
import com.example.android.e_meds.roomdb.OrdersDatabase;

import java.util.ArrayList;

public class MyOrders extends AppCompatActivity {
    RecyclerView orderRecycler;
    ArrayList<MyOrderModel> arrayList = new ArrayList<>();
    OrdersDatabase database;
    String tag = "MyOrder.java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        orderRecycler=findViewById(R.id.orderRecycler);
        database = OrdersDatabase.getDB(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        orderRecycler.setLayoutManager(layoutManager);
        arrayList = (ArrayList<MyOrderModel>) database.orderDao().getAllCourses();
        orderRecycler.setAdapter(new OrderAdapter(this, arrayList));

    }
}