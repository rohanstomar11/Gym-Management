package com.rohansingh.gymmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DeleteActivity extends AppCompatActivity {

    EditText etDeletePassword;
    Button btDeleteAccount;
    String email;
    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;
    FirebaseUser firebaseUser;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        setupUI();
        btDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = firebaseUser.getEmail();
                String password = etDeletePassword.getText().toString();
                if(password.isEmpty()){
                    Toast.makeText(DeleteActivity.this, "Please Enter Password!", Toast.LENGTH_SHORT).show();
                } else {
                    deleteAccount(email,password);
                }
            }
        });
    }

    private void deleteAccount(String email,String pass) {
        AuthCredential authCredential = EmailAuthProvider.getCredential(email, pass);
        firebaseUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(DeleteActivity.this);
                    builder.setMessage("Are you sure you want to delete your account?");
                    builder.setCancelable(true);
                    builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            deleteProfilePic();
                            firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(DeleteActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(DeleteActivity.this,LoginActivity.class));
                                    } else{
                                        Toast.makeText(DeleteActivity.this, "Couldn't Delete Account,Try Again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                    builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Toast.makeText(DeleteActivity.this, "Thanks!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    Toast.makeText(DeleteActivity.this, "Couldn't Validate, Try Again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteProfilePic() {
        StorageReference imageReference = storageReference.child("Images").child("ProfilePic").child(firebaseAuth.getUid());
        imageReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("001","ProfilePicDeleted");
            }
        });
    }

    private void setupUI() {
        etDeletePassword = findViewById(R.id.etDeletePassword);
        btDeleteAccount = findViewById(R.id.btDeleteAccount);
    }
}