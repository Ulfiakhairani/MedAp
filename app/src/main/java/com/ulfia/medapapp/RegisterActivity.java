package com.ulfia.medapapp;

//import beberapa library
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    //deklarasi variabel
    private static final String TAG = "TAG";
    EditText mNama, mEmail, mPonsel, mPassword;
    Button mDaftar;
    TextView mLoginNow;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //inisialisasi dengan menggunakan findeviewbyid
        mNama = findViewById(R.id.nama);
        mEmail = findViewById(R.id.email);
        mPonsel = findViewById(R.id.ponsel);
        mPassword = findViewById(R.id.password);
        mDaftar = findViewById(R.id.RegisterBtn);
        mLoginNow = findViewById(R.id.loginNowBtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        //untuk cek apakah pengguan sudah login atau belum
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            finish();
        }

        mDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //baca tulisan yang dimasukkan oleh user saat pengisian form
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String nama = mNama.getText().toString();
                String phone = mPonsel.getText().toString();

                //cek apakah email dan password sudah terisi
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email tidak boleh kosong");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password tidak boleh kosong");
                    return;
                }
                //tampilkan progress bar
                progressBar.setVisibility(View.VISIBLE);

                //mmebuat pengguna baru dengan email dan password
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Anda berhasil mendaftar", Toast.LENGTH_LONG).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("nama", nama);
                            user.put("email", email);
                            user.put("ponsel",phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "onSucces: user Profile is created for "+userID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        //hasil ketika teks login di klik
        mLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}