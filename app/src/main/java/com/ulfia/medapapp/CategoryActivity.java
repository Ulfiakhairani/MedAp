package com.ulfia.medapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CategoryActivity extends AppCompatActivity {
    Button upload;
    ImageView imageCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        upload = findViewById(R.id.upload);
        imageCategory = findViewById(R.id.imageCategory);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, UploadProductActivity.class);
                startActivity(intent);
            }
        });

        imageCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, ListDoctorsActivity.class);
                startActivity(intent);
            }
        });
    }
}