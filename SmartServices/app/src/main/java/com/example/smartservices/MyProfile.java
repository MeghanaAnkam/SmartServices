package com.example.smartservices;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.tonywills.loadingbutton.HorizontalLoadingButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class MyProfile extends AppCompatActivity {
Switch aSwitch;
    private static String MY_PREFERENCES="nightmodeprefs";
    private static String KEY="isnightmode";
    SharedPreferences sharedPreferences;
    TextView name,emailid,account;
    private SlidrInterface slidr;
    GoogleSignInClient googleSignInClient;
    ImageView imageView;
    private HorizontalLoadingButton horizontalLoadingButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (InitApplication.getInstance().isNightModeEnabled()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#293859"));
        }
        slidr= Slidr.attach(this);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_profile);
        aSwitch = findViewById(R.id.switch1);
        account = findViewById(R.id.accountname);
        imageView = findViewById(R.id.userphoto);
        horizontalLoadingButton = (HorizontalLoadingButton) findViewById(R.id.signoutuser);
        name = findViewById(R.id.personname);
        emailid = findViewById(R.id.emailid);
        String ima = getIntent().getStringExtra("image");
        String username = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String ids = getIntent().getStringExtra("id");
        account.setText(username);
        name.setText(username);
        emailid.setText(email);
        Uri myUri = Uri.parse(ima);
        Glide.with(this).load(myUri).apply(RequestOptions.circleCropTransform()).into(imageView);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            aSwitch.setChecked(true);
        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    InitApplication.getInstance().setIsNightModeEnabled(true);
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    recreate();
                } else {
                    InitApplication.getInstance().setIsNightModeEnabled(false);
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    recreate();
                }
            }
        });
        horizontalLoadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horizontalLoadingButton.setLoading(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        signOut();
                        horizontalLoadingButton.setLoading(false);
                    }
                }, 1000);
            }
        });
    }
    private void signOut() {
        googleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MyProfile.this, "Signed out successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), GoogleSignin.class);
                        intent.putExtra("finish", true);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                        startActivity(intent);
                        finish();
                    }
                });
    }

}
