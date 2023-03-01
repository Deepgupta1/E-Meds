package com.example.android.e_meds.roomdb;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.android.e_meds.models.CartModel;
import com.example.android.e_meds.models.MedicineModel;

@Database(entities = CartModel.class,exportSchema = false,version = 1)
public abstract class MedicineDatabase extends RoomDatabase {

    private static final String DB_NAME = "cartDb";
    private static MedicineDatabase instance;

    public static synchronized MedicineDatabase getDB(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context,MedicineDatabase.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract CartDao cartDao();
}
