package com.example.android.e_meds.viewModels;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.e_meds.activities.AdminLogin;
import com.example.android.e_meds.activities.ForAdmin;
import com.example.android.e_meds.activities.Medicine;
import com.example.android.e_meds.activities.MyCart;
import com.example.android.e_meds.activities.MyOrders;
import com.example.android.e_meds.adapter.MedicineAdapter;
import com.example.android.e_meds.models.MedicineModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainViewMmodel extends ViewModel {

    MedicineModel medicineModel = new MedicineModel();

    ArrayList<String> arrayList = new ArrayList<>();
    MutableLiveData<String> search = new MutableLiveData<>();

    AutoCompleteTextView autoCompleteTextView;
    Context context;

    public MainViewMmodel(Context context) {
        this.context = context;


    }

    public void onClickSkin() {
        Intent intent = new Intent(context, Medicine.class);
        intent.putExtra("click", "skin");
        context.startActivity(intent);
    }



    public void onClickAll() {
        Intent intent = new Intent(context, Medicine.class);
        intent.putExtra("click", "all");
        context.startActivity(intent);

    }

    public void onClickHealth() {

        Intent intent = new Intent(context, Medicine.class);
        intent.putExtra("click", "health");
        context.startActivity(intent);
    }

    public void onClickInjections() {
        Intent intent = new Intent(context, Medicine.class);
        intent.putExtra("click", "injections");
        context.startActivity(intent);
    }

    public void onClickMyCart() {
        // Toast.makeText(context, "Working on it", Toast.LENGTH_SHORT).show();
        context.startActivity(new Intent(context, MyCart.class));
    }

    public void onClickContactUs() {
        context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "8368175729")));
    }

    public void onClickForAdmin() {
        context.startActivity(new Intent(context, AdminLogin.class));

    }

    public void onClickMyOrder() {
        // Toast.makeText(context, "Working on it", Toast.LENGTH_SHORT).show();

        context.startActivity(new Intent(context, MyOrders.class));
    }
}
