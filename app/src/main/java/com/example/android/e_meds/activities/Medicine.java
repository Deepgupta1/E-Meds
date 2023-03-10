package com.example.android.e_meds.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import org.checkerframework.common.returnsreceiver.qual.This;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Medicine extends AppCompatActivity {
    String click, search;
    RecyclerView medicineRecycler;
    ArrayList<MedicineModel> arrayList = new ArrayList<>();
    FirebaseFirestore db;
    SearchView searchView;
    MedicineAdapter adapter;
    String tag = "Medicine.java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        medicineRecycler = findViewById(R.id.medicineRecycler);
        searchView = findViewById(R.id.searchView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        medicineRecycler.setLayoutManager(layoutManager);

        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        click = intent.getStringExtra("click");
search=intent.getStringExtra("name");
        read(click);

/*
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<MedicineModel> filteredList = filter(arrayList, newText);
                adapter.filterList(filteredList);
                return true;
            }
        });*/


    }

    private List<MedicineModel> filter(List<MedicineModel> myObjects, String query) {
        query = query.toLowerCase();
        final List<MedicineModel> filteredList = new ArrayList<>();
        for (MedicineModel myObject : myObjects) {
            final String text = myObject.getItemName().toLowerCase();
            if (text.contains(query)) {
                filteredList.add(myObject);
            }
        }
        return filteredList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search, menu);

        MenuItem searchViewItem = menu.findItem(R.id.search_bar);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<MedicineModel> filteredList = filter(arrayList, newText);
                adapter.filterList(filteredList);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void read(String click) {
        if (click != null && click.equals("all")) {

            db.collection("users").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                Log.d(tag, "innullSuccess");
                                for (QueryDocumentSnapshot documents : task.getResult()) {
                                    String id = documents.getId();
                                    String name = documents.get("Item Name").toString();
                                    String type = documents.get("Type").toString();
                                    String pack = documents.get("Pack").toString();
                                    Float rate = Float.parseFloat(documents.get("Rate").toString());
                                    int discount = Integer.parseInt(documents.get("Discount").toString());

                                    arrayList.add(new MedicineModel(id, name, pack, rate, discount, type));

                                }
                                adapter = new MedicineAdapter(Medicine.this, arrayList);
                                medicineRecycler.setAdapter(adapter);
                            } else {
                                Log.d(tag, "in null else");
                                Toast.makeText(Medicine.this, "error", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(tag, "in null error");
                            Toast.makeText(Medicine.this, "error", Toast.LENGTH_SHORT).show();

                        }
                    });

        } else if (search != null&& search.equals("search")) {
            db.collection("users").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                Log.d(tag, "in search success");
                                for (QueryDocumentSnapshot documents : task.getResult()) {
                                    String id = documents.getId();
                                    String name = documents.get("Item Name").toString();
                                    String type = documents.get("Type").toString();
                                    String pack = documents.get("Pack").toString();
                                    Float rate = Float.parseFloat(documents.get("Rate").toString());
                                    int discount = Integer.parseInt(documents.get("Discount").toString());

                                    arrayList.add(new MedicineModel(id, name, pack, rate, discount, type));

                                }
                                medicineRecycler.setAdapter(new MedicineAdapter(Medicine.this, arrayList));
                            } else {
                                Log.d(tag, "in search else");
                                Toast.makeText(Medicine.this, "No Medicine Found", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(tag, "in search failure");
                            Toast.makeText(Medicine.this, "error", Toast.LENGTH_SHORT).show();

                        }
                    });
        } else {
            db.collection("users").whereEqualTo("Type", click).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                Log.d(tag, "in default success");
                                for (QueryDocumentSnapshot documents : task.getResult()) {
                                    String id = documents.getId();
                                    String name = documents.get("Item Name").toString();
                                    String type = documents.get("Type").toString();
                                    String pack = documents.get("Pack").toString();
                                    Float rate = Float.parseFloat(documents.get("Rate").toString());
                                    int discount = Integer.parseInt(documents.get("Discount").toString());

                                    arrayList.add(new MedicineModel(id, name, pack, rate, discount, type));

                                }
                                medicineRecycler.setAdapter(new MedicineAdapter(Medicine.this, arrayList));
                            } else {
                                Log.d(tag, "in default else");
                                Toast.makeText(Medicine.this, "No Medicine Found", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(tag, "in default failure");
                            Toast.makeText(Medicine.this, "error", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }
}