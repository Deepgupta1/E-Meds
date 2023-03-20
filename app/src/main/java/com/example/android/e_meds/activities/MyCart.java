package com.example.android.e_meds.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.e_meds.R;
import com.example.android.e_meds.adapter.CartAdapter;
import com.example.android.e_meds.models.CartModel;
import com.example.android.e_meds.roomdb.MedicineDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MyCart extends AppCompatActivity {
    RecyclerView addToCartRecycler;
    ArrayList<CartModel> arrayList = new ArrayList<>();
    MedicineDatabase database;
    String tag = "MyCart.java";
    FloatingActionButton messageBtn;
    Boolean flag = false;
    public static TextView txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        addToCartRecycler = findViewById(R.id.addToCartRecycler);
        messageBtn = findViewById(R.id.messageBtn);
        txtTotal=findViewById(R.id.txtTotal);
        database = MedicineDatabase.getDB(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        addToCartRecycler.setLayoutManager(layoutManager);
        arrayList = (ArrayList<CartModel>) database.cartDao().getAllCourses();

        //Log.d(tag, arrayList.get(0).getItemName() + "  size:" + arrayList.size());


        addToCartRecycler.setAdapter(new CartAdapter(this, arrayList));
        int grandTotal=0;
        for (int i=0;i<arrayList.size();i++){
            grandTotal=grandTotal+arrayList.get(i).getTotal();
        }

        txtTotal.setText("Total price: \u20B9"+grandTotal);

        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String smsNumber = "918368175729";

              /*  String body = "Hi,\nI want to order:\n";
                for (int i = 0; i < arrayList.size(); i++) {
                    body += i + 1 + ". " + arrayList.get(i).getItemName() + "\n";
                }

                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setPackage("com.whatsapp");
                sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber) + "@s.whatsapp.net");//phone number without "+" prefix
                sendIntent.putExtra(Intent.EXTRA_TEXT, body);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);*/
                Uri uri = Uri.parse("smsto:8368175729");
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                String  body="Hi,\nI want to order:\n";
                for(int i=0;i<arrayList.size();i++){
                    body+=i+1+". "+arrayList.get(i).getItemName()+" quantity: "+arrayList.get(i).getQuant()+" price:"+arrayList.get(i).getTotal()+"\n";
                }
                int grandTotal=0;
                for (int i=0;i<arrayList.size();i++){
                    grandTotal=grandTotal+arrayList.get(i).getTotal();
                }
                body+="total:"+grandTotal;

                it.putExtra("sms_body", body);
                startActivity(it);
            }
        });


       //

    }
    public static void totalPrice(int total){
        Log.d("abc---","as=="+total);
        txtTotal.setText("Total price: \u20B9"+total);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (flag == true) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Have you sent Message")
                    .setIcon(R.drawable.add_to_cart)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MyCart.this, "Yes", Toast.LENGTH_SHORT).show();
                            database = MedicineDatabase.getDB(MyCart.this);

                            database.cartDao().deleteAllCourses();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MyCart.this, "No", Toast.LENGTH_SHORT).show();

                        }
                    });

            AlertDialog alertDialog = builder.create();
            builder.show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        flag = true;
    }


    private void openWhatsApp() {
        String smsNumber = "919540388951";
        boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
        if (isWhatsappInstalled) {

            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber) + "@s.whatsapp.net");//phone number without "+" prefix

            startActivity(sendIntent);
        } else {
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            Toast.makeText(this, "WhatsApp not Installed",
                    Toast.LENGTH_SHORT).show();
            startActivity(goToMarket);
        }
    }

    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}