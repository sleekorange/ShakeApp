package com.example.jrube.shakeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


public class Login extends AppCompatActivity implements AsyncResponse  {

    EditText UsernameEt, PasswordEt;
    TableLayout table_layout;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private String saveLogin;
    private String username;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UsernameEt = (EditText)findViewById(R.id.etUserName);
        PasswordEt = (EditText)findViewById(R.id.etPassword);
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);
        saveLoginCheckBox = (CheckBox)findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getString("saveLogin", "false");

        System.out.println("Save login is: " +saveLogin);

        if (saveLogin.equals("true")) {
            loginSuccess();
        }
    }

    public void OnLogin(View view) {

        username = UsernameEt.getText().toString();
        password = PasswordEt.getText().toString();

        if(saveLoginCheckBox.isChecked()){
            saveLogin = "true";
        } else {
            saveLogin = "false";
        }

        String type = "login";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.delegate = this;
        backgroundWorker.execute(type, username, password, saveLogin);
    }

    //this override the implemented method from asyncTask
    public void processFinish(String result){
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(UsernameEt.getWindowToken(), 0);



        try {
            JSONArray jArr = new JSONArray(result);
            JSONObject jObj = jArr.getJSONObject(0);

            System.out.println(result);
            System.out.println(jObj);
            System.out.println(jObj.getString("status"));


            if(jObj.getString("status").equals("success")) {


                if (jObj.getString("saveLogin").equals("true")) {
                    loginPrefsEditor.putString("saveLogin", jObj.getString("saveLogin"));
                    loginPrefsEditor.putString("username", jObj.getString("username"));
                    loginPrefsEditor.putString("password", jObj.getString("password"));
                    loginPrefsEditor.commit();
                    System.out.println("Saved");
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                    System.out.println("No save :(");
                }

                loginSuccess();
            } else {
                System.out.println("Wrong user or password input");
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }





//        try {
//            JSONArray jArr = new JSONArray(result);
///*            JSONObject jObj = jArr.getJSONObject(0);
//
//            String pmid = jObj.getString("pmid");
//
//            JSONArray mJsonArrayProperty = jObj.getJSONArray("properties");*/
//            for (int i = 0; i < jArr.length(); i++) {
//                JSONObject jObj = jArr.getJSONObject(i);
//
//                TableRow row = new TableRow(this);
//                row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
//                        LayoutParams.WRAP_CONTENT));
//
//                String myUser = jObj.getString("username");
//                System.out.println(myUser);
//
//                for (int j = 0; j < jObj.length(); j++) {
//
//
//                    TextView tv = new TextView(this);
//                    tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
//                            LayoutParams.WRAP_CONTENT));
//                    tv.setGravity(Gravity.CENTER);
//                    tv.setTextSize(18);
//                    tv.setPadding(0, 5, 0, 5);
//
//                    switch (j) {
//                        case 0:  tv.setText(jObj.getString("id"));
//                            break;
//                        case 1:  tv.setText(jObj.getString("username"));
//                            break;
//                        case 2:  tv.setText(jObj.getString("password"));
//                            break;
//                    }
//
//                    row.addView(tv);
//
//                }
//                table_layout.addView(row);
//            }
//        }
//        catch (JSONException e)
//        {
//           e.printStackTrace();
//        }



//        JSONObject jObj = null;
//        try {
//            String jsonStr = result;
//            jsonStr = jsonStr.substring(1, jsonStr.length()-1);
//            System.out.println(jsonStr);
//            jObj = new JSONObject(jsonStr);
//
//            String myUser = "User="+jObj.get("username");
//
//            System.out.println(myUser);
//            System.out.println("Lenght is: " + jObj.length());
//
///*            for(int i = 0; i<jObj.length(); i++){
//                //Log.v(TAG, "key = " + jobject.names().getString(i) + " value = " + jobject.get(jobject.names().getString(i)));
//                String key = jObj.getString(i);
//                System.out.println( + jObj.get(jObj.getString(i)));
//            }*/
//
//            // inner for loop
///*            for (int j = 0; j < cols; j++) {
//
//                TextView tv = new TextView(this);
//                tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
//                        LayoutParams.WRAP_CONTENT));
//                tv.setBackgroundResource(R.drawable.cell_shape);
//                tv.setGravity(Gravity.CENTER);
//                tv.setTextSize(18);
//                tv.setPadding(0, 5, 0, 5);
//
//                tv.setText(c.getString(j));
//
//                row.addView(tv);
//
//            }
//
//            c.moveToNext();
//
//            table_layout.addView(row);*/
//
//
//        } catch (JSONException e)
//        {
//            e.printStackTrace();
//
//        }
    }

    public void loginSuccess(){
        startActivity(new Intent(Login.this, MainActivity.class));
        Login.this.finish();
    }

}