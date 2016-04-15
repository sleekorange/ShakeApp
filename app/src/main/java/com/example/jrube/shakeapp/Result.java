package com.example.jrube.shakeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Result extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView receivedText = (TextView) findViewById(R.id.theScore);

        Intent intent = getIntent();
        String myScore = intent.getStringExtra(Shake.EXTRA_MESSAGE);

        receivedText.setText("Your score: " + myScore);

    }

    public void retry(View view) {
        Intent intent  = new Intent(this, Shake.class);
        startActivity(intent);

    }

    public void menu(View view) {
        Intent intent  = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}
