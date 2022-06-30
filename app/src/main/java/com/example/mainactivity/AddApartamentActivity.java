package com.example.mainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mainactivity.Models.Apartament;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class AddApartamentActivity extends AppCompatActivity {

    private MaterialEditText apName, apDesc, apAddress, apType, apImage;
    private Button btnAdd;
    private ProgressBar pgBarLoading;
    private FirebaseDatabase db;
    private DatabaseReference databaseReference;
    private String apId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.add_apartament_window);

        apName = findViewById(R.id.apartamentName);
        apDesc = findViewById(R.id.apartamentDescription);
        apAddress = findViewById(R.id.apartamentAddress);
        apType = findViewById(R.id.apartamentType);
        apImage = findViewById(R.id.apartamentImage);

        btnAdd = findViewById(R.id.buttonAddApartament);
        pgBarLoading = findViewById(R.id.prgBarLoading);

        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Apartaments");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pgBarLoading.setVisibility(View.VISIBLE);
                String aName = apName.getText().toString();
                String aDesc = apDesc.getText().toString();
                String aAddress = apAddress.getText().toString();
                String aType = apType.getText().toString();
                String aImage = apImage.getText().toString();
                apId = aName;

                Apartament apartament = new Apartament(aName, aAddress, aType, aImage, aDesc, apId);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(apId).setValue(apartament);
                        pgBarLoading.setVisibility(View.GONE);
                        Toast.makeText(AddApartamentActivity.this, "Апартаменты добавлены", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddApartamentActivity.this, ApartamentListActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        pgBarLoading.setVisibility(View.GONE);
                        Toast.makeText(AddApartamentActivity.this, "Ошибка при добавлении" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}