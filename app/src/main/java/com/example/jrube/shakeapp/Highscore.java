package com.example.jrube.shakeapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Highscore extends AppCompatActivity implements AsyncResponse {

    TextView headText;
    TableLayout table_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        headText = (TextView)findViewById(R.id.headText);
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);
        Button backMenu = (Button)findViewById(R.id.backMenu);

        Typeface mFont = Typeface.createFromAsset(getAssets(), "shake.ttf");

        headText.setTypeface(mFont);
        backMenu.setTypeface(mFont);

        showScores();

    }

    public void showScores() {

        String type = "highscore";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.delegate = this;
        backgroundWorker.execute(type);
    }

    //this override the implemented method from asyncTask
    public void processFinish(String result){
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.


        try {
            JSONArray jArr = new JSONArray(result);
            JSONObject jObj = jArr.getJSONObject(0);
            System.out.println(result);

            if(jObj.getString("status").equals("success")) {
                for (int i = 0; i < jArr.length(); i++) {
                    jObj = jArr.getJSONObject(i);

                    TableRow row = new TableRow(this);
                    row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                            LayoutParams.WRAP_CONTENT));

                    String myUser = jObj.getString("username");
                    System.out.println(myUser);

                    for (int j = 0; j < 3; j++) {

                        TextView tv = new TextView(this);
                        tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT));
                        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) tv
                                .getLayoutParams();
                        Typeface mFont = Typeface.createFromAsset(getAssets(), "shake.ttf");
                        tv.setTypeface(mFont);
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(18);
                        tv.setPadding(0, 5, 0, 5);
                        tv.setTextColor(Color.parseColor("#FFFFFF"));
                        tv.setBackgroundColor(Color.parseColor("#0a1824"));
                        mlp.setMargins(0, 0, 10, 0);

                        String place = Integer.toString(i+1);

                        switch (j) {
                            case 0:
                                tv.setText(place);
                                break;
                            case 1:
                                tv.setText(jObj.getString("username"));
                                break;
                            case 2:
                                tv.setText(jObj.getString("score"));
                                break;
                        }

                        row.addView(tv);

                    }
                    table_layout.addView(row);
                }

            }else {
                System.out.println("Something went wrong");
                System.out.println(result);
            }
        }
        catch (JSONException e)
        {
           e.printStackTrace();
        }



/*        JSONObject jObj = null;
        try {
            String jsonStr = result;
            jsonStr = jsonStr.substring(1, jsonStr.length()-1);
            System.out.println(jsonStr);
            jObj = new JSONObject(jsonStr);

            String myUser = "User="+jObj.get("username");

            System.out.println(myUser);
            System.out.println("Lenght is: " + jObj.length());

            for(int i = 0; i<jObj.length(); i++){
                //Log.v(TAG, "key = " + jobject.names().getString(i) + " value = " + jobject.get(jobject.names().getString(i)));
                String key = jObj.getString(i);
                System.out.println( + jObj.get(jObj.getString(i)));
            }

            // inner for loop
            for (int j = 0; j < cols; j++) {

                TextView tv = new TextView(this);
                tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT));
                tv.setBackgroundResource(R.drawable.cell_shape);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(18);
                tv.setPadding(0, 5, 0, 5);

                tv.setText(c.getString(j));

                row.addView(tv);

            }

            c.moveToNext();

            table_layout.addView(row);


        } catch (JSONException e)
        {
            e.printStackTrace();

        }*/
    }

    public void backMenu(View view) {
        finish();
    }

}
