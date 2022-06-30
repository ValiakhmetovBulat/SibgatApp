package com.example.mainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mainactivity.Models.Apartament;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

public class EditApartamentActivity extends AppCompatActivity {

    private MaterialEditText apName, apDesc, apAddress, apType, apImage;
    private Button btnEdit, btnDelete;
    private ProgressBar pgBarLoading;
    private FirebaseDatabase db;
    private DatabaseReference databaseReference;
    private String apId;
    private Apartament apartament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_edit_apartament_window);

        apName = findViewById(R.id.apartamentName);
        apDesc = findViewById(R.id.apartamentDescription);
        apAddress = findViewById(R.id.apartamentAddress);
        apType = findViewById(R.id.apartamentType);
        apImage = findViewById(R.id.apartamentImage);

        btnEdit = findViewById(R.id.buttonEditApartament);
        btnDelete = findViewById(R.id.buttonDeleteApartament);

        pgBarLoading = findViewById(R.id.prgBarLoading);

        db = FirebaseDatabase.getInstance();

        apartament = getIntent().getParcelableExtra("apartament");

        if (apartament != null) {
            apName.setText(apartament.getApartamentName());
            apDesc.setText(apartament.getApartamentDescription());
            apAddress.setText(apartament.getApartamentAddress());
            apType.setText(apartament.getApartamentType());
            apImage.setText(apartament.getApartamentImage());
            apId = apartament.getApartamentId();
        }

        databaseReference = db.getReference("Apartaments").child(apId);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                pgBarLoading.setVisibility(View.VISIBLE);
                String aName = apName.getText().toString();
                String aDesc = apDesc.getText().toString();
                String aAddress = apAddress.getText().toString();
                String aType = apType.getText().toString();
                String aImage = apImage.getText().toString();

                Map<String, Object> map = new HashMap<>();
                map.put("apartamentName", aName);
                map.put("apartamentDescription", aDesc);
                map.put("apartamentAddress", aAddress);
                map.put("apartamentType", aType);
                map.put("apartamentImage", aImage);
                map.put("apartamentId", apId);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(map);
                        pgBarLoading.setVisibility(View.GONE);
                        Toast.makeText(EditApartamentActivity.this, "Информация изменена", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditApartamentActivity.this, ApartamentListActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditApartamentActivity.this, "Ошибка при изменении" + error.toString(), Toast.LENGTH_SHORT).show();
                        pgBarLoading.setVisibility(View.GONE);
                    }
                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteApartament();
            }
        });
    }

    private void deleteApartament() {
        databaseReference.removeValue();
        Toast.makeText(EditApartamentActivity.this,"Апартамент был удален", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditApartamentActivity.this, ApartamentListActivity.class));
    }
}