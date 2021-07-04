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

public class ChangePasswordActivity extends AppCompatActivity {

    EditText etCurrentPassword, etNewPassword,etNewPassword2;
    Button btUpdatePassword;
    FirebaseAuth firebaseAuth;
    String email;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        email = firebaseUser.getEmail();
        setupUI();
        btUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = etCurrentPassword.getText().toString();
                String newPassword = etNewPassword.getText().toString();
                String newPassword2 = etNewPassword2.getText().toString();
                validate(currentPassword,newPassword,newPassword2);
            }
        });
    }

    private void setupUI() {
        etCurrentPassword = findViewById(R.id.etCurrentPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etNewPassword2 = findViewById(R.id.etNewPassword2);
        btUpdatePassword = findViewById(R.id.btUpdatePassword);
    }

    private void validate(String cP, String nP, String nP2){
        if(cP.isEmpty() || nP.isEmpty() || nP2.isEmpty()){
            Toast.makeText(this, "Fields Cannot Be Blank!", Toast.LENGTH_SHORT).show();
        } else if(!(nP.equals(nP2))) {
            Toast.makeText(this, "New Passwords Do Not Match!", Toast.LENGTH_SHORT).show();
        } else {
            updatePassword(cP,nP);
        }
    }

    private void updatePassword(String cP, final String nP) {
        AuthCredential authCredential = EmailAuthProvider.getCredential(email,cP);
        firebaseUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    firebaseUser.updatePassword(nP).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ChangePasswordActivity.this, "Password Changed!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ChangePasswordActivity.this, DashboardActivity.class));
                            } else {
                                Toast.makeText(ChangePasswordActivity.this, "Password Couldn't Be Changed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Couldn't Authenticate!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}