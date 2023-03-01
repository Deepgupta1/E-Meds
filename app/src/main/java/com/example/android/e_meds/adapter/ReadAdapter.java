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
import com.example.android.e_meds.models.MedicineModel;

import java.util.ArrayList;

public class ReadAdapter extends RecyclerView.Adapter<ReadAdapter.ReadViewHolder> {
    Context context;
    ArrayList<MedicineModel> arrayList=new ArrayList<>();
    View view;

    public ReadAdapter(Context context, ArrayList<MedicineModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ReadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.read_adapter,parent,false);

        return new ReadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReadViewHolder holder, int position) {
        holder.txtId.setText("Id: "+arrayList.get(position).getId());
        holder.txtPack.setText("Pack: "+arrayList.get(position).getPack());
        holder.txtDiscount.setText("Discount: "+arrayList.get(position).getDiscount());
        holder.txtName.setText("Name: "+arrayList.get(position).getItemName());
        holder.txtType.setText("Type: "+arrayList.get(position).getType());
        holder.txtRate.setText("Rate: "+arrayList.get(position).getRate());



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ReadViewHolder extends RecyclerView.ViewHolder {

        TextView txtName,txtRate,txtPack,txtType,txtId,txtDiscount;
        public ReadViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDiscount=itemView.findViewById(R.id.txtDiscount);
            txtName=itemView.findViewById(R.id.txtName);
            txtRate=itemView.findViewById(R.id.txtRate);
            txtType=itemView.findViewById(R.id.txtType);
            txtId=itemView.findViewById(R.id.txtId);
            txtPack=itemView.findViewById(R.id.txtPack);

        }
    }
}
