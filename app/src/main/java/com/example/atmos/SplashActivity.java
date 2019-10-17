package com.example.atmos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.karan.churi.PermissionManager.PermissionManager;

import java.util.ArrayList;

public class SplashActivity extends Activity implements Runnable {

    PermissionManager permissionManager;

    Thread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mThread = new Thread(this);

        mThread.start();
    }
    @Override
    public void run(){
        try {
            Thread.sleep(4550);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

}