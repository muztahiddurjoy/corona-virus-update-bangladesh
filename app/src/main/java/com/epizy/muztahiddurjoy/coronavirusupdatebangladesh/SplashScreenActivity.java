package com.epizy.muztahiddurjoy.coronavirusupdatebangladesh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.NetworkInterface;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if(internetEnabled()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
                }
            },2000);
        }
        else {
            startActivity(new Intent(SplashScreenActivity.this,NoInternetActivity.class));
        }

    }

    private boolean internetEnabled() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        boolean flag = false;
        if (info!=null){
            if (info.getType()== ConnectivityManager.TYPE_WIFI){
                flag = true;
            }
            else if(info.getType() == ConnectivityManager.TYPE_MOBILE){
                flag = true;
            }
            else {
                flag = false;
            }
        }
        return flag;
    }
}