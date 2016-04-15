package com.example.jrube.shakeapp;

        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.support.v7.app.ActionBarActivity;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.johan.main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

    }

    public void change(View view) {
        Intent intent  = new Intent(this, Shake.class);
        startActivity(intent);

    }

    public void login(View view) {
        Intent intent  = new Intent(this, Login.class);
        startActivity(intent);

    }

    public void logout(View view) {
        SharedPreferences loginPreferences;
        SharedPreferences.Editor loginPrefsEditor;

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        loginPrefsEditor.putString("saveLogin", "false");
        loginPrefsEditor.commit();

        Toast.makeText(this, "You are logged out!",
                Toast.LENGTH_LONG).show();

    }
}
