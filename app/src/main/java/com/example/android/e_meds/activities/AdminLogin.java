package com.example.android.e_meds.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.e_meds.R;

public class AdminLogin extends AppCompatActivity {
   EditText etEmailAddress,etPassword;
   Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        
        etEmailAddress=findViewById(R.id.etEmailAddress);
        btnLogin=findViewById(R.id.btnLogin);
        etPassword=findViewById(R.id.etPassword);
        
        
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etEmailAddress.getText().toString().isEmpty()&&etPassword.getText().toString().isEmpty()){
                    Toast.makeText(AdminLogin.this, "enter all fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(etEmailAddress.getText().toString().equals("satishpals621@gmail.com")&&etPassword.getText().toString().equals("S621@pal")){
                        startActivity(new Intent(AdminLogin.this,ForAdmin.class));
                    }
                    else{
                        Toast.makeText(AdminLogin.this, "Details are incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
                
            }
        });
    }
}