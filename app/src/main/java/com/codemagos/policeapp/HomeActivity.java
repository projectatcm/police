package com.codemagos.policeapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codemagos.policeapp.SharedPreferences.SharedPreferencesStore;
import com.codemagos.policeapp.Utils.Settings;
import com.codemagos.policeapp.Webservice.WebService;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {
SharedPreferencesStore spStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        spStore = new SharedPreferencesStore(getApplicationContext());
    }


    public void navigation(View v){
        switch (v.getId()){
            case R.id.btn_news:
                startActivity(new Intent(getApplicationContext(),NewsActivity.class));
                break;
            case R.id.btn_complaints:
                startActivity(new Intent(getApplicationContext(),ComplaintsActivity.class));
                break;
            case R.id.btn_law:
                startActivity(new Intent(getApplicationContext(),LawActivity.class));
                break;
            case R.id.btn_numbers:

                startActivity(new Intent(getApplicationContext(),NumbersActivity.class));
                break;

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                spStore.clearLogData();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
