package com.ulfia.medapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class IntroActivity extends AppCompatActivity {
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        btnStart = findViewById(R.id.btn_start);

        //ketika di klik tombolnya akan mengarah ke halaman login
        btnStart.setOnClickListener(view -> {
            Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
            startActivity(intent);
        });
//        btnStart.setOnClickListener(view -> {
//            Intent intent = new Intent(IntroActivity.this, RegisterActivity.class);
//            startActivity(intent);
//        });
    }
}