package com.example.mainactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainactivity.Models.Apartament;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ApartamentListActivityUser extends AppCompatActivity implements ApartamentRVAdapter.IApartamentClick {

    private RecyclerView apartamentRV;
    private ProgressBar pgBarLoading;
    private FirebaseDatabase db;
    private DatabaseReference databaseReference;
    private ArrayList<Apartament> apartamentArrayList;
    private RelativeLayout bottomSheetRL;
    private ApartamentRVAdapter apartamentRVAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Список апартаментов");
        getSupportActionBar().show();

        setContentView(R.layout.activity_apartament_list_user);

        apartamentRV = findViewById(R.id.IdRV);
        pgBarLoading = findViewById(R.id.prggBarLoading);
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Apartaments");

        apartamentArrayList = new ArrayList<>();

        bottomSheetRL = findViewById(R.id.idRLBottom);

        mAuth = FirebaseAuth.getInstance();

        apartamentRVAdapter = new ApartamentRVAdapter(apartamentArrayList, this, this);

        apartamentRV.setLayoutManager(new LinearLayoutManager(this));
        apartamentRV.setAdapter(apartamentRVAdapter);

        getAllApartaments();
    }

    private void getAllApartaments() {

        apartamentArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                apartamentArrayList.add(snapshot.getValue(Apartament.class));
                apartamentRVAdapter.notifyDataSetChanged();
                pgBarLoading.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                apartamentRVAdapter.notifyDataSetChanged();
                pgBarLoading.setVisibility(View.GONE);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                apartamentRVAdapter.notifyDataSetChanged();
                pgBarLoading.setVisibility(View.GONE);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                apartamentRVAdapter.notifyDataSetChanged();
                pgBarLoading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onApartamentClick(int postition) {

        displayBottomSheet(apartamentArrayList.get(postition));
    }

    private void displayBottomSheet(Apartament apartament) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog_user, bottomSheetRL);

        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView apartamentNameTV = layout.findViewById(R.id.idTVApartamentName);
        ImageView apartamentImageTV = layout.findViewById(R.id.idTVApartamentImg);
        TextView apartamentTypeTV = layout.findViewById(R.id.idTVApartamentType);
        TextView apartamentAddressTV = layout.findViewById(R.id.idTVApartamentAddress);
        TextView apartamentDescTV = layout.findViewById(R.id.idTVApartamentDesc);
        Button showDetailsBtn = layout.findViewById(R.id.btnViewDetails);

        apartamentNameTV.setText(apartament.getApartamentName());
        Picasso.get().load(apartament.getApartamentImage()).into(apartamentImageTV);
        apartamentTypeTV.setText(apartament.getApartamentType());
        apartamentAddressTV.setText(apartament.getApartamentAddress());
        apartamentDescTV.setText(apartament.getApartamentDescription());

        showDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(apartament.getApartamentImage()));

                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item .getItemId();

        switch (id) {
            case R.id.idLogOut:
                Toast.makeText(this, "Пользователь вышел из системы", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent i = new Intent(ApartamentListActivityUser.this, MainActivity.class);
                startActivity(i);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}