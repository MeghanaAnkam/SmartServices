package com.example.smartservices;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.github.tonywills.loadingbutton.HorizontalLoadingButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class Cleanliness extends AppCompatActivity {
    TextInputEditText e1, e2;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private HorizontalLoadingButton horizontalLoadingButton;
    int b = 0;
    int PERMISSION_ALL = 1;
    private SlidrInterface slidr;
    String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        slidr= Slidr.attach(this);
        if (InitApplication.getInstance().isNightModeEnabled()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor("#121212"));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.WHITE);
            }
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getSupportActionBar().hide();

        setContentView(R.layout.activity_cleanliness);
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        String email=getIntent().getStringExtra("email");
        e1 = findViewById(R.id.rollno);
        horizontalLoadingButton = (HorizontalLoadingButton) findViewById(R.id.uploadbtn);
        e2 = findViewById(R.id.dustplace);
        horizontalLoadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horizontalLoadingButton.setLoading(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(e1.getText().toString())) {
                            e1.setError("Roll number cannot be blank");
                            horizontalLoadingButton.setLoading(false);
                            return;
                        }
                        if (e1.getText().toString().length() != 10) {
                            e1.setError("Roll number should contain 10 letters");
                            horizontalLoadingButton.setLoading(false);
                            return;
                        }
                        if (TextUtils.isEmpty(e2.getText().toString())) {
                            e2.setError("Place cannot be empty");
                            horizontalLoadingButton.setLoading(false);
                            return;
                        }
                        db.collection("cleanliness").document(e2.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    if (documentSnapshot.exists() && documentSnapshot != null) {
                                        Toast.makeText(Cleanliness.this, "Task Already Exists...", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Intent intent = new Intent(Cleanliness.this, CleanImageActivity.class);
                                        intent.putExtra("rollno", e1.getText().toString());
                                        intent.putExtra("dustplace", e2.getText().toString());
                                        intent.putExtra("email",email);
                                        startActivity(intent);
                                    }
                                }
                            }
                        });

                        horizontalLoadingButton.setLoading(false);
                    }
                }, 2000);

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
}
