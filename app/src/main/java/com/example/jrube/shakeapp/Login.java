package com.example.jrube.shakeapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
    TextView messageTxt, registerLink, headText;
    TableLayout table_layout;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private String username;
    private String password;
    Button loginBtn;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UsernameEt = (EditText)findViewById(R.id.etUserName);
        PasswordEt = (EditText)findViewById(R.id.etPassword);
        loginBtn = (Button)findViewById(R.id.btnLogin);
        messageTxt = (TextView)findViewById(R.id.messageTxt);
        registerLink = (TextView)findViewById(R.id.registerLink);
        headText = (TextView)findViewById(R.id.headText);
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);
        user = new User(this);

        Typeface mFont = Typeface.createFromAsset(getAssets(), "shake.ttf");

        UsernameEt.setTypeface(mFont);
        PasswordEt.setTypeface(mFont);
        loginBtn.setTypeface(mFont);
        messageTxt.setTypeface(mFont);
        registerLink.setTypeface(mFont);
        headText.setTypeface(mFont);

    }

    public void OnLogin(View view) {

        username = UsernameEt.getText().toString();
        password = PasswordEt.getText().toString();


        String type = "login";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.delegate = this;
        backgroundWorker.execute(type, username, password);
    }

    public void register(View view) {
        Intent intent  = new Intent(this, Register.class);
        startActivity(intent);
    }

    //this override the implemented method from asyncTask
    public void processFinish(String result){
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.

        boolean logIn = user.UserLogIn(result);

        if(logIn){
            user.loginSuccess();
        } else {
            Toast.makeText(this, "Wrong user or password",
                    Toast.LENGTH_LONG).show();
        }
    }




}