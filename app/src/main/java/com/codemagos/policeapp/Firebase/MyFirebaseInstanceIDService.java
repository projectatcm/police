package com.codemagos.policeapp.Firebase;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by prasanth on 11/3/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService{
    String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
       sendRegistrationToServer(refreshedToken);
    }


    public void sendRegistrationToServer(String token){

    }



   protected class BackTask extends AsyncTask<String,String,String>{

       @Override
       protected String doInBackground(String... params) {
           return null;
       }
   }
}
