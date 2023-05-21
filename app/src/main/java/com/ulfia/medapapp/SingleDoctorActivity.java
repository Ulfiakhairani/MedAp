package com.ulfia.medapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SingleDoctorActivity extends AppCompatActivity {
    TextView singleDoctor, singleSpecialist, singleLocation, singlePatient, singleRating, singleDescription;
    ImageView singleImage;
    Button btnJanji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_doctor);

        singleDoctor = findViewById(R.id.singleDoctor);
        singleSpecialist = findViewById(R.id.singleSpecialist);
        singleLocation = findViewById(R.id.singleLocation);
        singlePatient = findViewById(R.id.singlePatient);
        singleRating = findViewById(R.id.singleRating);
        singleDescription = findViewById(R.id.singleDescription);
        btnJanji = findViewById(R.id.btnJanji);

        singleImage = findViewById(R.id.singleImage);

        Picasso.get().load(getIntent().getStringExtra("singleImage"))
                .placeholder(R.drawable.upload)
                .into(singleImage);

        singleDoctor.setText(getIntent().getStringExtra("singleDoctor"));
        singleSpecialist.setText(getIntent().getStringExtra("singleSpecialist"));
        singleLocation.setText(getIntent().getStringExtra("singleLocation"));
        singlePatient.setText(getIntent().getStringExtra("singlePatient"));
        singleRating.setText(getIntent().getStringExtra("singleRating"));
        singleDescription.setText(getIntent().getStringExtra("singleDescription"));

        btnJanji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingleDoctorActivity.this, BuatJanjiActivity.class);
                intent.putExtra("singleDoctor", singleDoctor.getText().toString());
                intent.putExtra("singleSpecialist", singleSpecialist.getText().toString());
                startActivity(intent);
            }
        });

    }
}