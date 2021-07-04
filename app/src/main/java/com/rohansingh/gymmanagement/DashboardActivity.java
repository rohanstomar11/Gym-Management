package com.rohansingh.gymmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity {


    FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        fbAuth = FirebaseAuth.getInstance();
        setupUI();
    }

    private void setupUI() {
    }

    public void dashboardLogOut(View view) {
        fbAuth.signOut();
        Toast.makeText(DashboardActivity.this, "Logout Successful!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
    }

    public void viewProfile(View view) {
        Toast.makeText(this, "View Profile", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,ViewProfileActivity.class));
    }

    public void editProfile(View view) {
        Toast.makeText(this, "Edit Profile", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,EditProfileActivity.class));
    }

    public void viewFees(View view) {
        Toast.makeText(this, "Fees", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,FeesActivity.class));
    }

    public void changeSettings(View view) {
        Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,SettingsActivity.class));
    }

    public void getContact(View view) {
        Toast.makeText(this, "Contact", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,ContactActivity.class));
    }

    public void viewGallery(View view){
        startActivity(new Intent(this, GalleryActivity.class));
    }

    public void viewAnnouncements(View view) {
        startActivity(new Intent(DashboardActivity.this, AnnouncementsActivity.class));
    }
}