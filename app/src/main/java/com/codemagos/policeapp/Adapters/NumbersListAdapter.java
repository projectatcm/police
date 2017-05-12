package com.codemagos.policeapp.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.codemagos.policeapp.Data.Contact;
import com.codemagos.policeapp.R;

import java.util.ArrayList;

/**
 * Created by prasanth on 17/3/17.
 */

public class NumbersListAdapter extends ArrayAdapter {
    ArrayList<Contact> list;
    Activity activity;

    public NumbersListAdapter(Activity activity, ArrayList list) {
        super(activity, R.layout.list_number_row, list);
        this.activity = activity;
        this.list = list;

    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View rowView = layoutInflater.inflate(R.layout.list_number_row, null);

        TextView txt_name = (TextView) rowView.findViewById(R.id.txt_name);
        TextView txt_number = (TextView) rowView.findViewById(R.id.txt_number);
        ImageButton btn_call = (ImageButton) rowView.findViewById(R.id.btn_call);

        Contact contact = list.get(position);

        String title = contact.getName();
        final String phone = contact.getPhone();

        txt_name.setText(title);
        txt_number.setText(phone);
        btn_call.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(activity.getApplicationContext(), "Calling...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(activity.getApplicationContext(),"Calling Permission Required...",Toast.LENGTH_LONG).show();
            return;
        }
       activity.startActivity(intent);
    }
});


        return rowView;

    }
}
