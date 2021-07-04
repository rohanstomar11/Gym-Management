package com.rohansingh.gymmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AnnouncementsActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    DynamicAnnouncements dynamicAnnouncements;
    Context context;
    LinearLayout llAnnouncements;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);
        firebaseFirestore = FirebaseFirestore.getInstance();
        setupUI();
        CollectionReference collectionReference = firebaseFirestore.collection("Announcements");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    count+=1;
                    String zzzzz = documentSnapshot.get(String.valueOf(count)).toString();
                    dynamicAnnouncements = new DynamicAnnouncements(context);
                    TextView textView = dynamicAnnouncements.makeTextView(getApplicationContext(), zzzzz);
                    llAnnouncements.addView(textView);
                }
            }
        });
    }

    private void setupUI() {
        llAnnouncements = findViewById(R.id.llAnnouncements);
    }
}