package com.example.android.e_meds.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.e_meds.R;
import com.example.android.e_meds.models.CartModel;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Context context;
    ArrayList<CartModel> arrayList = new ArrayList<>();
    View view;

    public CartAdapter(Context context, ArrayList<CartModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        Log.d("CartAdapter--inCons",arrayList.size()+"");
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("CartAdapter--inCreate",arrayList.size()+"");
        view = LayoutInflater.from(context).inflate(R.layout.medicine_adapter, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Log.d("CartAdapter--",arrayList.size()+"");
        holder.nameMed.setText(arrayList.get(position).getItemName());
        holder.discountMed.setText("Discount: "+arrayList.get(position).getDiscount()+"%");
        holder.packMed.setText("Pack: "+arrayList.get(position).getPack());
        holder.typeMed.setText("For: "+arrayList.get(position).getType());
        holder.rateMed.setText("Price: \u20B9"+arrayList.get(position).getRate()+"");



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView rateMed, packMed, discountMed, typeMed, nameMed;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            nameMed = itemView.findViewById(R.id.nameMedtxt);
            rateMed = itemView.findViewById(R.id.rateMed);
            packMed = itemView.findViewById(R.id.PackMed);
            discountMed = itemView.findViewById(R.id.DiscountMed);
            typeMed = itemView.findViewById(R.id.typeMed);
        }
    }
}
