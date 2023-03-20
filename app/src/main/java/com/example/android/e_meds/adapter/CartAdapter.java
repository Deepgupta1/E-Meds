package com.example.android.e_meds.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.e_meds.R;
import com.example.android.e_meds.activities.MyCart;
import com.example.android.e_meds.models.CartModel;
import com.example.android.e_meds.models.MyOrderModel;
import com.example.android.e_meds.roomdb.MedicineDatabase;
import com.example.android.e_meds.roomdb.OrdersDatabase;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Context context;
    ArrayList<CartModel> arrayList = new ArrayList<>();
    View view;
    int discount;
    float rate;
    int quant, total;
    MedicineDatabase database;
    OrdersDatabase database2;

    public CartAdapter(Context context, ArrayList<CartModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        Log.d("CartAdapter--inCons", arrayList.size() + "");
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("CartAdapter--inCreate", arrayList.size() + "");
        view = LayoutInflater.from(context).inflate(R.layout.cart_adapter, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d("CartAdapter--", arrayList.size() + "");

        holder.deleteBtn.setVisibility(View.VISIBLE);
        database = MedicineDatabase.getDB(context);
        database2 = OrdersDatabase.getDB(context);


        discount = arrayList.get(position).getDiscount();
        rate = arrayList.get(position).getRate();
        quant = arrayList.get(position).getQuant();
        holder.nameMed.setText(arrayList.get(position).getItemName());
        holder.discountMed.setText("Discount: " + discount + "%");
        holder.packMed.setText("Pack: " + arrayList.get(position).getPack());
        holder.typeMed.setText("For: " + arrayList.get(position).getType());
        holder.rateMed.setText("Price: \u20B9" + rate + "");
        holder.prnumber.setText(quant + "");


        total = (int) ((rate - (rate * discount) / 100) * quant);

        holder.total.setText("MRP: \u20B9" + total);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate = arrayList.get(position).getRate();
                quant = arrayList.get(position).getQuant();
                quant=  Integer.parseInt(holder.prnumber.getText().toString());

                Log.d("cartAdapter--", holder.prnumber.getText().toString() + ",rate = " + rate + ", discount =" + discount);
                quant = quant + 1;

                Log.d("cartAdapter--", quant + "");
                holder.prnumber.setText(quant + "");
                total = (int) ((rate - (rate * discount) / 100) * quant);

                holder.total.setText("MRP: \u20B9" + total);
                arrayList.get(position).setTotal(total);
                arrayList.get(position).setQuant(quant);
                database.cartDao().update(arrayList.get(position));

                int grandTotal=0;
                for (int i=0;i<arrayList.size();i++){
                    grandTotal=grandTotal+arrayList.get(i).getTotal();
                }

//                MyCart myCart=new MyCart();

                MyCart.totalPrice(grandTotal);

            }
        });
        holder.subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quant=  Integer.parseInt(holder.prnumber.getText().toString());
                if (quant > 1) {
                    rate = arrayList.get(position).getRate();
                    quant = arrayList.get(position).getQuant();
                    Log.d("cartAdapter--", holder.prnumber.getText().toString() + ",rate = " + rate + ", discount =" + discount);
                    quant = quant - 1;

                    Log.d("cartAdapter--", quant + "");
                    holder.prnumber.setText(quant + "");


                    total = (int) ((rate - (rate * discount) / 100) * quant);

                    holder.total.setText("MRP: \u20B9" + total);

                    arrayList.get(position).setTotal(total);
                    arrayList.get(position).setQuant(quant);

                    database.cartDao().update(arrayList.get(position));
                    int grandTotal=0;
                    for (int i=0;i<arrayList.size();i++){
                        grandTotal=grandTotal+arrayList.get(i).getTotal();
                    }

                    MyCart myCart=new MyCart();

                    myCart.totalPrice(grandTotal);

                } else
                    Toast.makeText(context, "Quantity can't be 0 or less than 0", Toast.LENGTH_SHORT).show();            }
        });

holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        showAlertDialog(position);
    }
});
        // totalPrice(discount,rate);


    }
    private void showAlertDialog(int position) {
        Log.d("Medicine->", position + "");
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you want to add to cart?")
                .setIcon(R.drawable.add_to_cart)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show();
                       database.cartDao().delete(arrayList.get(position));

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "No", Toast.LENGTH_SHORT).show();

                    }
                });

        AlertDialog alertDialog = builder.create();
        builder.show();
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView rateMed, packMed, discountMed, typeMed, nameMed, total, prnumber;
        CardView subtract, add;
        ImageButton deleteBtn;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            nameMed = itemView.findViewById(R.id.nameMedtxt);
            rateMed = itemView.findViewById(R.id.rateMed);
            packMed = itemView.findViewById(R.id.PackMed);
            discountMed = itemView.findViewById(R.id.DiscountMed);
            typeMed = itemView.findViewById(R.id.typeMed);
            total = itemView.findViewById(R.id.total);
            subtract = itemView.findViewById(R.id.subtract);
            add = itemView.findViewById(R.id.add);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            prnumber = itemView.findViewById(R.id.prnumber);
        }
    }
}
