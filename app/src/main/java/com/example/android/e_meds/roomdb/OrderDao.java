package com.example.android.e_meds.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;



import com.example.android.e_meds.models.MyOrderModel;

import java.util.List;

@Dao
public interface OrderDao {

    @Insert
    void insert(MyOrderModel myOrderModel);

    @Update
    void update(MyOrderModel myOrderModel);

    @Delete
    void delete(MyOrderModel myOrderModel);

    @Query("DELETE FROM order_table")
    void deleteAllCourses();

    @Query("SELECT * FROM order_table ORDER BY itemName ASC")
    List<MyOrderModel> getAllCourses();
}
