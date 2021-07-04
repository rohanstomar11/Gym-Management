package com.rohansingh.gymmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText etUpdateName,etUpdateMobile,etUpdateDateOfBirth,etUpdateHeight,etUpdateWeight,etUpdateCity,etUpdateMedicalCondition;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    String updateName,updateMobile,updateDateOfBirth,updateGender,updateHeight,updateWeight,updateCity,updateMedicalCondition,updateTimings,updatePackages,updateCore,updatePersonalTrainer="No",updateZumba="No",updateLocker="No",updateSteamBath="No";
    Button btUpdateProfile;
    Spinner spUpdateGender,spUpdateTimings,spUpdatePackages;
    CheckBox cbUpdatePersonalTrainer, cbUpdateZumba, cbUpdateLocker, cbUpdateSteamBath;
    RadioButton rbUpdateWeightTraining, rbUpdateCardioTraining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_activity);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        setupUI();
        if(spUpdateGender!=null){
            spUpdateGender.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.gender,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(spUpdateGender!=null){
            spUpdateGender.setAdapter(adapter);
        }

        if(spUpdateTimings!=null){
            spUpdateTimings.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adTimings = ArrayAdapter.createFromResource(this,R.array.timings,android.R.layout.simple_spinner_item);
        adTimings.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(spUpdateTimings!=null){
            spUpdateTimings.setAdapter(adTimings);
        }

        if(spUpdatePackages!=null){
            spUpdatePackages.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adPackages = ArrayAdapter.createFromResource(this,R.array.packages,android.R.layout.simple_spinner_item);
        adPackages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(spUpdatePackages!=null){
            spUpdatePackages.setAdapter(adPackages);
        }

        btUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateButtonClick();
            }
        });
    }

    private void setupUI(){
        etUpdateName = findViewById(R.id.etUpdateName);
        etUpdateMobile = findViewById(R.id.etUpdateMobile);
        etUpdateDateOfBirth = findViewById(R.id.etUpdateDateOfBirth);
        etUpdateHeight = findViewById(R.id.etUpdateHeight);
        etUpdateWeight = findViewById(R.id.etUpdateWeight);
        etUpdateCity = findViewById(R.id.etUpdateCity);
        etUpdateMedicalCondition = findViewById(R.id.etUpdateMedicalCondition);
        btUpdateProfile = findViewById(R.id.btUpdateProfile);
        spUpdateGender = findViewById(R.id.spUpdateGender);
        spUpdateTimings = findViewById(R.id.spUpdateTimings);
        spUpdatePackages = findViewById(R.id.spUpdatePackage);
        setProfile();
    }

    private void setProfile(){
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                updateName = userProfile.getName();
                updateMobile = userProfile.getMobile();
                updateDateOfBirth = userProfile.getDate();
                updateHeight = userProfile.getHeight();
                updateWeight = userProfile.getWeight();
                updateCity = userProfile.getCity();
                updateMedicalCondition = userProfile.getMedicalConditions();
                etUpdateName.setText(updateName);
                etUpdateMobile.setText(updateMobile);
                etUpdateDateOfBirth.setText(updateDateOfBirth);
                etUpdateHeight.setText(updateHeight);
                etUpdateWeight.setText(updateWeight);
                etUpdateCity.setText(updateCity);
                etUpdateMedicalCondition.setText(updateMedicalCondition);
                rbUpdateWeightTraining = findViewById(R.id.rbUpdateWeightTraining);
                rbUpdateCardioTraining = findViewById(R.id.rbUpdateCardioTraining);
                cbUpdatePersonalTrainer = findViewById(R.id.cbUpdatePersonalTrainer);
                cbUpdateZumba = findViewById(R.id.cbUpdateZumba);
                cbUpdateLocker = findViewById(R.id.cbUpdateLocker);
                cbUpdateSteamBath = findViewById(R.id.cbUpdateSteamBath);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void onUpdateButtonClick(){
        updateName = etUpdateName.getText().toString();
        updateMobile = etUpdateMobile.getText().toString();
        updateDateOfBirth = etUpdateDateOfBirth.getText().toString();
        updateHeight = etUpdateHeight.getText().toString();
        updateWeight = etUpdateWeight.getText().toString();
        updateCity = etUpdateCity.getText().toString();
        updateMedicalCondition = etUpdateMedicalCondition.getText().toString();
        DatabaseReference myRef =firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(updateName,updateMobile,updateDateOfBirth,updateGender,updateHeight,updateWeight,updateCity,updateMedicalCondition,updateTimings,updatePackages,updateCore,updatePersonalTrainer,updateZumba,updateLocker,updateSteamBath);
        myRef.child("Profile").setValue(userProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(EditProfileActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spOne = (Spinner) parent;
        Spinner spTwo = (Spinner) parent;
        Spinner spThree = (Spinner) parent;
        if(spOne.getId()==R.id.spUpdateGender){
            updateGender = parent.getItemAtPosition(position).toString();
        } else if(spTwo.getId()==R.id.spUpdateTimings){
            updateTimings = parent.getItemAtPosition(position).toString();
        } else if(spThree.getId()==R.id.spUpdatePackage) {
            updatePackages = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onUpdateCheckBoxClicked(View view) {
        Boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.cbUpdatePersonalTrainer:
                if (checked) {
                    updatePersonalTrainer = "Yes";
                } else {
                    updatePersonalTrainer = "No";
                }
                break;
            case R.id.cbUpdateZumba:
                if (checked) {
                    updateZumba = "Yes";
                } else {
                    updateZumba = "No";
                }
                break;
            case R.id.cbUpdateLocker:
                if (checked) {
                    updateLocker = "Yes";
                } else {
                    updateLocker = "No";
                }
                break;
            case R.id.cbUpdateSteamBath:
                if (checked) {
                    updateSteamBath = "Yes";
                } else {
                    updateSteamBath = "No";
                }
                break;
            default:
                break;
        }
    }

    public void onUpdateRadioButtonClicked(View view) {
        boolean checked = ((RadioButton)view).isChecked();
        switch(view.getId()){
            case R.id.rbUpdateWeightTraining :
                if(checked){
                    updateCore=getString(R.string.weight_training);
                }
                break;
            case R.id.rbUpdateCardioTraining :
                if (checked){
                    updateCore=getString(R.string.cardio_training);
                }
                break;
            default:
                break;
        }
    }
}