package com.rohansingh.gymmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AdminMembersActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    FirebaseDatabase firebaseDatabase;
    DynamicViews dynamicViews;
    Context context;
    LinearLayout llMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_members);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        setupUI();
        CollectionReference collectionReference = firebaseFirestore.collection("Users");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    final String uId = documentSnapshot.getId();
                    DatabaseReference databaseReference = firebaseDatabase.getReference(uId);
                    databaseReference.child("Profile").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            UserProfile userProfile = snapshot.getValue(UserProfile.class);
                            String name = userProfile.getName();
                            dynamicViews = new DynamicViews(context);
                            TextView textView = dynamicViews.makeTextView(getApplicationContext(), name);
                            llMembers.addView(textView);
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(AdminMembersActivity.this, AdminProfileActivity.class);
                                    intent.putExtra("UID", uId);
                                    startActivity(intent);
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(AdminMembersActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void setupUI() {
        llMembers = findViewById(R.id.llMembers);
    }
}