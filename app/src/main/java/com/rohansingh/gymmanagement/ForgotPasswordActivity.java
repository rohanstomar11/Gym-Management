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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText etForgotEmail;
    Button btForgotPassword;
    FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setupUI();
        fbAuth = FirebaseAuth.getInstance();
        btForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etForgotEmail.getText().toString();
                if(email.isEmpty()){
                    Toast.makeText(ForgotPasswordActivity.this,"Email cannot be blank!",Toast.LENGTH_SHORT).show();
                }else{
                    fbAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ForgotPasswordActivity.this, "Please check your mail for password reset link!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
                                finish();
                            }else {
                                Toast.makeText(ForgotPasswordActivity.this, "Try Again!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    private void setupUI(){
        etForgotEmail = findViewById(R.id.etForgotEmail);
        btForgotPassword = findViewById(R.id.btForgotPassword);
    }
}