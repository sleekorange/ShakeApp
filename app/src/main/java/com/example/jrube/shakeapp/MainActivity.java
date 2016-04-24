package com.example.jrube.shakeapp;

        import android.animation.Animator;
        import android.animation.AnimatorInflater;
        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Typeface;
        import android.media.Image;
        import android.os.Bundle;
        import android.os.Handler;
        import android.support.v7.app.ActionBarActivity;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.animation.Animation;
        import android.view.animation.AnimationSet;
        import android.view.animation.AnimationUtils;
        import android.view.animation.ScaleAnimation;
        import android.view.animation.TranslateAnimation;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.Toast;

        import java.util.Timer;
        import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.johan.main";
    User user;
    Button startBtn, highBtn, logoutBtn;
    ImageView logo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        user = new User(this);

        user.checkForLogin();
        startBtn = (Button)findViewById(R.id.startBtn);
        highBtn = (Button)findViewById(R.id.highBtn);
        logoutBtn = (Button)findViewById(R.id.logoutBtn);

        logo = (ImageView)findViewById(R.id.logo);

        Typeface mFont = Typeface.createFromAsset(getAssets(), "shake.ttf");

        startBtn.setTypeface(mFont);
        highBtn.setTypeface(mFont);
        logoutBtn.setTypeface(mFont);

       shakeLogo();
//     test();


    }

    public void shakeLogo(){

        final Animation shake = AnimationUtils
                .loadAnimation(getApplicationContext(), R.anim.shake);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                shake.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                       shakeLogo();
                    }
                });

                logo.startAnimation(shake);
            }
        },2000); //adding one sec delay
    }





//    public void test() {
//        final Animation shake = AnimationUtils
//                .loadAnimation(getApplicationContext(), R.anim.shake);
//
//
//
//        shake.setAnimationListener(new Animation.AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                logo.startAnimation(shake);
//            }
//        });
//
//        logo.startAnimation(shake);
//    }


    public void start(View view) {
        Intent intent  = new Intent(this, Shake.class);
        startActivity(intent);


    }

    public void highScore(View view) {
        Intent intent  = new Intent(this, Highscore.class);
        startActivity(intent);

    }

    public void logout(View view) {
        user.UserLogOut();
        finish();
        Toast.makeText(this, "You are logged out!",
                Toast.LENGTH_LONG).show();

    }
}
