package com.ulfia.medapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class UploadProductActivity extends AppCompatActivity {
    TextView doctor_name, description, specialist, location, patients, rating;
    ImageView uploadbtn, productImage;
    Button submit;
    Uri ImageUri;
    RelativeLayout relativeLayout;

    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product);

        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Please Wait...");
        dialog.setCancelable(false);
        dialog.setTitle("Product Uploading");
        dialog.setCanceledOnTouchOutside(false);

        doctor_name = findViewById(R.id.doctor_name);
        description = findViewById(R.id.description);
        specialist = findViewById(R.id.specialist);
        location = findViewById(R.id.location);
        patients = findViewById(R.id.patients);
        rating = findViewById(R.id.rating);
        uploadbtn = findViewById(R.id.uploadbtn);
        productImage = findViewById(R.id.productImage);
        submit = findViewById(R.id.submit);
        relativeLayout = findViewById(R.id.relative);

        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
                relativeLayout.setVisibility(View.VISIBLE);
                uploadbtn.setVisibility(View.GONE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                final StorageReference reference = firebaseStorage.getReference().child("dokter")
                        .child(System.currentTimeMillis()+"");
                reference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                ProjectModel model = new ProjectModel();
                                model.setProductImage(uri.toString());

                                model.setDoctor_name(doctor_name.getText().toString());
                                model.setDescription(description.getText().toString());
                                model.setSpecialist(specialist.getText().toString());
                                model.setLocation(location.getText().toString());
                                model.setPatients(patients.getText().toString());
                                model.setRating(rating.getText().toString());

                                database.getReference().child("dokter").push().setValue(model)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(UploadProductActivity.this, "Dokter Upload Successfully", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        dialog.dismiss();
                                        Toast.makeText(UploadProductActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

                    }
                });

            }
        });
    }

    private void UploadImage() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, 101);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(UploadProductActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();

                    }
                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK){
            ImageUri = data.getData();
            productImage.setImageURI(ImageUri);
        }
    }
}