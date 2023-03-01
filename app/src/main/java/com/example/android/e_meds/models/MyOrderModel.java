package com.example.android.e_meds.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "order_table")
public class MyOrderModel {

    @PrimaryKey(autoGenerate = true)
    int id;
    String itemName;
    String pack;
    float rate;
    int Discount;
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MyOrderModel(String itemName, String pack, float rate, int discount, String type) {
        this.itemName = itemName;
        this.pack = pack;
        this.rate = rate;
        this.Discount = discount;
        this.type = type;

    }

    public MyOrderModel() {

    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }
}
