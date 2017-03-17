package com.codemagos.policeapp.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codemagos.policeapp.R;

import java.util.ArrayList;

/**
 * Created by prasanth on 17/3/17.
 */

public class NumbersListAdapter extends ArrayAdapter {
    ArrayList names;
    ArrayList numbers;
    Activity activity;

    public NumbersListAdapter(Activity activity, ArrayList names, ArrayList numbers) {
        super(activity, R.layout.list_number_row, names);
        this.activity = activity;
        this.names = names;
        this.numbers = numbers;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View rowView = layoutInflater.inflate(R.layout.list_number_row, null);

        TextView txt_name = (TextView) rowView.findViewById(R.id.txt_name);
        TextView txt_number = (TextView) rowView.findViewById(R.id.txt_number);

        String title = names.get(position).toString();
        String date = numbers.get(position).toString();

        txt_name.setText(title);
        txt_number.setText(date);



        return rowView;

    }
}
