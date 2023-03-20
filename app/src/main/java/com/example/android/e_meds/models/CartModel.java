package com.example.android.e_meds.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_table")
public class CartModel {

    @PrimaryKey(autoGenerate = true)
    int id;
    String itemName;
    String pack;
    float rate;
    int Discount;
    String type;
    int total;
    int quant;

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

    public CartModel(String itemName, String pack, float rate, int discount, String type,int total,int quant) {
        this.itemName = itemName;
        this.pack = pack;
        this.rate = rate;
        this.Discount = discount;
        this.type = type;
        this.total=total;
        this.quant=quant;

    }

    public CartModel(int id,String itemName, String pack, float rate, int discount, String type,int total,int quant) {
        this.id=id;
        this.itemName = itemName;
        this.pack = pack;
        this.rate = rate;
        this.Discount = discount;
        this.type = type;
        this.total=total;
        this.quant=quant;

    }


    public CartModel() {

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }
}
