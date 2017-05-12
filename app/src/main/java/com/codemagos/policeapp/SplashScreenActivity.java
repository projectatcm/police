package com.codemagos.policeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.codemagos.policeapp.SharedPreferences.SharedPreferencesStore;

public class SplashScreenActivity extends AppCompatActivity {
SharedPreferencesStore spStore;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        spStore = new SharedPreferencesStore(getApplicationContext());

        if(spStore.getID().equals("")){
            intent = new Intent(getApplicationContext(),LoginActivity.class);
        }else{
            intent = new Intent(getApplicationContext(),HomeActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
