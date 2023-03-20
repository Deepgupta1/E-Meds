package com.example.android.e_meds.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.e_meds.R;
import com.example.android.e_meds.activities.ForAdmin;
import com.example.android.e_meds.models.MedicineModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReadAdapter extends RecyclerView.Adapter<ReadAdapter.ReadViewHolder> {
    Context context;
    ArrayList<MedicineModel> arrayList = new ArrayList<>();
    View view;
    FirebaseFirestore db;
    Dialog dialog;
    EditText edittextName, edittextType, edittextPack, edittextRate, edittextDiscount;
    Button update;

    public ReadAdapter(Context context, ArrayList<MedicineModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ReadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.read_adapter, parent, false);

        return new ReadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReadViewHolder holder, int position) {
        db = FirebaseFirestore.getInstance();

        holder.txtId.setText("Id: " + arrayList.get(position).getId());
        holder.txtPack.setText("Pack: " + arrayList.get(position).getPack());
        holder.txtDiscount.setText("Discount: " + arrayList.get(position).getDiscount());
        holder.txtName.setText("Name: " + arrayList.get(position).getItemName());
        holder.txtType.setText("Type: " + arrayList.get(position).getType());
        holder.txtRate.setText("Rate: " + arrayList.get(position).getRate());

        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog(position);
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
deleteDialog(position);
            }
        });


    }

    private void deleteDialog(int position) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);

        builder.setCancelable(true);
        builder.setMessage("Are you sure you want to delete?");
        builder.setIcon(R.drawable.ic_baseline_delete_24);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteData(arrayList.get(position).getItemName());
                Toast.makeText(context,"deleted",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = builder.create();
        builder.show();

    }

    private void updateDialog(int position) {
        dialog = new Dialog(context);
        dialog.setCancelable(true);

        dialog.setContentView(R.layout.update_layout_dialog);
        update = dialog.findViewById(R.id.updateBtn);
        edittextName = dialog.findViewById(R.id.edittextName);
        edittextRate = dialog.findViewById(R.id.edittextRate);
        edittextPack = dialog.findViewById(R.id.edittextPack);
        edittextType = dialog.findViewById(R.id.edittextType);
        edittextDiscount = dialog.findViewById(R.id.edittextDiscount);


        edittextDiscount.setText(arrayList.get(position).getDiscount()+"");
        edittextName.setText(arrayList.get(position).getItemName());
        edittextRate.setText(arrayList.get(position).getRate()+"");
        edittextType.setText(arrayList.get(position).getType());
        edittextPack.setText(arrayList.get(position).getPack());




dialog.show();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edittextName.getText().toString();
                String pack = edittextPack.getText().toString();
                String type = edittextType.getText().toString();
                float rate = Float.parseFloat(edittextRate.getText().toString());
                int discount = Integer.parseInt(edittextDiscount.getText().toString());
                setUpdate(name, pack, rate, discount, type);
            }
        });


    }

    public void setUpdate(String name, String pack, float rate, int discount, String type) {
        Map<String, Object> user = new HashMap<>();
        user.put("Item Name", name);
        user.put("Pack", pack);
        user.put("Rate", rate);
        user.put("Discount", discount);
        user.put("Type", type);

        db.collection("users").whereEqualTo("Item Name", name)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String id = documentSnapshot.getId();
                            db.collection("users").document(id).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    Toast.makeText(context, "data updated Successfully", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    //   Log.d(tag, "updated");

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "no item name found", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void deleteData(String name) {

        db.collection("users").whereEqualTo("Item Name", name)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String id = documentSnapshot.getId();

                            db.collection("users").document(id).delete().addOnSuccessListener(
                                    new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                            Toast.makeText(context, "data deleted Successfully", Toast.LENGTH_SHORT).show();

                                            // Log.d(tag, "deleted");
                                        }
                                    }
                            ).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "no item name found", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(context, "errors", Toast.LENGTH_SHORT).show();

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ReadViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtRate, txtPack, txtType, txtId, txtDiscount;
        Button updateBtn, deleteBtn;

        public ReadViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDiscount = itemView.findViewById(R.id.txtDiscount);
            txtName = itemView.findViewById(R.id.txtName);
            txtRate = itemView.findViewById(R.id.txtRate);
            txtType = itemView.findViewById(R.id.txtType);
            txtId = itemView.findViewById(R.id.txtId);
            txtPack = itemView.findViewById(R.id.txtPack);
            updateBtn = itemView.findViewById(R.id.updateBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);

        }
    }
}
