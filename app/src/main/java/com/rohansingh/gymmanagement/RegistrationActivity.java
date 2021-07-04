package com.rohansingh.gymmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class    RegistrationActivity extends AppCompatActivity {

    EditText etRegEmail,etRegPassword,etRegPassword2;
    TextView tvLogin;
    Button btRegister;
    FirebaseAuth fbAuth;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        fbAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        setupUI();
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etRegEmail.getText().toString();
                String pass = etRegPassword.getText().toString();
                String pass2 = etRegPassword2.getText().toString();
                if(validate(email,pass,pass2)){
                    fbAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser firebaseUser = fbAuth.getCurrentUser();
                                DocumentReference documentReference = firebaseFirestore.collection("Users").document(firebaseUser.getUid());
                                Map<String, Object> userInfo = new HashMap<>();
                                userInfo.put("UserEmail", email);
                                userInfo.put("IsUser", "1");
                                documentReference.set(userInfo);
                                sendEmailVerification();
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
            }
        });
    }
    private void setupUI(){
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPassword);
        etRegPassword2 = findViewById(R.id.etRegPassword2);
        btRegister = findViewById(R.id.btRegister);
        tvLogin = findViewById(R.id.tvLogin);
    }
    private boolean validate(String email, String pass,String pass2){
        if(email.isEmpty() || pass.isEmpty() || pass2.isEmpty()){
            Toast.makeText(this, "Email or Password cannot be Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }
    private void sendEmailVerification(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegistrationActivity.this, "Registration successful, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        fbAuth.signOut();
                        finish();
                        startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Verification mail hasn't been sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}