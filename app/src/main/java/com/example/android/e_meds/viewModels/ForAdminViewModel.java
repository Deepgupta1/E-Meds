package com.example.android.e_meds.viewModels;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ForAdminViewModel  {
    Context context;
    FirebaseFirestore db;
    String tag="ForAdminViewModel.java";
    MutableLiveData<String> name=new MutableLiveData<>();
    public ForAdminViewModel(Context context) {
        this.context = context;
        db = FirebaseFirestore.getInstance();

    }



    public void delete(){


    }

    public void update(){

    }

    public void read(){

    }
    public void addData(String name, String pack, float rate, int discount, String type) {
        db.collection("ids").document("userId")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        String userId = task.getResult().getLong("currentUserId") + "";
                        createUserAccount(userId,name,pack,rate,discount,type);
                        HashMap<String, Object> hashMap = new HashMap<>();

                        hashMap.put("currentUserId", Long.parseLong(userId) + 1);
                        db.collection("ids").document("userId").update(hashMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(tag, "updated");
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



    private void createUserAccount(String userId,String name,String pack,float rate,int discount,String type) {
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
}
