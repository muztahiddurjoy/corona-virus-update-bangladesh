package com.epizy.muztahiddurjoy.coronavirusupdatebangladesh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.scwang.wave.MultiWaveHeader;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
private TextView totalAffected, totalDead, totalRecovered, totalActiveCases, totalCriticalCases, totalTested, todayAffected, todayDead, todayRecovered,
    todayActiveCases, todayCriticalCases, todayTested,totalDeathRatio,todayDeathRatio,totalRecoveredRatio,todayRecoveredRatio;
private RequestQueue queue;
private Button today,visit;
private MultiWaveHeader header;
private static DecimalFormat df2 = new DecimalFormat("#.##");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        totalAffected = (TextView) findViewById(R.id.total_affected);
        totalDead = (TextView) findViewById(R.id.total_dead);
        totalRecovered = (TextView) findViewById(R.id.total_recovered);
        totalActiveCases = (TextView) findViewById(R.id.total_active_cases);
        totalCriticalCases = (TextView) findViewById(R.id.total_critical_cases);
        totalTested = (TextView) findViewById(R.id.total_tested);
        todayAffected = (TextView) findViewById(R.id.today_affected);
        todayDead = (TextView) findViewById(R.id.today_dead);
        todayRecovered = (TextView) findViewById(R.id.today_recovered);
        todayActiveCases = (TextView) findViewById(R.id.today_total_case);
        todayCriticalCases = (TextView) findViewById(R.id.today_critical_case);
        todayTested = (TextView) findViewById(R.id.today_tested);
        totalDeathRatio = (TextView) findViewById(R.id.total_death_ratio);
        todayDeathRatio = (TextView) findViewById(R.id.today_death_ratio);
        totalRecoveredRatio = (TextView) findViewById(R.id.total_recover_ratio);
        todayRecoveredRatio = (TextView) findViewById(R.id.today_recover_ratio);
        header = (MultiWaveHeader) findViewById(R.id.wave);
        visit = (Button) findViewById(R.id.visitSite);


        header.setVelocity(1);
        header.setProgress(1);
        header.isRunning();
        header.setGradientAngle(45);
        header.setWaveHeight(45);



        visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://corona.gov.bd/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        today = (Button) findViewById(R.id.refreshtoday);
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseData();
            }
        });

        queue = Volley.newRequestQueue(this);

        parseData();

        createNotificationChannel();
        Intent intent = new Intent(MainActivity.this,ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent,0);


        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 16);
        c.set(Calendar.MINUTE, 31);
        c.set(Calendar.SECOND,0);
        alarmManager.setRepeating(AlarmManager.RTC,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void parseData() {
        String url = "https://coronavirus-map.p.rapidapi.com/v1/summary/region?region=bangladesh";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ProgressDialog dialog = new ProgressDialog(MainActivity.this);
                            dialog.setTitle("Loading Data!");
                            dialog.show();
                            JSONObject object = response.getJSONObject("data");
                            //total situation
                            JSONObject object1 = object.getJSONObject("summary");
                            String totaldeaths = object1.getString("deaths");
                            String totalaffcted = object1.getString("total_cases");
                            String totalrecovered = object1.getString("recovered");
                            String totalactive = object1.getString("active_cases");
                            String totalcritical = object1.getString("critical");
                            String totaltested = object1.getString("tested");
                            String totaldeathratio = object1.getString("death_ratio");
                            String totalrecoveredratio = object1.getString("recovery_ratio");
                            totalAffected.setText(totalaffcted);
                            totalDead.setText(totaldeaths);
                            totalRecovered.setText(totalrecovered);
                            totalActiveCases.setText(totalactive);
                            totalCriticalCases.setText(totalcritical);
                            totalTested.setText(totaltested);
                            totalDeathRatio.setText(df2.format(Double.parseDouble(totaldeathratio)));
                            totalRecoveredRatio.setText(df2.format(Double.parseDouble(totalrecoveredratio)));

                            //today's situation
                            JSONObject object2 = object.getJSONObject("change");
                            String todaydeaths = object2.getString("deaths");
                            String todayaffcted = object2.getString("total_cases");
                            String todayrecovered = object2.getString("recovered");
                            String todayactive = object2.getString("active_cases");
                            String todaycritical = object2.getString("critical");
                            String todaytested = object2.getString("tested");
                            String todaydeathratio = object2.getString("death_ratio");
                            String todayrecoveredratio = object2.getString("recovery_ratio");

                            todayAffected.setText(todayaffcted);
                            todayDead.setText(todaydeaths);
                            todayRecovered.setText(todayrecovered);
                            todayActiveCases.setText(todayactive);
                            todayTested.setText(todaytested);
                            todayCriticalCases.setText(todaycritical);
                            todayDeathRatio.setText(df2.format(Double.parseDouble(todaydeathratio)));
                            todayRecoveredRatio.setText(df2.format(Double.parseDouble(todayrecoveredratio)));

                            dialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Data loading failed!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("x-rapidapi-key","ef9b6d72fcmshba5f440c554d904p123142jsn18adbf91c52d");
                map.put("x-rapidapi-host","coronavirus-map.p.rapidapi.com");
                return map;
            }
        };
        queue.add(request);
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            CharSequence name = "coronavirusbangladeshupdate";
            String description = "Channel for Corona App";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("coronavirusbangladeshupdate",name,importance);
            channel.setDescription(description);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

}