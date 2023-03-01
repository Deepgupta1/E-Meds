package com.example.android.e_meds.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.android.e_meds.models.CartModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CartDao {

    @Insert
    void insert(CartModel cartModel);

    @Update
    void update(CartModel cartModel);

    @Delete
    void delete(CartModel cartModel);

    @Query("DELETE FROM cart_table")
    void deleteAllCourses();

    @Query("SELECT * FROM cart_table ORDER BY itemName ASC")
    List<CartModel> getAllCourses();
}
