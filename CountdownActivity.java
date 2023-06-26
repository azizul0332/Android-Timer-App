package com.example.staticappdevelopmentproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CountdownActivity extends AppCompatActivity {

    private TextView tv;
    private EditText countValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);

        tv = findViewById(R.id.tv);
        countValue = findViewById(R.id.countValue);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Counter");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Integer integerTime = intent.getIntExtra("TimeRemaining", 0);
                tv.setText(integerTime.toString());
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);

    }
    public void startButton(View view){
        Intent intentService = new Intent(this,countdownService.class);
        Integer integerTimeSet = Integer.parseInt(countValue.getText().toString());
        intentService.putExtra("TimeValue", integerTimeSet);
        startService(intentService);
    }
}