package com.codemagos.policeapp.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by prasanth on 11/3/17.
 */

public class SharedPreferencesStore {
    SharedPreferences sharedPreferences;

    public SharedPreferencesStore(Context context){
        sharedPreferences = context.getSharedPreferences("OPS_PREFERENCES",Context.MODE_PRIVATE);
    }

    public void setLogData(String id,String name,String email){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id",id);
        editor.putString("name",name);
        editor.putString("email",email);
    editor.commit();
    }
    public void clearLogData(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public String getID(){
        return sharedPreferences.getString("id","");
    }

    public String getName(){
        return sharedPreferences.getString("name","");
    }
    public String getEmail(){
        return sharedPreferences.getString("email","");
    }
}
