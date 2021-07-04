package com.rohansingh.gymmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminProfileActivity extends AppCompatActivity {

    TextView tvName,tvMobile,tvDateOfBirth,tvGender,tvHeight,tvWeight,tvCity,tvMedicalCondition,tvTimings,tvPackages,tvCore,tvPersonalTrainer,tvZumba,tvLocker,tvSteamBath;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    final String yes="Yes";
    final String no="No";
    CircleImageView ivDashboardProfilePic;
    StorageReference storageReference;
    final long ONE_MB = 1024*1024;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        setupUI();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        Intent intent = getIntent();
        String uid = intent.getStringExtra("UID");
        getData(uid);
    }

    private void setupUI() {
        tvName = findViewById(R.id.tvName);
        tvMobile = findViewById(R.id.tvMobile);
        tvDateOfBirth = findViewById(R.id.tvDateOfBirth);
        tvGender = findViewById(R.id.tvGender);
        tvHeight = findViewById(R.id.tvHeight);
        tvWeight = findViewById(R.id.tvWeight);
        tvCity = findViewById(R.id.tvCity);
        tvMedicalCondition = findViewById(R.id.tvMedicalCondition);
        tvTimings = findViewById(R.id.tvTimings);
        tvPackages = findViewById(R.id.tvPackages);
        tvCore = findViewById(R.id.tvCore);
        tvPersonalTrainer = findViewById(R.id.tvPersonalTrainer);
        tvZumba = findViewById(R.id.tvZumba);
        tvLocker = findViewById(R.id.tvLocker);
        tvSteamBath = findViewById(R.id.tvSteamBath);
    }

    private void getData(final String uid) {
        DatabaseReference databaseReference = firebaseDatabase.getReference(uid);
        databaseReference.child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                tvName.setText(userProfile.getName());
                tvMobile.setText(userProfile.getMobile());
                tvDateOfBirth.setText(userProfile.getDate());
                tvGender.setText(userProfile.getGender());
                tvHeight.setText(userProfile.getHeight() + " cm");
                tvWeight.setText(userProfile.getWeight() + " Kg");
                tvCity.setText(userProfile.getCity());
                tvMedicalCondition.setText(userProfile.getMedicalConditions());
                tvTimings.setText(userProfile.getTimings());
                tvPackages.setText(userProfile.getPackages());
                tvCore.setText(userProfile.getCore());
                if(userProfile.getPersonalTrainer().equals(yes)){
                    tvPersonalTrainer.setText(R.string.personal_trainer);
                } else if(userProfile.getPersonalTrainer().equals(no)) {
                    tvPersonalTrainer.setVisibility(View.GONE);
                }
                if(userProfile.getZumba().equals(yes)){
                    tvZumba.setText(R.string.zumba);
                } else if(userProfile.getZumba().equals(no)) {
                    tvZumba.setVisibility(View.GONE);
                }
                if(userProfile.getLocker().equals(yes)){
                    tvLocker.setText(R.string.locker);
                } else if(userProfile.getLocker().equals(no)){
                    tvLocker.setVisibility(View.GONE);
                }
                if(userProfile.getSteamBath().equals(yes)){
                    tvSteamBath.setText(R.string.steam_bath);
                } else if(userProfile.getSteamBath().equals(no)) {
                    tvSteamBath.setVisibility(View.GONE);
                }
                ivDashboardProfilePic = findViewById(R.id.ivDashboardProfilePic);
                StorageReference imageRef = storageReference.child("Images").child("ProfilePic").child(uid);
                imageRef.getBytes(ONE_MB).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0 , bytes.length);
                        ivDashboardProfilePic.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminProfileActivity.this, "Couldn't Load The Profile Pic!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}