package com.example.android.e_meds.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.android.e_meds.R;
import com.example.android.e_meds.adapter.ReadAdapter;
import com.example.android.e_meds.models.MedicineModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ReadActivity extends AppCompatActivity {
    RecyclerView readRecycler;
    String tag = "readFragment.java";
    FirebaseFirestore db;
    ArrayList<MedicineModel> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        db = FirebaseFirestore.getInstance();

        readRecycler=findViewById(R.id.readRecycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ReadActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        readRecycler.setLayoutManager(layoutManager);
        readData();
    }
    public void readData() {

        db.collection("users")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documents : task.getResult()) {

                                String id = documents.getId();
                                String name = documents.get("Item Name").toString();
                                float rate = Float.parseFloat(documents.get("Rate").toString());
                                int discount = Integer.parseInt(documents.get("Discount").toString());
                                String pack = documents.get("Pack").toString();
                                String type = documents.get("Type").toString();
                                arrayList.add(new MedicineModel(id, name, pack, rate, discount, type));
                                Log.d(tag, " ---> in if" +arrayList.size());
                                Log.d(tag, documents.getId() + " ---> " + documents.getData());
                            }

                            Log.d(tag, " ---> " +arrayList.get(0).getItemName());
                            readRecycler.setAdapter(new ReadAdapter(ReadActivity.this, arrayList));
                        } else {
                            Toast.makeText(ReadActivity.this, "No Medicine Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ReadActivity.this, "error", Toast.LENGTH_SHORT).show();

                    }
                });

    }
}