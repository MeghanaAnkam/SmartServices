package com.example.smartservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Account extends AppCompatActivity {
    ImageView imageView;
    TextView textName, textEmail, texts;
    GoogleSignInClient mGoogleSignInClient;
    String imageuri, id;
    AccountDb mydb;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    List<String> list;
    List<String> list1;
    int count1 = 0;
    long mLastClickTime = 0;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_account);
        list = new ArrayList();
        mydb = new AccountDb(this);
        list1 = new ArrayList();
        b = findViewById(R.id.next);
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        imageView = findViewById(R.id.imageView);
        textName = findViewById(R.id.textViewName);
        textEmail = findViewById(R.id.textViewEmail);
        texts = findViewById(R.id.texts);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            id = personId;
            Uri personPhoto = acct.getPhotoUrl();
            imageuri = personPhoto.toString();
            Glide.with(this).load(personPhoto).apply(RequestOptions.circleCropTransform()).into(imageView);
            textName.setText(personName);
            textEmail.setText(personEmail);
            Cursor res = mydb.getRole(textEmail.getText().toString());
            if (res.getCount() != 0) {
                if (res.moveToNext()) {
                    if (res.getString(1).equalsIgnoreCase("canteen_admin")) {
                        Intent intent = new Intent(Account.this, Main2Activity.class);
                        intent.putExtra("email",textEmail.getText().toString());
                        intent.putExtra("name",textName.getText().toString());
                        startActivity(intent);
                        finish();
                    } else if (res.getString(1).equalsIgnoreCase("clean_admin")) {
                        Intent intent = new Intent(Account.this, Main3Activity.class);
                        intent.putExtra("email", textEmail.getText().toString());
                        intent.putExtra("name", textName.getText().toString());
                        startActivity(intent);
                        finish();
                    } else if (res.getString(1).equalsIgnoreCase("events_admin")) {
                        Intent intent = new Intent(Account.this, Events_Admin.class);
                        intent.putExtra("email", textEmail.getText().toString());
                        intent.putExtra("name", textName.getText().toString());
                        startActivity(intent);
                        finish();
                    } else if (res.getString(1).equalsIgnoreCase("admin")) {
                        Intent intent = new Intent(Account.this, AdminActivity.class);
                        intent.putExtra("email", textEmail.getText().toString());
                        intent.putExtra("name", textName.getText().toString());
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(Account.this, DashBoard.class);
                        intent.putExtra("email", textEmail.getText().toString());
                        intent.putExtra("imageUri", imageuri);
                        intent.putExtra("name", textName.getText().toString());
                        intent.putExtra("id", id);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                String str = textEmail.getText().toString();
                db.collection("users").document("roles").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            if (documentSnapshot.get("canteen_admin").toString().equalsIgnoreCase(str)) {
                                mydb.insertData(str,"canteen_admin");
                                Intent intent = new Intent(Account.this, Main2Activity.class);
                                intent.putExtra("email",textEmail.getText().toString());
                                intent.putExtra("name",textName.getText().toString());
                                startActivity(intent);
                                finish();
                            } else if (documentSnapshot.get("clean_admin").toString().equalsIgnoreCase(str)) {
                                mydb.insertData(str, "clean_admin");
                                Intent intent = new Intent(Account.this, Main3Activity.class);
                                intent.putExtra("email", textEmail.getText().toString());
                                intent.putExtra("name", textName.getText().toString());
                                startActivity(intent);
                                finish();
                            } else if (documentSnapshot.get("events_admin").toString().equalsIgnoreCase(str)) {
                                mydb.insertData(str, "events_admin");
                                Intent intent = new Intent(Account.this, Events_Admin.class);
                                intent.putExtra("email", textEmail.getText().toString());
                                intent.putExtra("name", textName.getText().toString());
                                startActivity(intent);
                                finish();
                            } else if (documentSnapshot.get("Main_admin").toString().equalsIgnoreCase(str)) {
                                mydb.insertData(str, "admin");
                                Intent intent = new Intent(Account.this, AdminActivity.class);
                                intent.putExtra("email", textEmail.getText().toString());
                                intent.putExtra("name", textName.getText().toString());
                                startActivity(intent);
                                finish();
                            } else {
                                mydb.insertData(str, "student");
                                Intent intent = new Intent(Account.this, DashBoard.class);
                                intent.putExtra("email", textEmail.getText().toString());
                                intent.putExtra("imageUri", imageuri);
                                intent.putExtra("name", textName.getText().toString());
                                intent.putExtra("id", id);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                });


            }
        });
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {

                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
