package com.codemagos.policeapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.codemagos.policeapp.Adapters.ComplaintsAdapter;
import com.codemagos.policeapp.Adapters.NewsListAdapter;
import com.codemagos.policeapp.SharedPreferences.SharedPreferencesStore;
import com.codemagos.policeapp.Webservice.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ComplaintsActivity extends AppCompatActivity {
ListView list_complaints;
    SharedPreferencesStore spStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);
        spStore = new SharedPreferencesStore(getApplicationContext());
        list_complaints = (ListView) findViewById(R.id.list_complaints);
        BackTask backTask = new BackTask();
        backTask.execute();

    }

    protected class BackTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            return WebService.getComplaint(spStore.getID());

        }

        @Override
        protected void onPostExecute(String response) {
            Log.e("login", response);
            try {
                JSONObject responseObject = new JSONObject(response);
                if (responseObject.getString("status").equals("success")) {
                    Toast.makeText(getApplicationContext(), responseObject.getString("message"), Toast.LENGTH_SHORT).show();
                    JSONArray newsArray = new JSONArray(responseObject.getString("data"));
                    final ArrayList ids = new ArrayList();
                    final ArrayList titles = new ArrayList();
                    ArrayList dates = new ArrayList();
                    ArrayList images = new ArrayList();

                    for (int i = 0; i < newsArray.length(); i++) {
                        JSONObject news_item = newsArray.getJSONObject(i);
                        String id = news_item.getString("id");
                        String title = news_item.getString("title");
                        String date = news_item.getString("date");
                        String image = news_item.getString("image");
                        // adding data to arraylist
                        ids.add(id);
                        titles.add(title);
                        dates.add(date);
                        images.add(image);
                    }

                    // creating listadapter
                    ComplaintsAdapter complaintsAdapter = new ComplaintsAdapter(ComplaintsActivity.this, titles, dates, images);
                    // setting adpter to listview
                    list_complaints.setAdapter(complaintsAdapter);
                    // setting on item click listener to listview
                    list_complaints.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String clicked_id = ids.get(position).toString();
                            Intent newsIntent = new Intent(getApplicationContext(), NewsViewActivity.class);
                            newsIntent.putExtra("id", clicked_id);
                            newsIntent.putExtra("title", titles.get(position).toString());
                            startActivity(newsIntent);
                        }
                    });


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


