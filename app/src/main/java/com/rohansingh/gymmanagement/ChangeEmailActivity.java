package com.rohansingh.gymmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeEmailActivity extends AppCompatActivity {

    EditText etUpdateEmail, etUpdateEmail2, etEmailPassword;
    Button btUpdateEmail;
    FirebaseAuth firebaseAuth;
    String emailCurrent;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        setupUI();
        btUpdateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = etEmailPassword.getText().toString();
                String email = etUpdateEmail.getText().toString();
                String email2 = etUpdateEmail2.getText().toString();
                emailCurrent = firebaseUser.getEmail();
                validate(pass,email,email2);
            }
        });
    }

    private void validate(String pass, String email, String email2) {
        if(pass.isEmpty() || email.isEmpty() || email2.isEmpty()){
            Toast.makeText(this, "Fields Cannot be Blank!", Toast.LENGTH_SHORT).show();
        } else if(!(email.equals(email2))){
            Toast.makeText(this, "Emails Do Not Match!", Toast.LENGTH_SHORT).show();
        } else {
            changeEmail(pass,email);
        }
    }

    private void changeEmail(String pass, final String email) {
        AuthCredential authCredential = EmailAuthProvider.getCredential(emailCurrent,pass);
        firebaseUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    firebaseUser.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ChangeEmailActivity.this, "Email Changed, Please Re-Verify!", Toast.LENGTH_SHORT).show();
                                firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            finish();
                                            firebaseAuth.signOut();
                                            startActivity(new Intent(ChangeEmailActivity.this, LoginActivity.class));
                                        } else {
                                            Toast.makeText(ChangeEmailActivity.this, "Couldn't send the verification mail!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(ChangeEmailActivity.this, "Email Couldn't be Changed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(ChangeEmailActivity.this, "Couldn't Authenticate", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setupUI() {
        etEmailPassword = findViewById(R.id.etEmailPassword);
        etUpdateEmail = findViewById(R.id.etUpdateEmail);
        etUpdateEmail2 = findViewById(R.id.etUpdateEmail2);
        btUpdateEmail = findViewById(R.id.btUpdateEmail);
    }
}