package com.example.android.e_meds.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.e_meds.R;
import com.example.android.e_meds.databinding.ActivityMainBinding;
import com.example.android.e_meds.viewModels.MainViewMmodel;
import com.example.android.e_meds.viewModels.MainViewModelFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MainViewMmodel mainViewMmodel;
    FirebaseFirestore db;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mainViewMmodel = new ViewModelProvider(this, new MainViewModelFactory(MainActivity.this)).get(MainViewMmodel.class);
        binding.setMainViewModel(mainViewMmodel);


        search();
    }

    public void search() {
        db = FirebaseFirestore.getInstance();

        db = FirebaseFirestore.getInstance();
        db.collection("users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            for (QueryDocumentSnapshot documents : task.getResult()) {

                                String name = documents.get("ItemName").toString();


                                arrayList.add(name);

                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
                            binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    if(!query.isEmpty()) {
                                        Intent intent = new Intent(MainActivity.this, Medicine.class);
                                        intent.putExtra("name", query);
                                        Log.d("Query--",query);
                                        startActivity(intent);
                                    }

                                    return true;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                  //  Toast.makeText(MainActivity.this, "seach change", Toast.LENGTH_SHORT).show();
                                    return false;
                                }
                            });

                            //binding.searchBar

                        } else {
                            Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();

                    }
                });

    }
}