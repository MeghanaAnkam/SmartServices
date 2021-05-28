package com.example.smartservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class DashBoard extends AppCompatActivity {
    CardView canteen,lostandfound,calcgpa,myprofile,clean,events;
    long mLastClickTime = 0;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (InitApplication.getInstance().isNightModeEnabled()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
             AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        setContentView(R.layout.activity_dash_board);
        canteen=findViewById(R.id.canteen);
        lostandfound=findViewById(R.id.lostandfound);
        calcgpa=findViewById(R.id.calcgpa);
        clean=findViewById(R.id.clean);
        myprofile=findViewById(R.id.myprofile);
        events=findViewById(R.id.events);
        imageView=findViewById(R.id.airplane);
        String ima=getIntent().getStringExtra("imageUri");
        String name=getIntent().getStringExtra("name");
        final String email=getIntent().getStringExtra("email");
        String id=getIntent().getStringExtra("id");
        canteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                Intent intent=new Intent(DashBoard.this,MainActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                Intent intent=new Intent(DashBoard.this,EventActivity1.class);
                intent.putExtra("email",email);
                startActivity(intent);
                Animatoo.animateSlideUp(DashBoard.this);
            }
        });
        calcgpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                Intent intent=new Intent(DashBoard.this,InsideGpa.class);
                intent.putExtra("email",email);
                startActivity(intent);
                Animatoo.animateSlideUp(DashBoard.this);
            }
        });
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                Intent intent=new Intent(DashBoard.this,Cleanliness.class);
                intent.putExtra("email",email);
                startActivity(intent);
                Animatoo.animateSlideUp(DashBoard.this);
            }
        });
        lostandfound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                Intent intent=new Intent(DashBoard.this,LostAndFound.class);
                intent.putExtra("email",email);
                startActivity(intent);
                Animatoo.animateSlideUp(DashBoard.this);
            }
        });
        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                Intent intent=new Intent(DashBoard.this,MyProfile.class);
                intent.putExtra("email",email);
                intent.putExtra("image",ima);
                intent.putExtra("name",name);
                intent.putExtra("id",id);
                startActivity(intent);
                Animatoo.animateSlideUp(DashBoard.this);
            }
        });
    }
}
