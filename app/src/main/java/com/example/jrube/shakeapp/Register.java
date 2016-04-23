package com.example.jrube.shakeapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity implements AsyncResponse {

    EditText UsernameEt, PasswordEt, ConfirmEt;
    private String username;
    private String password;
    private String confirm;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_register);
        UsernameEt = (EditText)findViewById(R.id.regUsername);
        PasswordEt = (EditText)findViewById(R.id.regPassword);
        ConfirmEt = (EditText)findViewById(R.id.regConfirm);
        user = new User(this);
    }

    public void OnRegister(View view) {

        username = UsernameEt.getText().toString();
        password = PasswordEt.getText().toString();
        confirm = ConfirmEt.getText().toString();

        if(password.equals(confirm)) {
            String type = "register";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.delegate = this;
            backgroundWorker.execute(type, username, password);
        } else {
            Toast.makeText(this, "Passwords doesn't match!",
                    Toast.LENGTH_LONG).show();
        }

    }

    //this override the implemented method from asyncTask
    public void processFinish(String result) {
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.

        String created = user.UserRegister(result);

        if (created.equals("created")) {
            user.loginSuccess();
            finish();
        } else {
            Toast.makeText(this, "Username in use - Try again!",
                    Toast.LENGTH_LONG).show();
        }

        System.out.println(result);
    }

}
