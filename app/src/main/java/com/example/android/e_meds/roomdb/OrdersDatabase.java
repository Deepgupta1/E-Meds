package com.example.android.e_meds.roomdb;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.android.e_meds.models.CartModel;
import com.example.android.e_meds.models.MyOrderModel;

@Database(entities = MyOrderModel.class,exportSchema = false,version = 1)
public abstract class OrdersDatabase extends RoomDatabase {

    private static final String DB_NAME = "OrderDb";
    private static OrdersDatabase instance;

    public static synchronized OrdersDatabase getDB(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context, OrdersDatabase.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract OrderDao orderDao();
}
