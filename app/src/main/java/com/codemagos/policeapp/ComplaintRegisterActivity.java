package com.codemagos.policeapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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
    EditText txt_title, txt_content;
    LocationManager locationManager;
    String title, content, latlng;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_register);
        spStore = new SharedPreferencesStore(getApplicationContext());
        txt_title = (EditText) findViewById(R.id.txt_title);
        txt_content = (EditText) findViewById(R.id.txt_content);
        txt_location = (TextView) findViewById(R.id.txt_location);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        latlng = "";
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = txt_title.getText().toString();
                content = txt_content.getText().toString();
                BackTask backTask = new BackTask();
                backTask.execute();
            }
        });


        txt_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(ComplaintRegisterActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ComplaintRegisterActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                progressDialog = ProgressDialog.show(ComplaintRegisterActivity.this, "",
                        "Fetching location Please Wait", true);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100000, 50000, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        progressDialog.hide();
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        latlng = latitude + "," + longitude;
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
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
            return WebService.complaintRegistration(spStore.getID(), title, content, latlng);
        }


        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            Log.d("response from server", response);
            try {
                JSONObject responseObject = new JSONObject(response);
                if (responseObject.getString("status").equals("success")) {
                    Toast.makeText(getApplicationContext(), responseObject.getString("message"), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), ComplaintsActivity.class));
                } else {
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
