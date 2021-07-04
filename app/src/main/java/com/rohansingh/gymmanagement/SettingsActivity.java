package com.rohansingh.gymmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    TextView tvChangePassword, tvChangeEmail, tvDeleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupUI();

        tvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, R.string.change_password, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SettingsActivity.this, ChangePasswordActivity.class));
            }
        });

        tvChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, R.string.change_email, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SettingsActivity.this, ChangeEmailActivity.class));
            }
        });

        tvDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, R.string.delete_account, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SettingsActivity.this, DeleteActivity.class));
            }
        });

    }

    private void setupUI(){
        tvChangePassword = findViewById(R.id.tvChangePassword);
        tvChangeEmail = findViewById(R.id.tvChangeEmail);
        tvDeleteAccount = findViewById(R.id.tvDeleteAccount);
    }

}