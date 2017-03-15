package com.codemagos.policeapp.Adapters;

import android.app.Activity;
import android.content.Context;
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
 * Created by prasanth on 13/3/17.
 */

public class NewsListAdapter extends ArrayAdapter {
    ArrayList titles;
    ArrayList imaegs;
    ArrayList dates;
    Activity activity;

    public NewsListAdapter(Activity activity,ArrayList titles,ArrayList dates,ArrayList images) {
        super(activity, R.layout.listview_news_row,titles);
        this.activity = activity;
        this.titles = titles;
        this.dates = dates;
        this.imaegs = images;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
       View rowView = layoutInflater.inflate(R.layout.listview_news_row,null);

        ImageView img_image = (ImageView) rowView.findViewById(R.id.img_image);
        TextView txt_title = (TextView) rowView.findViewById(R.id.txt_title);
        TextView txt_date = (TextView) rowView.findViewById(R.id.txt_date);

        String title = titles.get(position).toString();
        String date = dates.get(position).toString();
        String image = imaegs.get(position).toString();

        txt_title.setText(title);
        txt_date.setText(date);

        if(!image.equals("")){
            // converting image to bitmap using imageparse class
            try {
                Bitmap bitmapImage = ImageParse.base64ToImage(image);
                img_image.setImageBitmap(bitmapImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return rowView;

    }
}
