package com.rohansingh.gymmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SplashScreenActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        welcomeScreen.start();

    }
    private class WelcomeScreen extends Thread{
        public void run(){
            try {
                long SLEEP_TIMER = 1;
                sleep(1000* SLEEP_TIMER);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(firebaseUser!=null){
                String uId = firebaseUser.getUid();
                DocumentReference documentReference = firebaseFirestore.collection("Users").document(uId);
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String valid = documentSnapshot.get("IsUser").toString();
                        if(valid.equals("0")){
                            startActivity(new Intent(SplashScreenActivity.this,AdminActivity.class));
                        } else {
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    UserProfile userProfile = snapshot.getValue(UserProfile.class);
                                    try {
                                        if((userProfile.getClass())!=null){
                                            SplashScreenActivity.this.finish();
                                            startActivity(new Intent(SplashScreenActivity.this,DashboardActivity.class));
                                        }
                                    } catch (NullPointerException e){
                                        SplashScreenActivity.this.finish();
                                        startActivity(new Intent(SplashScreenActivity.this,AddProfileActivity.class));
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });



            } else {
                Intent intent = new Intent(SplashScreenActivity.this,LoginActivity.class);
                startActivity(intent);
                SplashScreenActivity.this.finish();
            }
        }
    }
}

