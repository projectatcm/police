package com.codemagos.policeapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
        BackTask bb = new BackTask();
        bb.execute();
    }



    protected class BackTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            return WebService.updateFCMToken(spStore.getID(), Settings.getFCMToken());

        }

        @Override
        protected void onPostExecute(String response) {
            Log.e("login",response);
        }
    }
}
