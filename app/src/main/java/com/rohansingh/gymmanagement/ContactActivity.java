package com.rohansingh.gymmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity {

    final String ownerNumber = "+917559454220";
    final String ownerEmail = "rohanstomar11@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }

    public void onCallUs(View view) {
        Uri uri = Uri.parse("tel:" + ownerNumber);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        try {
            startActivity(intent);
        } catch (SecurityException securityException){
            Toast.makeText(this, securityException.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onWhatsAppUs(View view) {
        String url = "https://api.whatsapp.com/send?phone="+ownerNumber;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void onMailUs(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{ownerEmail});
        intent.setType("message/rf2c82");
        try {
            startActivity(Intent.createChooser(intent,"Choose an Email Client : "));
        } catch (SecurityException securityException){
            Toast.makeText(this, securityException.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}