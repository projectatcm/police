package com.codemagos.policeapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codemagos.policeapp.Adapters.NumbersListAdapter;
import com.codemagos.policeapp.Webservice.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    String key = "";
    ListView list_numbers;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        list_numbers = (ListView) findViewById(R.id.list_numbers);


        BackTask backTask = new BackTask();
        backTask.execute();


    }

    protected class BackTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(NumbersActivity.this, "",
                    "Loading. Please wait...", true);
        }

        @Override
        protected String doInBackground(String... params) {
            return WebService.getContacts("");

        }

        @Override
        protected void onPostExecute(String response) {
            Log.e("Contacts", response);
            progressDialog.hide();
            ArrayList names = new ArrayList();
            final ArrayList numbers = new ArrayList();
            try {
                JSONObject responseObject = new JSONObject(response);
                if (responseObject.getString("status").equals("success")) {
                    JSONArray data = new JSONArray(responseObject.getString("data"));
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject contact = data.getJSONObject(i);
                        names.add(contact.getString("name"));
                        numbers.add(contact.getString("phone"));
                    }
                    NumbersListAdapter numbersListAdapter = new NumbersListAdapter(NumbersActivity.this, names, numbers);
                    list_numbers.setAdapter(numbersListAdapter);
                }else{
                    // if the response if error
                    Toast.makeText(getApplicationContext(), responseObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("ERROR : ", response);
            }
        }
    }
}
