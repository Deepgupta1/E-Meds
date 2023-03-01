package com.example.android.e_meds.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.e_meds.R;
import com.example.android.e_meds.fragment.readFragment;
import com.example.android.e_meds.models.MedicineModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForAdmin extends AppCompatActivity {
    String tag = "ForAdmin.java", selectedType;
    EditText edittextName, edittextType, edittextPack, edittextRate, edittextDiscount;
    Button add, read, delete, update;
    Spinner spinner;
    ArrayList<MedicineModel> arrayList = new ArrayList<>();


    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_admin);

        add = findViewById(R.id.addBtn);
        read = findViewById(R.id.readBtn);
        delete = findViewById(R.id.deleteBtn);
        update = findViewById(R.id.updateBtn);
        edittextName = findViewById(R.id.edittextName);
        edittextRate = findViewById(R.id.edittextRate);
        edittextPack = findViewById(R.id.edittextPack);
        edittextType = findViewById(R.id.edittextType);
        edittextDiscount = findViewById(R.id.edittextDiscount);

        spinner = findViewById(R.id.typeSpinner);
       ;


        db = FirebaseFirestore.getInstance();


        edittextType.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                openTypeList();

                return true;
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edittextDiscount.getText().toString().isEmpty())
                    Toast.makeText(ForAdmin.this, "please fill all Details", Toast.LENGTH_SHORT).show();
                else if (edittextName.getText().toString().isEmpty())
                    Toast.makeText(ForAdmin.this, "please fill all Details", Toast.LENGTH_SHORT).show();
                else if (edittextPack.getText().toString().isEmpty())
                    Toast.makeText(ForAdmin.this, "please fill all Details", Toast.LENGTH_SHORT).show();
                else if (edittextType.getText().toString().isEmpty())
                    Toast.makeText(ForAdmin.this, "please fill all Details", Toast.LENGTH_SHORT).show();
                else if (edittextRate.getText().toString().isEmpty())
                    Toast.makeText(ForAdmin.this, "please fill all Details", Toast.LENGTH_SHORT).show();
                else {
                    String name = edittextName.getText().toString();
                    String pack = edittextPack.getText().toString();
                    float rate = Float.parseFloat(edittextRate.getText().toString());
                    int discount = Integer.parseInt(edittextDiscount.getText().toString());
                    String type = edittextType.getText().toString();


                    addData(name, pack, rate, discount, type);
                }
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(ForAdmin.this,ReadActivity.class));
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edittextDiscount.getText().toString().isEmpty())
                    Toast.makeText(ForAdmin.this, "please fill all Details", Toast.LENGTH_SHORT).show();
                else if (edittextName.getText().toString().isEmpty())
                    Toast.makeText(ForAdmin.this, "please fill all Details", Toast.LENGTH_SHORT).show();
                else if (edittextPack.getText().toString().isEmpty())
                    Toast.makeText(ForAdmin.this, "please fill all Details", Toast.LENGTH_SHORT).show();
                else if (edittextType.getText().toString().isEmpty())
                    Toast.makeText(ForAdmin.this, "please fill all Details", Toast.LENGTH_SHORT).show();
                else if (edittextRate.getText().toString().isEmpty())
                    Toast.makeText(ForAdmin.this, "please fill all Details", Toast.LENGTH_SHORT).show();
                else {
                    String name = edittextName.getText().toString();
                    String pack = edittextPack.getText().toString();
                    float rate = Float.parseFloat(edittextRate.getText().toString());
                    int discount = Integer.parseInt(edittextDiscount.getText().toString());
                    String type = edittextType.getText().toString();


                    setUpdate(name, pack, rate, discount, type);
                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edittextName.getText().toString().isEmpty())
                    Toast.makeText(ForAdmin.this, "please fill all Details", Toast.LENGTH_SHORT).show();
                else {
                    String name = edittextName.getText().toString();

                    deleteData(name);
                }
            }
        });


//        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, typesArray));


    }

    private void openTypeList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("choose a type");
        ArrayList<String> typesArray = new ArrayList<>();
        typesArray.add("skin");
        typesArray.add("health");
        typesArray.add("injections");

        String[] typeData = typesArray.toArray(new String[0]);
        int checkedItem = 0;
        builder.setSingleChoiceItems(typeData, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selectedType = typeData[i];
            }
        });

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                edittextType.setText(selectedType);

            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    public void addData(String name, String pack, float rate, int discount, String type) {
        db.collection("ids").document("userId")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        String userId = task.getResult().getLong("currentUserId") + "";
                        createUserAccount(userId, name, pack, rate, discount, type);
                        HashMap<String, Object> hashMap = new HashMap<>();

                        hashMap.put("currentUserId", Long.parseLong(userId) + 1);
                        db.collection("ids").document("userId").update(hashMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        edittextName.setText("");
                                        edittextRate.setText("");
                                        edittextPack.setText("");
                                        edittextType.setText("");
                                        edittextDiscount.setText("");
                                        Toast.makeText(ForAdmin.this, "data added Successfully", Toast.LENGTH_SHORT).show();

                                        Log.d(tag, "added");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(tag, "fail");
                                    }
                                });
                    }
                });

    }

    private void createUserAccount(String userId, String name, String pack, float rate, int discount, String type) {
        Map<String, Object> user = new HashMap<>();
        user.put("ItemName", name);
        user.put("Pack", pack);
        user.put("Rate", rate);
        user.put("Discount", discount);
        user.put("type", type);

        db.collection("users")
                .document(userId)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(tag, "succes");
                    }


                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(tag, "Error adding document", e);
                    }
                });
    }




    public void setUpdate(String name, String pack, float rate, int discount, String type) {
        Map<String, Object> user = new HashMap<>();
        user.put("ItemName", name);
        user.put("Pack", pack);
        user.put("Rate", rate);
        user.put("Discount", discount);
        user.put("type", type);

        db.collection("users").whereEqualTo("ItemName", name)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String id = documentSnapshot.getId();
                            db.collection("users").document(id).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    edittextName.setText("");
                                    edittextRate.setText("");
                                    edittextPack.setText("");
                                    edittextType.setText("");
                                    edittextDiscount.setText("");
                                    Toast.makeText(ForAdmin.this, "data updated Successfully", Toast.LENGTH_SHORT).show();

                                    Log.d(tag, "updated");

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ForAdmin.this, "no item name found", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            Toast.makeText(ForAdmin.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void deleteData(String name) {

        db.collection("users").whereEqualTo("ItemName", name)
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
                                            edittextName.setText("");
                                            edittextRate.setText("");
                                            edittextPack.setText("");
                                            edittextType.setText("");
                                            edittextDiscount.setText("");
                                            Toast.makeText(ForAdmin.this, "data deleted Successfully", Toast.LENGTH_SHORT).show();

                                            Log.d(tag, "deleted");
                                        }
                                    }
                            ).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ForAdmin.this, "no item name found", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(ForAdmin.this, "errors", Toast.LENGTH_SHORT).show();

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ForAdmin.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });

    }


}