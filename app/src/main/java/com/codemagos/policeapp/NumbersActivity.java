package com.codemagos.policeapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.codemagos.policeapp.Adapters.NumbersListAdapter;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    ImageButton btn_call;
    TextView txt_name, txt_number;
    ListView list_numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        list_numbers = (ListView) findViewById(R.id.list_numbers);
        ArrayList names = new ArrayList();
        final ArrayList numbers = new ArrayList();
            names.add("Controll Room");
        numbers.add("100");
        names.add("Pink Police");
        numbers.add("1515");

        NumbersListAdapter numbersListAdapter = new NumbersListAdapter(NumbersActivity.this, names, numbers);
        list_numbers.setAdapter(numbersListAdapter);




    }
}
