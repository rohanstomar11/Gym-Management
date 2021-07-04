package com.rohansingh.gymmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AdminAnnouncementsActivity extends AppCompatActivity {

    EditText etAdminAnnouncements;
    Button btAdminAnnouncements;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_announcements);
        firebaseFirestore = FirebaseFirestore.getInstance();
        setupUI();
        btAdminAnnouncements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String announcement = etAdminAnnouncements.getText().toString();
                CollectionReference collectionReference = firebaseFirestore.collection("Announcements");
                collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        int count = queryDocumentSnapshots.size();
                        count +=1;
                        DocumentReference documentReference = firebaseFirestore.collection("Announcements").document(String.valueOf(count));
                        Map<String,String> userInfo = new HashMap<>();
                        userInfo.put(String.valueOf(count),announcement);
                        documentReference.set(userInfo);
                        Toast.makeText(AdminAnnouncementsActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }

    private void setupUI() {
        etAdminAnnouncements = findViewById(R.id.etAdminAnnouncements);
        btAdminAnnouncements = findViewById(R.id.btAdminAnnouncements);
    }
}