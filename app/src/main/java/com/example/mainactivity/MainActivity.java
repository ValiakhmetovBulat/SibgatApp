package com.example.mainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.mainactivity.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn, btnRegister;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    ProgressBar prgBarLoading;

    LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnRegister = findViewById(R.id.btnRegister);

        root = findViewById(R.id.root_element);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        prgBarLoading = findViewById(R.id.prgBarLoadingMain);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                showRegisterWindow();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignInWindow();
            }
        });
    }

    private void showSignInWindow()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("????????");
        dialog.setMessage("?????????????? ???????????? ?????? ??????????");

        LayoutInflater inflater = LayoutInflater.from(this);
        View sign_in_window = inflater.inflate(R.layout.sign_in_window, null);
        dialog.setView(sign_in_window);

        MaterialEditText email, password;

        // ?????????????????? ?????????? ?????? ??????????
        email = sign_in_window.findViewById(R.id.emailField);
        password = sign_in_window.findViewById(R.id.passwordField);

        // ???????????? ???????????? ?? ???????????????????? ????????
        dialog.setNegativeButton("????????????????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("??????????", new DialogInterface.OnClickListener() {
            @Override
            // ???????????????? ??????????
            public void onClick(DialogInterface dialogInterface, int i) {
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Snackbar.make(root, "?????????????? ??????????", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                else if (password.getText().toString().length() < 6 && password.getText().toString().length() > 15) {
                    Snackbar.make(root, "?????????? ???????????? ???????????? ???????? ???? 6 ???? 15 ????????????????", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                prgBarLoading.setVisibility(View.VISIBLE);
                // ??????????????????????
                Authorize(email.getText().toString(), password.getText().toString());

            }
        });

        dialog.show();
        prgBarLoading.setVisibility(View.GONE);
    }

    public void Authorize(String email, String password) {

        auth.signInWithEmailAndPassword(email, password)
                //?????? ???????????????? ??????????
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if (email.equals("valiaxmetovb134@gmail.com"))
                        {
                            startActivity(new Intent(MainActivity.this, ApartamentListActivity.class));
                        }
                        else
                        {
                            startActivity(new Intent(MainActivity.this, ApartamentListActivityUser.class));
                        }
                        finish();
                        Snackbar.make(
                                root,
                                "???????????????? ??????????????????????",
                                Snackbar.LENGTH_SHORT)
                                .show();
                    }
                })
                // ?????? ???????????? ??????????
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(
                                root,
                                "???????????? ??????????????????????. " + e.getMessage(),
                                Snackbar.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    private void showRegisterWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("??????????????????????");
        dialog.setMessage("?????????????? ???????????? ?????? ??????????????????????");

        LayoutInflater inflater = LayoutInflater.from(this);
        View reg_window = inflater.inflate(R.layout.register_window, null);
        dialog.setView(reg_window);

        MaterialEditText email, password, name, phone;

        // ?????????????????? ?????????? ?????? ??????????
        email = reg_window.findViewById(R.id.emailField);
        password = reg_window.findViewById(R.id.passwordField);
        phone = reg_window.findViewById(R.id.phoneField);
        name = reg_window.findViewById(R.id.nameField);

        // ???????????? ???????????? ?? ???????????????????? ????????
        dialog.setNegativeButton("????????????????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("????????????????????????????????????", new DialogInterface.OnClickListener() {
            @Override
            // ???????????????? ??????????
            public void onClick(DialogInterface dialogInterface, int i) {
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Snackbar.make(root, "?????????????? ??????????", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (name.getText().toString().length() < 2) {
                    Snackbar.make(root, "?????????? ?????????? ???????????? ???????? ???? ?????????? ???????? ????????????????", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone.getText().toString())) {
                    Snackbar.make(root, "?????????????? ??????????????", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                else if (password.getText().toString().length() < 6 && password.getText().toString().length() > 15) {
                    Snackbar.make(root, "?????????? ???????????? ???????????? ???????? ???? 6 ???? 15 ????????????????", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                // ??????????????????????
                auth.createUserWithEmailAndPassword(
                        email.getText().toString(),
                        password.getText().toString()
                ).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        User user = new User();

                        user.setEmail(email.getText().toString());
                        user.setPassword(password.getText().toString());
                        user.setPhone(phone.getText().toString());
                        user.setName(phone.getText().toString());

                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Snackbar.make(
                                                root,
                                                "???????????????? ??????????????????????",
                                                Snackbar.LENGTH_SHORT)
                                        .show();
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(
                                root,
                                "???????????? ??????????????????????. " + e.getMessage(),
                                Snackbar.LENGTH_SHORT)
                                .show();
                    }
                });

            }
        });

        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            Intent i = new Intent(MainActivity.this, ApartamentListActivity.class);
            startActivity(i);
            this.finish();
        }
    }
}