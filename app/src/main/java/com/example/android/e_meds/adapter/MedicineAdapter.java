package com.example.android.e_meds.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {
    Context context;
    ArrayList<MedicineModel> arrayList = new ArrayList<>();
    View view;
    FirebaseAuth mAuth;
    MedicineDatabase database;
    OrdersDatabase database2;

    public MedicineAdapter(Context context, ArrayList<MedicineModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(context).inflate(R.layout.medicine_adapter, parent, false);


        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {

        holder.nameMed.setText(arrayList.get(position).getItemName());
        holder.discountMed.setText("Discount: "+arrayList.get(position).getDiscount()+"%");
        holder.packMed.setText("Pack: "+arrayList.get(position).getPack());
        holder.typeMed.setText("For: "+arrayList.get(position).getType());
        holder.rateMed.setText("Price: \u20B9"+arrayList.get(position).getRate()+"");

        holder.medicineCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(position);
            }
        });


    }

    private void showAlertDialog(int position) {
   AlertDialog.Builder builder=new AlertDialog.Builder(context);
   builder.setMessage("Do you want to add to cart?")
           .setIcon(R.drawable.add_to_cart)
           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show();
                   String name=arrayList.get(position).getItemName();
                   int discount=arrayList.get(position).getDiscount();
                   String pack=arrayList.get(position).getPack();
                   float rate=arrayList.get(position).getRate();
                   String type=arrayList.get(position).getType();
                   clickYes(name,discount,rate,pack,type);
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

    private void clickYes(String name,int discount,float rate,String pack,String type) {
        database=MedicineDatabase.getDB(context);
        database2=OrdersDatabase.getDB(context);
        database2.orderDao().insert(new MyOrderModel(name,pack,rate,discount,type));

        database.cartDao().insert(new CartModel(name,pack,rate,discount,type));
        Toast.makeText(context, "added Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MedicineViewHolder extends RecyclerView.ViewHolder {

        TextView rateMed, packMed, discountMed, typeMed, nameMed;
        CardView medicineCard;

        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            nameMed = itemView.findViewById(R.id.nameMedtxt);
            rateMed = itemView.findViewById(R.id.rateMed);
            packMed = itemView.findViewById(R.id.PackMed);
            discountMed = itemView.findViewById(R.id.DiscountMed);
            typeMed = itemView.findViewById(R.id.typeMed);
            medicineCard=itemView.findViewById(R.id.medicineCard);


        }
    }
}
