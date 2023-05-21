package com.ulfia.medapapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.riwayat);

        recyclerView = findViewById(R.id.recyclerView);
        db = FirebaseFirestore.getInstance();

        Query query = db.collection("appointments");

        FirestoreRecyclerOptions<Appointment> options = new FirestoreRecyclerOptions.Builder<Appointment>()
                .setQuery(query, Appointment.class)
                .build();

        FirestoreRecyclerAdapter<Appointment, AppointmentViewHolder> adapter = new FirestoreRecyclerAdapter<Appointment, AppointmentViewHolder>(options) {
            @NonNull
            @Override
            public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
                return new AppointmentViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position, @NonNull Appointment model) {
                holder.date.setText(model.getDate());
                holder.time.setText(model.getTime());
                holder.doctorName.setText(model.getDoctor());
                holder.specialistName.setText(model.getSpecialist());
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Start listening for Firestore updates
        adapter.startListening();

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.riwayat:
                        return true;

                    case R.id.beranda:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    private class AppointmentViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView time;
        private TextView doctorName;
        private TextView specialistName;

        public AppointmentViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            doctorName = itemView.findViewById(R.id.doctor_name);
            specialistName = itemView.findViewById(R.id.specialist_name);
        }
    }
}