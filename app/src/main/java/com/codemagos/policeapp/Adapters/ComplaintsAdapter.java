package com.codemagos.policeapp.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codemagos.policeapp.R;
import com.codemagos.policeapp.Webservice.ImageParse;

import java.util.ArrayList;

/**
 * Created by prasanth on 15/3/17.
 */

public class ComplaintsAdapter extends ArrayAdapter {
    ArrayList titles;
    ArrayList dates;
    ArrayList status;
    Activity activity;

    public ComplaintsAdapter(Activity activity,ArrayList titles,ArrayList dates,ArrayList status) {
        super(activity, R.layout.listview_news_row,titles);
        this.activity = activity;
        this.titles = titles;
        this.dates = dates;
        this.status = status;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View rowView = layoutInflater.inflate(R.layout.listview_news_row,null);

        TextView txt_status = (TextView) rowView.findViewById(R.id.txt_status);
        TextView txt_title = (TextView) rowView.findViewById(R.id.txt_title);
        TextView txt_date = (TextView) rowView.findViewById(R.id.txt_date);

        String title = titles.get(position).toString();
        String date = dates.get(position).toString();
        String stat = status.get(position).toString();

        txt_title.setText(title);
        txt_date.setText(date);
        txt_status.setText(stat);



        return rowView;

    }
}
