package com.codemagos.policeapp;

import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    SharedPreferencesStore spStore;
Button btn_login,btn_register;
    EditText txt_password;
    String password,device_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spStore = new SharedPreferencesStore(getApplicationContext());
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        txt_password = (EditText) findViewById(R.id.txt_password);
        // finding device id from settings class
        device_id = Settings.getDeviceID(getApplicationContext());
        Toast.makeText(getApplicationContext(),
                Settings.getFCMToken(),Toast.LENGTH_LONG).show();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // login action
                password = txt_password.getText().toString();
                BackTask backTask = new BackTask();
                backTask.execute();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // register action
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });
    }


    protected class BackTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            return WebService.userLogin(device_id,password);

        }

        @Override
        protected void onPostExecute(String response) {
            Log.e("login",response);
            try {
                JSONObject responseObject = new JSONObject(response);
                if (responseObject.getString("status").equals("success")) {
                    Toast.makeText(getApplicationContext(), responseObject.getString("message"), Toast.LENGTH_SHORT).show();
                    JSONObject data = new JSONObject(responseObject.getString("data"));
                    //spStore.setLogData(data.getString("id"), data.getString("name"), data.getString("email"));
                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
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
