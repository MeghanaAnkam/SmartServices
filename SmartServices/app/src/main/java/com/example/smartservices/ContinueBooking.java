package com.example.smartservices;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class ContinueBooking extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_continue_booking);
        String email=getIntent().getStringExtra("email");
        Thread myThread=new Thread(){
            @Override
            public void run()
            {
                try {
                    sleep(2500);
                    Intent intent=new Intent(getApplicationContext(),MyOrders.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                    finish();
                    Animatoo.animateInAndOut(ContinueBooking.this);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}
