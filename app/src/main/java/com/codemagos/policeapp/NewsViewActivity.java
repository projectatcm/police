package com.codemagos.policeapp;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.widget.TextView;
import android.widget.Toast;

import com.codemagos.policeapp.Webservice.ImageParse;
import com.codemagos.policeapp.Webservice.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class NewsViewActivity extends AppCompatActivity {
    CollapsingToolbarLayout toolbar_layout;
    Toolbar toolbar;
    String newsID,newsTitle;
    TextView txt_news,txt_title,txt_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_view);
        newsID = getIntent().getStringExtra("id");
        newsTitle = getIntent().getStringExtra("title");
    toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
/*        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
     getSupportActionBar().setHomeButtonEnabled(true);*/
        txt_news = (TextView) findViewById(R.id.txt_news);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_title = (TextView) findViewById(R.id.txt_title);



        BackTask backTask = new BackTask();
        backTask.execute();
    }

    protected class BackTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            return WebService.getNews(newsID);

        }

        @Override
        protected void onPostExecute(String response) {
            Log.e("login",response);
            try {
                JSONObject responseObject = new JSONObject(response);
                if (responseObject.getString("status").equals("success")) {
                    Toast.makeText(getApplicationContext(), responseObject.getString("message"), Toast.LENGTH_SHORT).show();
                    JSONArray newsArray = new JSONArray(responseObject.getString("data"));
                    JSONObject news  = newsArray.getJSONObject(0);
                    String news_title = news.getString("title");
                    String news_date = news.getString("date");
                    String news_body = news.getString("news");
                    String news_image = news.getString("image");
                    if(!news_image.equals("")){
                        Bitmap news_image_bitmap = ImageParse.base64ToImage(news_image);
                    toolbar_layout.setBackground(new BitmapDrawable(getResources(),news_image_bitmap));

                    }
                    txt_title.setText(news_title);
                    txt_date.setText(news_date);
                    txt_news.setText(news_body);


                }else{
                    // if the response if error
                    Toast.makeText(getApplicationContext(), responseObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("ERROR : ", response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




}
