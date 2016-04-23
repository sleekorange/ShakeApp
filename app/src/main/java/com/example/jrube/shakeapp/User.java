package com.example.jrube.shakeapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Johan on 21/04/16.
 */
public class User {

    SharedPreferences loginPreferences;
    Context context;


    public User(Context context) {
        loginPreferences = context.getSharedPreferences("loginPrefs", 0);
        this.context = context.getApplicationContext();
    }

    public void checkForLogin(){
        SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();

        String username = loginPreferences.getString("username", "");

        if (username.length() > 0) {
            //loginSuccess();
        }
        else {
            Intent intent = new Intent(context.getApplicationContext(),Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(intent);
        }

    }

    public boolean UserLogIn(String result) {
        SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();

        try {
            JSONArray jArr = new JSONArray(result);
            JSONObject jObj = jArr.getJSONObject(0);

            System.out.println(result);
            System.out.println(jObj);
            System.out.println(jObj.getString("status"));


            if(jObj.getString("status").equals("success")) {
                loginPrefsEditor.putInt("id", jObj.getInt("id"));
                loginPrefsEditor.putString("username", jObj.getString("username"));
                loginPrefsEditor.putInt("score", jObj.getInt("score"));
                loginPrefsEditor.commit();

                return true;
            } else {
                System.out.println("Wrong user or password input");
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public String UserRegister(String result) {
        SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();

        try {
            JSONArray jArr = new JSONArray(result);
            JSONObject jObj = jArr.getJSONObject(0);

            System.out.println(result);
            System.out.println(jObj);
            System.out.println(jObj.getString("status"));


            if(jObj.getString("status").equals("success")) {
                loginPrefsEditor.putInt("id", jObj.getInt("id"));
                loginPrefsEditor.putString("username", jObj.getString("username"));
                loginPrefsEditor.putInt("score", 0);
                loginPrefsEditor.commit();

                return "created";
            } else {
                System.out.println("Could not create user");
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    public void UserLogOut() {

        SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();

        loginPrefsEditor.clear();
        loginPrefsEditor.commit();
        checkForLogin();
    }

    public int getUserId() {
        SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();

        int userId = loginPreferences.getInt("id", -1);

        return userId;
    }

    public String getUserName() {

        SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();

        String userName = loginPreferences.getString("username", "");

        return userName;
    }

    public int getScore() {
        SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();

        int score = loginPreferences.getInt("score", 0);

        return score;
    }

//    public void saveScore(int score) {
//        SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();
//
//        loginPrefsEditor.putInt("score", score);
//        loginPrefsEditor.commit();
//    }

    public boolean saveDbScore(String result) {
        SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();

        try {
            JSONArray jArr = new JSONArray(result);
            JSONObject jObj = jArr.getJSONObject(0);

            System.out.println(result);
            System.out.println(jObj);
            System.out.println(jObj.getString("status"));


            if(jObj.getString("status").equals("success")) {
                loginPrefsEditor.putInt("score", jObj.getInt("score"));
                loginPrefsEditor.commit();

                return true;
            } else {
                System.out.println("Could not update the score");
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public void loginSuccess(){
        Intent intent = new Intent(context.getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }


}
