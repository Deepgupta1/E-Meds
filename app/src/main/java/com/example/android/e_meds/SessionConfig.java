package com.example.android.e_meds;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionConfig {

private Context context;
private SharedPreferences sharedPreferences;

public SessionConfig(Context context){
    this.context=context;
    sharedPreferences= context.getSharedPreferences("Context",Context.MODE_PRIVATE);

}

public void setLogin(Boolean login){
SharedPreferences.Editor editor=sharedPreferences.edit();
editor.putBoolean("login",login);
editor.apply();
}

public  Boolean getLogin(){
    return sharedPreferences.getBoolean("login", false);
}
}
