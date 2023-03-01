package com.example.android.e_meds.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.e_meds.R;
import com.example.android.e_meds.adapter.MedicineAdapter;
import com.example.android.e_meds.models.MedicineModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Medicine extends AppCompatActivity {
    String click,search;
    RecyclerView medicineRecycler;
    ArrayList<MedicineModel> arrayList = new ArrayList<>();
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        medicineRecycler = findViewById(R.id.medicineRecycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        medicineRecycler.setLayoutManager(layoutManager);

        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        click = intent.getStringExtra("click");
search=intent.getStringExtra("name");
        read(click);



    }

    private void read(String click) {
        if (click!=null&&click.equals("all")){

            db.collection("users").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot documents : task.getResult()) {
                                    String id = documents.getId();
                                    String name = documents.get("ItemName").toString();
                                    String type = documents.get("type").toString();
                                    String pack = documents.get("Pack").toString();
                                    Float rate = Float.parseFloat(documents.get("Rate").toString());
                                    int discount = Integer.parseInt(documents.get("Discount").toString());

                                    arrayList.add(new MedicineModel(id, name, pack, rate, discount, type));

                                }
                                medicineRecycler.setAdapter(new MedicineAdapter(Medicine.this, arrayList));
                            } else {
                                Toast.makeText(Medicine.this, "error", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Medicine.this, "error", Toast.LENGTH_SHORT).show();

                        }
                    });

    }
        else if(search!=null){
            db.collection("users").whereEqualTo("ItemName", search).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot documents : task.getResult()) {
                                    String id = documents.getId();
                                    String name = documents.get("ItemName").toString();
                                    String type = documents.get("type").toString();
                                    String pack = documents.get("Pack").toString();
                                    Float rate = Float.parseFloat(documents.get("Rate").toString());
                                    int discount = Integer.parseInt(documents.get("Discount").toString());

                                    arrayList.add(new MedicineModel(id, name, pack, rate, discount, type));

                                }
                                medicineRecycler.setAdapter(new MedicineAdapter(Medicine.this, arrayList));
                            } else {
                                Toast.makeText(Medicine.this, "No Medicine Found", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Medicine.this, "error", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
        else{
               db.collection("users").whereEqualTo("type", click).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot documents : task.getResult()) {
                                    String id = documents.getId();
                                    String name = documents.get("ItemName").toString();
                                    String type = documents.get("type").toString();
                                    String pack = documents.get("Pack").toString();
                                    Float rate = Float.parseFloat(documents.get("Rate").toString());
                                    int discount = Integer.parseInt(documents.get("Discount").toString());

                                    arrayList.add(new MedicineModel(id, name, pack, rate, discount, type));

                                }
                                medicineRecycler.setAdapter(new MedicineAdapter(Medicine.this, arrayList));
                            } else {
                                Toast.makeText(Medicine.this, "No Medicine Found", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Medicine.this, "error", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }
}