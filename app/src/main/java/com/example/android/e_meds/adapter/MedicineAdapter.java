package com.example.android.e_meds.adapter;

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
import com.example.android.e_meds.models.CartModel;
import com.example.android.e_meds.models.MedicineModel;
import com.example.android.e_meds.models.MyOrderModel;
import com.example.android.e_meds.roomdb.MedicineDatabase;
import com.example.android.e_meds.roomdb.OrdersDatabase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {
    Context context;
    ArrayList<MedicineModel> arrayList = new ArrayList<>();
    View view;
    FirebaseAuth mAuth;
    MedicineDatabase database;
    OrdersDatabase database2;
    int total,quant=1;
    float rate;
    int discount;

    public MedicineAdapter(Context context, ArrayList<MedicineModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(context).inflate(R.layout.cart_adapter, parent, false);


        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        holder.addToCart.setVisibility(View.VISIBLE);
        discount = arrayList.get(position).getDiscount();
        rate = arrayList.get(position).getRate();
        holder.nameMed.setText(arrayList.get(position).getItemName());
        holder.discountMed.setText("Discount: " + discount + "%");
        holder.packMed.setText("Pack: " + arrayList.get(position).getPack());

        holder.typeMed.setText("For: " + arrayList.get(position).getType());
        holder.rateMed.setText("Price: \u20B9" + rate + "");

        total = (int) ((rate - (rate * discount) / 100) * quant);

        holder.total.setText("MRP: \u20B9" + total);

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(position);
            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discount = arrayList.get(position).getDiscount();
                rate = arrayList.get(position).getRate();
                addQuantity(discount,rate,holder);
            }
        });
        holder.subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discount = arrayList.get(position).getDiscount();
                rate = arrayList.get(position).getRate();
                subtractQuantity(discount,rate, holder);
            }
        });


    }

    private void subtractQuantity(int discount,float rate,MedicineViewHolder holder) {

       quant=  Integer.parseInt(holder.prnumber.getText().toString());
        if (quant > 1) {
            Log.d("cartAdapter--", holder.prnumber.getText().toString() + ",rate = " + rate + ", discount =" + discount);
            quant = quant - 1;

            Log.d("cartAdapter--", quant + "");
            holder.prnumber.setText(quant + "");


            total = (int) ((rate - (rate * discount) / 100) * quant);

            holder.total.setText("MRP: \u20B9" + total);
        } else
            Toast.makeText(context, "Quantity can't be 0 or less than 0", Toast.LENGTH_SHORT).show();
    }

    private void addQuantity(int discount,float rate,MedicineViewHolder holder) {
        quant=  Integer.parseInt(holder.prnumber.getText().toString());

        Log.d("cartAdapter--", holder.prnumber.getText().toString() + ",rate = " + rate + ", discount =" + discount);
        quant = quant + 1;

        Log.d("cartAdapter--", quant + "");
        holder.prnumber.setText(quant + "");
        total = (int) ((rate - (rate * discount) / 100) * quant);

        holder.total.setText("MRP: \u20B9" + total);

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
                        String name = arrayList.get(position).getItemName();
                        int discount = arrayList.get(position).getDiscount();
                        String pack = arrayList.get(position).getPack();
                        float rate = arrayList.get(position).getRate();
                        String type = arrayList.get(position).getType();
                        clickYes(name, discount, rate, pack, type);
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

    private void clickYes(String name, int discount, float rate, String pack, String type) {
        database = MedicineDatabase.getDB(context);
        database2 = OrdersDatabase.getDB(context);
        database2.orderDao().insert(new MyOrderModel(name, pack, rate, discount, type, total, quant));

        database.cartDao().insert(new CartModel(name, pack, rate, discount, type, total, quant));
        Toast.makeText(context, "added Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MedicineViewHolder extends RecyclerView.ViewHolder {

        TextView rateMed, packMed, discountMed, typeMed, nameMed, total, prnumber;
        CardView subtract, add;
        ImageButton deleteBtn, addToCart;
        // CardView medicineCard;

        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            nameMed = itemView.findViewById(R.id.nameMedtxt);
            rateMed = itemView.findViewById(R.id.rateMed);
            packMed = itemView.findViewById(R.id.PackMed);
            discountMed = itemView.findViewById(R.id.DiscountMed);
            typeMed = itemView.findViewById(R.id.typeMed);
            // medicineCard = itemView.findViewById(R.id.medicineCard);

            total = itemView.findViewById(R.id.total);
            subtract = itemView.findViewById(R.id.subtract);
            add = itemView.findViewById(R.id.add);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            prnumber = itemView.findViewById(R.id.prnumber);
            addToCart = itemView.findViewById(R.id.addToCart);


        }
    }

    public void filterList(List<MedicineModel> filteredList) {
        arrayList = (ArrayList<MedicineModel>) filteredList;
        notifyDataSetChanged();
    }
}
