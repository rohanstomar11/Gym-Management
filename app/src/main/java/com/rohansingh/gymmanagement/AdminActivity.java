package com.rohansingh.gymmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    TextView tvGallery, tvAdminMembers, tvAdminTimings, tvAdminPackages, tvAdminCore,tvAnnouncements;
    Button btAdminLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setupUI();
        firebaseAuth = FirebaseAuth.getInstance();
        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, GalleryActivity.class));
            }
        });
        tvAdminMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AdminMembersActivity.class));
            }
        });
        tvAnnouncements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AdminAnnouncementsActivity.class));
            }
        });
        btAdminLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Toast.makeText(AdminActivity.this, "Logout Successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void setupUI() {
        tvGallery = findViewById(R.id.tvGallery);
        tvAdminMembers = findViewById(R.id.tvAdminMembers);
        tvAdminTimings = findViewById(R.id.tvAdminTimings);
        tvAdminPackages = findViewById(R.id.tvAdminPackages);
        tvAdminCore = findViewById(R.id.tvAdminCore);
        tvAnnouncements = findViewById(R.id.tvAnnouncements);
        btAdminLogout = findViewById(R.id.btAdminLogout);
    }
}