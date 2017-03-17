package com.codemagos.policeapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codemagos.policeapp.SharedPreferences.SharedPreferencesStore;
import com.codemagos.policeapp.Webservice.WebService;

import org.json.JSONException;
import org.json.JSONObject;

public class ComplaintRegisterActivity extends AppCompatActivity {
    SharedPreferencesStore spStore;
Button btn_submit;
    TextView txt_location;
    EditText txt_title,txt_content;
    String title,content,location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_register);
        spStore = new SharedPreferencesStore(getApplicationContext());
        txt_title = (EditText) findViewById(R.id.txt_title);
        txt_content = (EditText) findViewById(R.id.txt_content);
        txt_location = (TextView) findViewById(R.id.txt_location);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        location = "";
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = txt_title.getText().toString();
                content = txt_content.getText().toString();
                BackTask backTask = new BackTask();
                backTask.execute();
            }
        });
    }

    protected class BackTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Creating Account...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {
            return WebService.complaintRegistration(spStore.getID(),title,content,location);
        }


        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            Log.d("response from server", response);
            try {
                JSONObject responseObject = new JSONObject(response);
                if (responseObject.getString("status").equals("success")) {
                    Toast.makeText(getApplicationContext(),responseObject.getString("message"),Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),ComplaintsActivity.class));
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
