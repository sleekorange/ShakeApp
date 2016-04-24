package com.example.jrube.shakeapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;



import java.util.List;



public class Shake extends AppCompatActivity implements SensorEventListener {

    public final static String EXTRA_MESSAGE = "shakeApp.Shake.message";
    User user;
    int count = 1;
    int error = 0;
    int totalTime = 0;
    float procentage;
    Button startGameBtn;
    ImageView flask;
    private final int finalScore = 500;
    private boolean init;
    private Sensor mAccelerometer;
    private SensorManager mSensorManager;
    private float x1, x2, x3;
    private static final float ERROR = (float) 7.0;

    private TextView counter, xText, yText, zText, theProgress, userName;

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    MediaPlayer player;



;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        Intent intent = getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        user = new User(this);




        counter = (TextView) findViewById(R.id.counter);
        //xText = (TextView) findViewById(R.id.xText);
        //yText = (TextView) findViewById(R.id.yText);
        //zText = (TextView) findViewById(R.id.zText);
        userName = (TextView) findViewById(R.id.userName);
        theProgress = (TextView) findViewById(R.id.barStatus);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        startGameBtn = (Button) findViewById(R.id.startGame);

        flask = (ImageView)findViewById(R.id.flask);

        String username = user.getUserName();

        userName.setText("Press to start, " + username + ".");

        Typeface mFont = Typeface.createFromAsset(getAssets(), "shake.ttf");

        startGameBtn.setTypeface(mFont);
        userName.setTypeface(mFont);
        player = MediaPlayer.create(this,R.raw.shakingmini);

        theProgress.setVisibility(theProgress.INVISIBLE);
        progressBar.setVisibility(theProgress.INVISIBLE);





/*        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List listOfSensorsOnDevice = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (int i = 0; i < listOfSensorsOnDevice.size(); i++) {
            if (listOfSensorsOnDevice.get(i).equals(Sensor.TYPE_ACCELEROMETER)) {

                Toast.makeText(this, "ACCELEROMETER sensor is available on device", Toast.LENGTH_SHORT).show();


                init = false;

                mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

            } else {

                Toast.makeText(this, "ACCELEROMETER sensor is NOT available on device", Toast.LENGTH_SHORT).show();

                init = false;

                mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
                progressStatus = 0;
            }
        }*/

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List listOfSensorsOnDevice = mSensorManager.getSensorList(Sensor.TYPE_ALL);





    }


    public void startGame(View view) {

        Timer myTimer = new Timer();
        //Set the schedule function and rate
        myTimer.scheduleAtFixedRate(new TimerTask() {
                                        @Override
                                        public void run() {
                                            //Called at every 1000 milliseconds (1 second)
                                            totalTime++;
                                        }
                                    },
                //set the amount of time in milliseconds before first execution
                0,
                //Set the amount of time between each execution (in milliseconds)
                1000);


        init = false;

        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        progressStatus = 0;

        view.setVisibility(View.INVISIBLE);
        userName.setText("SHAKE! SHAKE! SHAKE!");

        //theProgress.setTextColor(Color.parseColor("#FFFFFF"));

        theProgress.setVisibility(theProgress.VISIBLE);
        progressBar.setVisibility(theProgress.VISIBLE);
    }

    public void shakeBottle(){

        final Animation shake = AnimationUtils
                .loadAnimation(getApplicationContext(), R.anim.shake);

        flask.startAnimation(shake);

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                shake.setAnimationListener(new Animation.AnimationListener() {
//
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        shakeBottle();
//                    }
//                });
//
//
//            }
//        },1000); //adding one sec delay
    }



    @Override
    public void onSensorChanged(SensorEvent e) {


        //Get x,y and z values
        float x,y,z;
        x = e.values[0];
        y = e.values[1];
        z = e.values[2];

//        xText.setText("X : "+ (int)x);
//        yText.setText("Y : "+ (int)y);
//        zText.setText("Z : "+ (int)z);


        if (!init) {
            x1 = x;
            x2 = y;
            x3 = z;
            init = true;
        } else {

            float diffX = Math.abs(x1 - x);
            float diffY = Math.abs(x2 - y);
            float diffZ = Math.abs(x3 - z);

            //Handling ACCELEROMETER Noise
            if (diffX < ERROR) {

                diffX = (float) 0.0;
            }
            if (diffY < ERROR) {
                diffY = (float) 0.0;
            }
            if (diffZ < ERROR) {

                diffZ = (float) 0.0;
            }


            x1 = x;
            x2 = y;
            x3 = z;


            if (diffZ > diffY) {

                error = error+1;
            }

            //Horizontal Shake Detected!!
            if (diffX < diffY) {

                //counter.setText("Shake Count : "+ count);
                count = count+1;
                procentage = (float)((count*100)/progressBar.getMax());
                //Toast.makeText(Shake.this, "Shake Detected!", Toast.LENGTH_SHORT).show();
                if (progressStatus < progressBar.getMax()) {
                    progressStatus = count;
                    progressBar.setProgress(progressStatus);
                    theProgress.setText(procentage + " %");

                }

//                procentage = (float)((count*100)/maxCount);
//                String sProcentage = Float.toString(procentage);
//
//                System.out.println("COUNT: "+ count);
//                System.out.println("MAX: "+ maxCount);
//                System.out.println("The procentage: "+ procentage);
//
//                switch (sProcentage) {
//                        case "1.0":  player = MediaPlayer.create(this,R.raw.shaking); player.start();
//                        break;
//                        case "25.0":  player.stop(); player = MediaPlayer.create(this,R.raw.shaking); player.start();
//                            break;
//                        case "50.0":  player.stop(); player = MediaPlayer.create(this,R.raw.shaking); player.start();
//                            break;
//                        case "75.0":  player.stop(); player = MediaPlayer.create(this,R.raw.shaking); player.start();
//                            break;
//                        case "90.0":  player.stop(); player = MediaPlayer.create(this,R.raw.shaking); player.start();
//                            break;
//                    }


                if(player.isPlaying() == false){
                    player.start();
                    //player.setLooping(true);
                }



                if (count == progressBar.getMax()) {

                    player.stop();

                    player = MediaPlayer.create(this,R.raw.pop);

                    player.start();

                    Intent intent = new Intent(this, Result.class);

                    String message = Integer.toString((finalScore - totalTime) - error);
                    intent.putExtra(EXTRA_MESSAGE, message);
                    startActivity(intent);
                }

//                new Thread(new Runnable() {
//                    public void run() {
//                        while(progressStatus < 100) {
//
//                            handler.post(new Runnable() {
//                                public void run() {
//                                    progressBar.setProgress(progressStatus);
//                                }
//                            });
//                            try {
//                                Thread.sleep(100);
//                            }
//                            catch (InterruptedException e){
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }).start();
            }
        }


    }


    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Noting to do!!
    }

    //Register the Listener when the Activity is resumed
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    //Unregister the Listener when the Activity is paused
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void back(View view) {
        finish();
    }
}