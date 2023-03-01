package com.example.android.e_meds.models;

public class MedicineModel {

    String id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MedicineModel(String id, String itemName, String pack, float rate, int discount, String type) {
        this.itemName = itemName;
        this.pack = pack;
        this.rate = rate;
        this.Discount = discount;
        this.type = type;
        this.id = id;
    }

    public MedicineModel() {

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
