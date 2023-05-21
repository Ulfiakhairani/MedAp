package com.ulfia.medapapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BuatJanjiActivity extends Activity {
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button submitButton;
    private FirebaseFirestore db;
    private String doctorName;
    private String specialistName;

    private TextView doctorView;
    private TextView specialistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_janji);

        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        submitButton = findViewById(R.id.submit_button);
        doctorView = findViewById(R.id.doctor_name);
        specialistView = findViewById(R.id.speciality);

        doctorName = getIntent().getStringExtra("singleDoctor");
        specialistName = getIntent().getStringExtra("singleSpecialist");

        if(doctorName != null) {
            doctorView.setText(doctorName);
        } else {
            doctorView.setText("");
        }

        if(specialistName != null) {
            specialistView.setText(specialistName);
        } else {
            specialistView.setText("");
        }


        db = FirebaseFirestore.getInstance();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = datePicker.getDayOfMonth() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getYear();
                String time;
                if (android.os.Build.VERSION.SDK_INT >= 23 ) {
                    time = String.format(Locale.getDefault(), "%02d:%02d", timePicker.getHour(), timePicker.getMinute());
                } else {
                    time = String.format(Locale.getDefault(), "%02d:%02d", timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                }

                Map<String, Object> appointment = new HashMap<>();
                appointment.put("date", date);
                appointment.put("time", time);
                appointment.put("doctor", doctorName);
                appointment.put("specialist", specialistName);

                db.collection("appointments")
                        .add(appointment)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(BuatJanjiActivity.this, "Janji berhasil dibuat", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(BuatJanjiActivity.this, HistoryActivity.class));
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(BuatJanjiActivity.this, "Terjadi kesalahan, coba lagi", Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }
}