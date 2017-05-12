package com.codemagos.policeapp.Webservice;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Sree on 01-Oct-15.
 */
public class WebService {

/*    static String reg_url = "http://codemagos.in/Mybook/index.php";*/
    static String SITE_URL = "http://10.0.2.2/project/ops_server/webservice/";

    public static String postData(String action_URL, String data) {
        String responce = "";
        URL url = null;
        Log.w("-->", "in web service");
        try {
            Log.w("-->", "in web service try");
            url = new URL(SITE_URL + action_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                responce += line;

            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return responce;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.w("-->", "in web service first catch");
        } catch (ProtocolException e) {
            Log.w("-->", "in web service second catch");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responce;
    }

    public static String getData(String action_URL) {
        String responce = "";
        URL url = null;
        Log.w("-->", "in web service");
        try {
            Log.w("-->", "in web service try");
            url = new URL(SITE_URL + action_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                responce += line;

            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return responce;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.w("-->", "in web service first catch");
        } catch (ProtocolException e) {
            Log.w("-->", "in web service second catch");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responce;
    }

    public static String userRegistration(String name,String email,String mobile,String password,String aadhar,String device_id, String fcm_id) {
        Log.w("-->", "in uer login web service method");
        String data = null;
        try {
            data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                    URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                    URLEncoder.encode("mobile", "UTF-8") + "=" + URLEncoder.encode(mobile, "UTF-8") + "&" +
                    URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&" +
                    URLEncoder.encode("aadhar", "UTF-8") + "=" + URLEncoder.encode(aadhar, "UTF-8") + "&" +
                    URLEncoder.encode("device_id", "UTF-8") + "=" + URLEncoder.encode(device_id, "UTF-8") + "&" +
                    URLEncoder.encode("fcm_id", "UTF-8") + "=" + URLEncoder.encode(fcm_id, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return postData("register_user.php", data);

    }
    public static String userLogin(String device_id, String password) {
        Log.w("-->", "in uer login web service method");
        String data = null;
        try {
            data = URLEncoder.encode("device_id", "UTF-8") + "=" + URLEncoder.encode(device_id, "UTF-8") + "&" +
                    URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return postData("login.php", data);

    }

    public static String getNews() {
        String url = "get_news.php";
        String responce = getData(url);
        return responce;
    }
    public static String getContacts() {
        String url = "get_contacts.php";
        String responce = getData(url);
        return responce;
    }
    public static String getContacts(String key) {
        String url = "get_contacts.php?key="+key;
        String responce = getData(url);
        return responce;
    }
    public static String getNews(String id) {
        String url = "get_news.php?id="+id;
        String responce = getData(url);
        return responce;
    }
    public static String complaintRegistration(String user_id,String title,String content,String location) {
        Log.w("-->", "in uer login web service method");
        String data = null;
        try {
            data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8") + "&" +
                    URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(title, "UTF-8") + "&" +
                    URLEncoder.encode("content", "UTF-8") + "=" + URLEncoder.encode(content, "UTF-8") + "&" +
                    URLEncoder.encode("location", "UTF-8") + "=" + URLEncoder.encode(location, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return postData("register_user.php", data);

    }
    public static String getComplaint(String id) {
        String url = "get_complaint.php?id="+id;
        String responce = getData(url);
        return responce;
    }
    public static String updateFCMToken(String id,String token) {
        String url = "update_fcm_token.php";
        String data = null;
        try {
             data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&" +
                    URLEncoder.encode("fcm_id", "UTF-8") + "=" + URLEncoder.encode(token, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return postData(url, data);
    }





}
