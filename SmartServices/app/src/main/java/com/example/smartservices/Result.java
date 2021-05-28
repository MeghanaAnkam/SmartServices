package com.example.smartservices;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.Locale;

public class Result extends AppCompatActivity implements TextToSpeech.OnInitListener {
    float x1,x2,y1,y2;
    TextToSpeech TTS;
    private SlidrInterface slidr;
    TextView textView;
    TextView p1, p2, p3, p4, p5, p6, p7, p8, crd1, crd2, crd3, crd4, crd5, crd6, crd7, crd8;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        if (InitApplication.getInstance().isNightModeEnabled()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor("#353a50"));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor("#1e3d59"));
            }

        }
        slidr= Slidr.attach(this);
        setContentView(R.layout.activity_result);
        TTS = new TextToSpeech(this, this);
        textView = findViewById(R.id.cgpa);
        p1 = findViewById(R.id.gra1);
        p2 = findViewById(R.id.gra2);
        p3 = findViewById(R.id.gra3);
        p4 = findViewById(R.id.gra4);
        p5 = findViewById(R.id.gra5);
        p6 = findViewById(R.id.gra6);
        p7 = findViewById(R.id.gra7);
        p8 = findViewById(R.id.gra8);
        crd1 = findViewById(R.id.cred1);
        crd2 = findViewById(R.id.cred2);
        imageView=findViewById(R.id.thank);
        int x=AppCompatDelegate.getDefaultNightMode();
        if(x==2)
        {
            imageView.setVisibility(View.INVISIBLE);
        }
        crd3 = findViewById(R.id.cred3);
        crd4 = findViewById(R.id.cred4);
        crd5 = findViewById(R.id.cred5);
        crd6 = findViewById(R.id.cred6);
        crd7 = findViewById(R.id.cred7);
        crd8 = findViewById(R.id.cred8);
        AlertDialog.Builder builder = new AlertDialog.Builder(Result.this);
        builder.setIcon(android.R.drawable.star_big_on);
        builder.setTitle("Thankyou for using this app");
        builder.setMessage("Screenshot the result for further reference");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        double re = getIntent().getDoubleExtra("Result", 0);
        String.format("%.2f", re);
        double g1 = getIntent().getDoubleExtra("gr1", 0);
        double g2 = getIntent().getDoubleExtra("gr2", 0);
        double g3 = getIntent().getDoubleExtra("gr3", 0);
        double g4 = getIntent().getDoubleExtra("gr4", 0);
        double g5 = getIntent().getDoubleExtra("gr5", 0);
        double g6 = getIntent().getDoubleExtra("gr6", 0);
        double g7 = getIntent().getDoubleExtra("gr7", 0);
        double g8 = getIntent().getDoubleExtra("gr8", 0);
        double c1 = getIntent().getDoubleExtra("cr1", 0);
        double c2 = getIntent().getDoubleExtra("cr2", 0);
        double c3 = getIntent().getDoubleExtra("cr3", 0);
        double c4 = getIntent().getDoubleExtra("cr4", 0);
        double c5 = getIntent().getDoubleExtra("cr5", 0);
        double c6 = getIntent().getDoubleExtra("cr6", 0);
        double c7 = getIntent().getDoubleExtra("cr7", 0);
        double c8 = getIntent().getDoubleExtra("cr8", 0);
        if (g1 == 0.0 && c1 == 0.0) {
            p1.setVisibility(View.INVISIBLE);
            crd1.setVisibility(View.INVISIBLE);
        } else {
            p1.setText(g1 + "");
            crd1.setText(c1 + "");
        }
        if (g2 == 0.0 && c2 == 0.0) {
            p2.setVisibility(View.INVISIBLE);
            crd2.setVisibility(View.INVISIBLE);
        } else {
            p2.setText(g2 + "");
            crd2.setText(c2 + "");
        }
        if (g3 == 0.0 && c3 == 0.0) {
            p3.setVisibility(View.INVISIBLE);
            crd3.setVisibility(View.INVISIBLE);
        } else {
            p3.setText(g3 + "");
            crd3.setText(c3 + "");
        }
        if (g4 == 0.0 && c4 == 0.0) {
            p4.setVisibility(View.INVISIBLE);
            crd4.setVisibility(View.INVISIBLE);
        } else {
            p4.setText(g4 + "");
            crd4.setText(c4 + "");
        }
        if (g5 == 0.0 && c5 == 0.0) {
            p5.setVisibility(View.INVISIBLE);
            crd5.setVisibility(View.INVISIBLE);
        } else {
            p5.setText(g5 + "");
            crd5.setText(c5 + "");
        }
        if (g6 == 0.0 && c6 == 0.0) {
            p6.setVisibility(View.INVISIBLE);
            crd6.setVisibility(View.INVISIBLE);
        } else {
            p6.setText(g6 + "");
            crd6.setText(c6 + "");
        }
        if (g7 == 0.0 && c7 == 0.0) {
            p6.setVisibility(View.INVISIBLE);
            crd6.setVisibility(View.INVISIBLE);
        } else {
            p7.setText(g7 + "");
            crd7.setText(c7 + "");
        }
        if (g7 == 0.0 && c7 == 0.0) {
            p6.setVisibility(View.INVISIBLE);
            crd6.setVisibility(View.INVISIBLE);
        } else {
            p8.setText(g8 + "");
            crd8.setText(c8 + "");
        }
        if (re >= 7) {
            AlertDialog.Builder builde = new AlertDialog.Builder(Result.this);
            builde.setIcon(android.R.drawable.button_onoff_indicator_on);
            builde.setTitle("Congrats");
            builde.setMessage("You have scored above 6");
            builde.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialogue = builde.create();
            dialogue.show();
        } else {
            AlertDialog.Builder build = new AlertDialog.Builder(Result.this);
            build.setIcon(android.R.drawable.presence_busy);
            build.setTitle("Can do better");
            build.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialogu = build.create();
            dialogu.show();
        }
        textView.setText(String.format("%.2f", re) + "");
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = TTS.setLanguage(Locale.ENGLISH);
            TTS.setPitch((float) 1
            );
            TTS.setSpeechRate((float) 1);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language Not Supported");


            } else {
                speak();
            }
        } else {
            Log.e("TTS", "Error initializing TTS Engine");

        }

    }

    public void speak() {
        double i = getIntent().getDoubleExtra("Result", 0);
        if (i > 7.00) {
            TTS.speak("congratutions your cgpa is " + textView.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            TTS.speak("cgpa is " + textView.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }
}