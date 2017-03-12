package com.codemagos.policeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codemagos.policeapp.SharedPreferences.SharedPreferencesStore;
import com.codemagos.policeapp.Utils.Settings;
import com.codemagos.policeapp.Webservice.WebService;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity {
    Button btn_save;
    EditText txt_name, txt_email, txt_mobile, txt_aadhar, txt_password, txt_re_password;
    String name, email, mobile, password, re_password, aadhar, device_id, fcm_id;
    SharedPreferencesStore spStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        spStore = new SharedPreferencesStore(getApplicationContext());
        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_mobile = (EditText) findViewById(R.id.txt_mobile);
        txt_aadhar = (EditText) findViewById(R.id.txt_aadhar);
        txt_password = (EditText) findViewById(R.id.txt_password);
        txt_re_password = (EditText) findViewById(R.id.txt_re_password);
        btn_save = (Button) findViewById(R.id.btn_save);
        // fetching device id and fcm id
        fcm_id = Settings.getFCMToken();
        device_id = Settings.getDeviceID(getApplicationContext());

        btn_save.setOnClickListener(userRegistrationAction);
    }

    View.OnClickListener userRegistrationAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // fething input data from input fields

            name = txt_name.getText().toString();
            email = txt_email.getText().toString();
            mobile = txt_mobile.getText().toString();
            aadhar = txt_aadhar.getText().toString();
            password = txt_password.getText().toString();
            re_password = txt_re_password.getText().toString();

            if (name.equals("")) {
                txt_name.setError("Enter Your Name");
            } else if (email.equals("")) {
                txt_email.setError("Enter your Email id");
            } else if (mobile.equals("")) {
                txt_mobile.setError("Enter your Mobile Number");
            } else if (aadhar.equals("")) {
                txt_aadhar.setError("Enter your Aadhar Card Number");
            } else if (password.equals("")) {
                txt_password.setError("Enter Password");
            } else if (re_password.equals("")) {
                txt_re_password.setError("Re Enter your password Again !");
            } else if (!password.equals(re_password)) {
                txt_password.setText("");
                txt_re_password.setText("");
                txt_password.setError("Passwords are not matching");
            } else {
                // form is  valid ready to submit
                BackTask backTask = new BackTask();
                backTask.execute();
            }

        }
    };


    protected class BackTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Creating Account...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {
            return WebService.userRegistration(name, email, mobile, password, aadhar, device_id, fcm_id);
        }


        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            Log.d("response from server", response);
            try {
                JSONObject responseObject = new JSONObject(response);
                if (responseObject.getString("status").equals("success")) {
                    Toast.makeText(getApplicationContext(), responseObject.getString("message"), Toast.LENGTH_SHORT).show();
                    JSONObject data = new JSONObject(responseObject.getString("data"));
                    spStore.setLogData(data.getString("id"), data.getString("name"), data.getString("email"));
                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                    intent.putExtra("meta","welcome");
                    startActivity(intent);
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
