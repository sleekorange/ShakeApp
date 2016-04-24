package com.example.jrube.shakeapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Result extends AppCompatActivity implements AsyncResponse {
    User user;
    MediaPlayer player;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        user = new User(this);

        TextView receivedText = (TextView) findViewById(R.id.theScore);
        TextView message = (TextView) findViewById(R.id.message);
        TextView subMessage = (TextView) findViewById(R.id.subMessage);
        Button tryBtn = (Button) findViewById(R.id.retry);
        Button backBtn = (Button) findViewById(R.id.backStart);

        Intent intent = getIntent();
        String myScore = intent.getStringExtra(Shake.EXTRA_MESSAGE);

        receivedText.setText(myScore + " points");

        String userId = Integer.toString(user.getUserId());
        int userScore = user.getScore();
        int intScore = Integer.parseInt(myScore);

        Typeface mFont = Typeface.createFromAsset(getAssets(), "shake.ttf");

        message.setTypeface(mFont);
        subMessage.setTypeface(mFont);
        tryBtn.setTypeface(mFont);
        backBtn.setTypeface(mFont);
        receivedText.setTypeface(mFont);

        if(userScore < intScore){
            saveDb(intScore, userId);

            message.setText("Congratz!");
            subMessage.setText("New highscore!");

            player = MediaPlayer.create(this, R.raw.cheers2);

            player.start();
        } else {
            message.setText("Come on!");
            subMessage.setText("You can do better!");

            player = MediaPlayer.create(this,R.raw.booh);

            player.start();
        }



    }

    public void saveDb(int intScore, String userId) {

        String sScore = Integer.toString(intScore);
        String type = "saveScore";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.delegate = this;
        backgroundWorker.execute(type, userId, sScore);
    }

    //this override the implemented method from asyncTask
    public void processFinish(String result) {
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.

        boolean savedScore = user.saveDbScore(result);

        if (savedScore) {
            Toast.makeText(this, "Score saved in database",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Nothing saved",
                    Toast.LENGTH_LONG).show();
        }

    }

    public void retry(View view) {
        Intent intent  = new Intent(this, Shake.class);

        startActivity(intent);

    }

    public void menu(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(intent);

    }

}
