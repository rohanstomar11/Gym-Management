package com.rohansingh.gymmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class FeesActivity extends AppCompatActivity implements PaymentResultListener {


    TextView tvFeesCore, tvFeesPersonalTrainer, tvFeesZumba, tvFeesLocker, tvFeesSteamBath,tvFeesTotal;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    Button btPayFees;
    final String yes="Yes";
    final String no="No";
    int feesCore,feesPersonalTrainer,feesZumba,feesLocker,feesSteamBath;
    double total,x=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        setupUI();
        setData();
        btPayFees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checkout checkout = new Checkout();
                checkout.setKeyID("rzp_test_rxJmIEhYP2QfGy");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("name", "Gym Management");
                    jsonObject.put("description", "Test payment");
                    jsonObject.put("currency", "INR");
                    jsonObject.put("amount", total*x*100);
                    checkout.open(FeesActivity.this, jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setFees(int feesCore, int feesPersonalTrainer, int feesZumba, int feesLocker, int feesSteamBath, double total, double x) {
        tvFeesCore.setText("Core : Rs." + feesCore*x);
        tvFeesPersonalTrainer.setText("Personal Trainer : Rs." + feesPersonalTrainer*x);
        tvFeesZumba.setText("Zumba : Rs." + feesZumba*x);
        tvFeesLocker.setText("Locker : Rs." + feesLocker*x);
        tvFeesSteamBath.setText("Steam Bath : Rs." + feesSteamBath*x);
        tvFeesTotal.setText("Total : Rs." + total*x);
    }

    private void setData() {
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                switch (userProfile.getPackages()){
                    case "Monthly":
                        x=1;
                        break;
                    case "Quarterly":
                        x=2.7;
                        break;
                    case "Half-Yearly":
                        x=5.1;
                        break;
                    case "Yearly":
                        x=9.6;
                        break;
                    default:
                        break;
                }
                if(userProfile.getCore().equals("Weight Training")){
                    feesCore = 800;
                } else if(userProfile.getCore().equals("Cardio Training")){
                    feesCore = 1000;
                }
                if(userProfile.getPersonalTrainer().equals(yes)){
                    feesPersonalTrainer=1000;
                } else if(userProfile.getPersonalTrainer().equals(no)) {
                    feesPersonalTrainer=0;
                }
                if(userProfile.getZumba().equals(yes)){
                    feesZumba = 500;
                } else if(userProfile.getZumba().equals(no)) {
                    feesZumba = 0;
                }
                if(userProfile.getLocker().equals(yes)){
                    feesLocker = 250;
                } else if(userProfile.getLocker().equals(no)){
                    feesLocker = 0;
                }
                if(userProfile.getSteamBath().equals(yes)){
                    feesSteamBath = 500;
                } else if(userProfile.getSteamBath().equals(no)) {
                    feesSteamBath = 0;
                }
                total = (feesCore+feesPersonalTrainer+feesZumba+feesLocker+feesSteamBath);
                setFees(feesCore,feesPersonalTrainer,feesZumba,feesLocker,feesSteamBath,total,x);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setupUI(){
        tvFeesCore = findViewById(R.id.tvFeesCore);
        tvFeesPersonalTrainer = findViewById(R.id.tvFeesPersonalTrainer);
        tvFeesZumba = findViewById(R.id.tvFeesZumba);
        tvFeesLocker = findViewById(R.id.tvFeesLocker);
        tvFeesSteamBath = findViewById(R.id.tvFeesSteamBath);
        tvFeesTotal = findViewById(R.id.tvFeesTotal);
        btPayFees = findViewById(R.id.btPayFees);
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successful : "+s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Error : "+s, Toast.LENGTH_SHORT).show();
    }
}