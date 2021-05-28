package com.example.smartservices;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class InsideGpa extends AppCompatActivity {
    private SlidrInterface slidr;
    long mLastClickTime = 0;
    AutoCompleteTextView g1,g2,g3,g4,g5,g6,g7,g8,c1,c2,c3,c4,c5,c6,c7,c8,g9,c9;
    Button cal;
    private static String[] GRADES = new String[]{
            "O","A+","A","B+","B","C","F"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        slidr= Slidr.attach(this);
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
        getSupportActionBar().hide();
        setContentView(R.layout.activity_inside_gpa);
        final MediaPlayer mpl= MediaPlayer.create(this,R.raw.sample1);
        AlertDialog.Builder builder=new AlertDialog.Builder(InsideGpa.this);
        builder.setIcon(android.R.drawable.presence_online);
        builder.setTitle("please read these instructions:");
        builder.setMessage("\nO --->  10\nA+ -->  9\nA --->   8\nB+ -->  7\nB --->   6\nC --->   5\nF --->   0\n Enter in Alphabets or Numbers");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
        g1 = findViewById(R.id.g1);
        g2 = findViewById(R.id.g2);
        g3 = findViewById(R.id.g3);
        g4 = findViewById(R.id.g4);
        g5 = findViewById(R.id.g5);
        g6 = findViewById(R.id.g6);
        g7 = findViewById(R.id.g7);
        g8 = findViewById(R.id.g8);
        cal=findViewById(R.id.cal);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item,
                GRADES);
        g1.setThreshold(1);
        g2.setThreshold(1);
        g3.setThreshold(1);
        g4.setThreshold(1);
        g5.setThreshold(1);
        g6.setThreshold(1);
        g7.setThreshold(1);
        g8.setThreshold(1);
        g1.setAdapter(adapter);
        g2.setAdapter(adapter);
        g3.setAdapter(adapter);
        g4.setAdapter(adapter);
        g5.setAdapter(adapter);
        g6.setAdapter(adapter);
        g7.setAdapter(adapter);
        g8.setAdapter(adapter);

        c1=findViewById(R.id.c1);
        c2=findViewById(R.id.c2);
        c3=findViewById(R.id.c3);
        c4=findViewById(R.id.c4);
        c5=findViewById(R.id.c5);
        c6=findViewById(R.id.c6);
        c7=findViewById(R.id.c7);
        c8=findViewById(R.id.c8);
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                mpl.seekTo(1000);
                mpl.start();

                if (g1.getText().toString().isEmpty() && g2.getText().toString().isEmpty() && g3.getText().toString().isEmpty() && g4.getText().toString().isEmpty() && g5.getText().toString().isEmpty() && g6.getText().toString().isEmpty() && g7.getText().toString().isEmpty() && g8.getText().toString().isEmpty()) {
                    StyleableToast.makeText(InsideGpa.this, "Please enter atleast one subject value", R.style.Toast).show();
                    return;
                }
                if (c1.getText().toString().isEmpty() && c2.getText().toString().isEmpty() && c3.getText().toString().isEmpty() && c4.getText().toString().isEmpty() && c5.getText().toString().isEmpty() && c6.getText().toString().isEmpty() && c7.getText().toString().isEmpty() && c8.getText().toString().isEmpty()) {
                    StyleableToast.makeText(InsideGpa.this, "Please enter atleast one subject value", R.style.Toast).show();
                    return;
                }
                if ((((g1.getText().toString().equalsIgnoreCase("O")) || g1.getText().toString().equalsIgnoreCase("10") || (g1.getText().toString().equalsIgnoreCase("A+")) || g1.getText().toString().equalsIgnoreCase("9")||(g1.getText().toString().equalsIgnoreCase("A")) ||g1.getText().toString().equalsIgnoreCase("8")|| (g1.getText().toString().equalsIgnoreCase("B+")) ||g1.getText().toString().equalsIgnoreCase("7") || (g1.getText().toString().equalsIgnoreCase("B"))  ||g1.getText().toString().equalsIgnoreCase("6")|| (g1.getText().toString().equalsIgnoreCase("C")) ||g1.getText().toString().equalsIgnoreCase("5")||g1.getText().toString().equalsIgnoreCase("F")||g1.getText().toString().equalsIgnoreCase(""))
                        || ((g2.getText().toString().equalsIgnoreCase("O")) ||g2.getText().toString().equalsIgnoreCase("10") ||(g2.getText().toString().equalsIgnoreCase("A+")) ||g2.getText().toString().equalsIgnoreCase("9")||(g2.getText().toString().equalsIgnoreCase("A")) ||g2.getText().toString().equalsIgnoreCase("8") || (g2.getText().toString().equalsIgnoreCase("B+")) ||g2.getText().toString().equalsIgnoreCase("7")|| (g2.getText().toString().equalsIgnoreCase("B")) ||g2.getText().toString().equalsIgnoreCase("6")|| (g2.getText().toString().equalsIgnoreCase("C"))||g2.getText().toString().equalsIgnoreCase("5")||g2.getText().toString().equalsIgnoreCase("F")||g2.getText().toString().equalsIgnoreCase("0"))
                        || ((g3.getText().toString().equalsIgnoreCase("O")) || g3.getText().toString().equalsIgnoreCase("10")||(g3.getText().toString().equalsIgnoreCase("A+")) ||g3.getText().toString().equalsIgnoreCase("9") ||  (g3.getText().toString().equalsIgnoreCase("A")) ||g3.getText().toString().equalsIgnoreCase("8")|| (g3.getText().toString().equalsIgnoreCase("B+"))  ||g3.getText().toString().equalsIgnoreCase("7")|| (g3.getText().toString().equalsIgnoreCase("B"))||g3.getText().toString().equalsIgnoreCase("6") || (g3.getText().toString().equalsIgnoreCase("C"))||g3.getText().toString().equalsIgnoreCase("5")||g3.getText().toString().equalsIgnoreCase("F")||g3.getText().toString().equalsIgnoreCase("0"))
                        || ((g4.getText().toString().equalsIgnoreCase("O")) || g4.getText().toString().equalsIgnoreCase("10")||(g4.getText().toString().equalsIgnoreCase("A+")) ||g4.getText().toString().equalsIgnoreCase("9")|| (g4.getText().toString().equalsIgnoreCase("A")) ||g4.getText().toString().equalsIgnoreCase("8")|| (g4.getText().toString().equalsIgnoreCase("B+"))  ||g4.getText().toString().equalsIgnoreCase("7")|| (g4.getText().toString().equalsIgnoreCase("B")) ||g4.getText().toString().equalsIgnoreCase("6")|| (g4.getText().toString().equalsIgnoreCase("C"))||g4.getText().toString().equalsIgnoreCase("5")||g4.getText().toString().equalsIgnoreCase("F")||g4.getText().toString().equalsIgnoreCase("0"))
                        || ((g5.getText().toString().equalsIgnoreCase("O")) ||g5.getText().toString().equalsIgnoreCase("10")|| (g5.getText().toString().equalsIgnoreCase("A+")) ||g5.getText().toString().equalsIgnoreCase("9")|| (g5.getText().toString().equalsIgnoreCase("A")) ||g5.getText().toString().equalsIgnoreCase("8")|| (g5.getText().toString().equalsIgnoreCase("B+"))  ||g5.getText().toString().equalsIgnoreCase("7")|| (g5.getText().toString().equalsIgnoreCase("B")) ||g5.getText().toString().equalsIgnoreCase("6")|| (g5.getText().toString().equalsIgnoreCase("C"))||g5.getText().toString().equalsIgnoreCase("5")||g5.getText().toString().equalsIgnoreCase("F")||g5.getText().toString().equalsIgnoreCase("0"))
                        || ((g6.getText().toString().equalsIgnoreCase("O")) ||g6.getText().toString().equalsIgnoreCase("10")|| (g6.getText().toString().equalsIgnoreCase("A+")) ||g6.getText().toString().equalsIgnoreCase("9")|| (g6.getText().toString().equalsIgnoreCase("A")) ||g6.getText().toString().equalsIgnoreCase("8")|| (g6.getText().toString().equalsIgnoreCase("B+"))  ||g6.getText().toString().equalsIgnoreCase("7")|| (g6.getText().toString().equalsIgnoreCase("B")) ||g6.getText().toString().equalsIgnoreCase("6")|| (g6.getText().toString().equalsIgnoreCase("C"))||g6.getText().toString().equalsIgnoreCase("5")||g6.getText().toString().equalsIgnoreCase("F")||g6.getText().toString().equalsIgnoreCase("0"))
                        || ((g7.getText().toString().equalsIgnoreCase("O")) || g7.getText().toString().equalsIgnoreCase("10")||(g7.getText().toString().equalsIgnoreCase("A+")) ||g7.getText().toString().equalsIgnoreCase("9")|| (g7.getText().toString().equalsIgnoreCase("A")) ||g7.getText().toString().equalsIgnoreCase("8")|| (g7.getText().toString().equalsIgnoreCase("B+"))  ||g7.getText().toString().equalsIgnoreCase("7")|| (g7.getText().toString().equalsIgnoreCase("B")) ||g7.getText().toString().equalsIgnoreCase("6")|| (g7.getText().toString().equalsIgnoreCase("C"))||g7.getText().toString().equalsIgnoreCase("5")||g7.getText().toString().equalsIgnoreCase("F")||g7.getText().toString().equalsIgnoreCase("0"))
                        || ((g8.getText().toString().equalsIgnoreCase("O")) ||g8.getText().toString().equalsIgnoreCase("10")|| (g8.getText().toString().equalsIgnoreCase("A+")) ||g8.getText().toString().equalsIgnoreCase("9")|| (g8.getText().toString().equalsIgnoreCase("A")) ||g8.getText().toString().equalsIgnoreCase("8")|| (g8.getText().toString().equalsIgnoreCase("B+"))  ||g8.getText().toString().equalsIgnoreCase("7")|| (g8.getText().toString().equalsIgnoreCase("B")) ||g8.getText().toString().equalsIgnoreCase("6")|| (g8.getText().toString().equalsIgnoreCase("C"))||g8.getText().toString().equalsIgnoreCase("5"))||g8.getText().toString().equalsIgnoreCase("F")||g8.getText().toString().equalsIgnoreCase("0")))
                {
                    if(!(g1.getText().toString().equalsIgnoreCase("O")|| g1.getText().toString().equalsIgnoreCase("10")||(g1.getText().toString().equalsIgnoreCase("A+")) ||g1.getText().toString().equalsIgnoreCase("9")|| (g1.getText().toString().equalsIgnoreCase("A")) ||g1.getText().toString().equalsIgnoreCase("8")|| (g1.getText().toString().equalsIgnoreCase("B+"))  ||g1.getText().toString().equalsIgnoreCase("7")|| (g1.getText().toString().equalsIgnoreCase("B"))||g1.getText().toString().equalsIgnoreCase("6") || (g1.getText().toString().equalsIgnoreCase("C"))||g1.getText().toString().equalsIgnoreCase("5")||(g1.getText().toString().equalsIgnoreCase(""))||(g1.getText().toString().equalsIgnoreCase("0"))||g1.getText().toString().equalsIgnoreCase("F")) )
                    {
                        g1.setText("");
                        StyleableToast.makeText(InsideGpa.this, "Invalid Assignment at Grade 1", R.style.Toast2).show();
                        return;
                    }

                    if(!(g2.getText().toString().equalsIgnoreCase("O")|| g2.getText().toString().equalsIgnoreCase("10")||(g2.getText().toString().equalsIgnoreCase("A+")) ||g2.getText().toString().equalsIgnoreCase("9")|| (g2.getText().toString().equalsIgnoreCase("A")) ||g2.getText().toString().equalsIgnoreCase("8")|| (g2.getText().toString().equalsIgnoreCase("B+"))  ||g2.getText().toString().equalsIgnoreCase("7")|| (g2.getText().toString().equalsIgnoreCase("B"))||g2.getText().toString().equalsIgnoreCase("6") || (g2.getText().toString().equalsIgnoreCase("C"))||g2.getText().toString().equalsIgnoreCase("5")||(g2.getText().toString().equalsIgnoreCase(""))||(g2.getText().toString().equalsIgnoreCase("0"))||g2.getText().toString().equalsIgnoreCase("F")) )
                    {
                        g2.setText("");
                        StyleableToast.makeText(InsideGpa.this, "Invalid Assignment at Grade 2", R.style.Toast2).show();
                        return;
                    }
                    if(!(g3.getText().toString().equalsIgnoreCase("O")||g3.getText().toString().equalsIgnoreCase("10")|| (g3.getText().toString().equalsIgnoreCase("A+"))||g3.getText().toString().equalsIgnoreCase("9") || (g3.getText().toString().equalsIgnoreCase("A"))||g3.getText().toString().equalsIgnoreCase("8") || (g3.getText().toString().equalsIgnoreCase("B+"))  ||g3.getText().toString().equalsIgnoreCase("7")|| (g3.getText().toString().equalsIgnoreCase("B"))||g3.getText().toString().equalsIgnoreCase("6") || (g3.getText().toString().equalsIgnoreCase("C"))||g3.getText().toString().equalsIgnoreCase("5")||(g3.getText().toString().equalsIgnoreCase(""))||(g3.getText().toString().equalsIgnoreCase("0"))||g3.getText().toString().equalsIgnoreCase("F")) )
                    {
                        g3.setText("");
                        StyleableToast.makeText(InsideGpa.this, "Invalid Assignment at Grade 3", R.style.Toast2).show();
                        return;
                    }
                    if(!(g4.getText().toString().equalsIgnoreCase("O")|| g4.getText().toString().equalsIgnoreCase("10")||(g4.getText().toString().equalsIgnoreCase("A+"))||g4.getText().toString().equalsIgnoreCase("9") || (g4.getText().toString().equalsIgnoreCase("A")) ||g4.getText().toString().equalsIgnoreCase("8")|| (g4.getText().toString().equalsIgnoreCase("B+")) ||g4.getText().toString().equalsIgnoreCase("7") || (g4.getText().toString().equalsIgnoreCase("B")) ||g4.getText().toString().equalsIgnoreCase("6")|| (g4.getText().toString().equalsIgnoreCase("C"))||g4.getText().toString().equalsIgnoreCase("5")||(g4.getText().toString().equalsIgnoreCase(""))||(g4.getText().toString().equalsIgnoreCase("0"))||g4.getText().toString().equalsIgnoreCase("F")) )
                    {
                        g4.setText("");
                        StyleableToast.makeText(InsideGpa.this, "Invalid Assignment at Grade 4", R.style.Toast2).show();
                        return;
                    }
                    if(!(g5.getText().toString().equalsIgnoreCase("O")|| g5.getText().toString().equalsIgnoreCase("10")||(g5.getText().toString().equalsIgnoreCase("A+"))||g5.getText().toString().equalsIgnoreCase("9") || (g5.getText().toString().equalsIgnoreCase("A"))||g5.getText().toString().equalsIgnoreCase("8") || (g5.getText().toString().equalsIgnoreCase("B+"))  ||g5.getText().toString().equalsIgnoreCase("7")|| (g5.getText().toString().equalsIgnoreCase("B"))||g5.getText().toString().equalsIgnoreCase("6") || (g5.getText().toString().equalsIgnoreCase("C"))||g5.getText().toString().equalsIgnoreCase("5")||(g5.getText().toString().equalsIgnoreCase(""))||(g5.getText().toString().equalsIgnoreCase("0"))||g5.getText().toString().equalsIgnoreCase("F")) )
                    {
                        g5.setText("");
                        StyleableToast.makeText(InsideGpa.this, "Invalid Assignment at Grade 5", R.style.Toast2).show();
                        return;
                    }
                    if(!(g6.getText().toString().equalsIgnoreCase("O")||g6.getText().toString().equalsIgnoreCase("10")|| (g6.getText().toString().equalsIgnoreCase("A+")) ||g6.getText().toString().equalsIgnoreCase("9")|| (g6.getText().toString().equalsIgnoreCase("A")) ||g6.getText().toString().equalsIgnoreCase("8")|| (g6.getText().toString().equalsIgnoreCase("B+"))  ||g6.getText().toString().equalsIgnoreCase("7")|| (g6.getText().toString().equalsIgnoreCase("B"))||g6.getText().toString().equalsIgnoreCase("6") || (g6.getText().toString().equalsIgnoreCase("C"))||g6.getText().toString().equalsIgnoreCase("5")||(g6.getText().toString().equalsIgnoreCase(""))||(g6.getText().toString().equalsIgnoreCase("0"))||g6.getText().toString().equalsIgnoreCase("F")) )
                    {
                        g6.setText("");
                        StyleableToast.makeText(InsideGpa.this, "Invalid Assignment at Grade 6", R.style.Toast2).show();
                        return;
                    }
                    if(!(g7.getText().toString().equalsIgnoreCase("O")||g7.getText().toString().equalsIgnoreCase("10")|| (g7.getText().toString().equalsIgnoreCase("A+"))||g7.getText().toString().equalsIgnoreCase("9") || (g7.getText().toString().equalsIgnoreCase("A"))||g7.getText().toString().equalsIgnoreCase("8")|| (g7.getText().toString().equalsIgnoreCase("B+")) ||g7.getText().toString().equalsIgnoreCase("7") || (g7.getText().toString().equalsIgnoreCase("B"))||g7.getText().toString().equalsIgnoreCase("6") || (g7.getText().toString().equalsIgnoreCase("C"))||g7.getText().toString().equalsIgnoreCase("5")||(g7.getText().toString().equalsIgnoreCase(""))||(g7.getText().toString().equalsIgnoreCase("0"))||g7.getText().toString().equalsIgnoreCase("F")) )
                    {
                        g7.setText("");
                        StyleableToast.makeText(InsideGpa.this, "Invalid Assignment at Grade 7", R.style.Toast2).show();
                        return;
                    }
                    if(!(g8.getText().toString().equalsIgnoreCase("O")||g8.getText().toString().equalsIgnoreCase("10")|| (g8.getText().toString().equalsIgnoreCase("A+")) ||g8.getText().toString().equalsIgnoreCase("9")|| (g8.getText().toString().equalsIgnoreCase("A")) ||g8.getText().toString().equalsIgnoreCase("8")|| (g8.getText().toString().equalsIgnoreCase("B+")) ||g8.getText().toString().equalsIgnoreCase("7") || (g8.getText().toString().equalsIgnoreCase("B"))||g8.getText().toString().equalsIgnoreCase("6") || (g8.getText().toString().equalsIgnoreCase("C"))||g8.getText().toString().equalsIgnoreCase("5")||(g8.getText().toString().equalsIgnoreCase(""))||(g8.getText().toString().equalsIgnoreCase("0"))||g8.getText().toString().equalsIgnoreCase("F")) )
                    {
                        g8.setText("");
                        StyleableToast.makeText(InsideGpa.this, "Invalid Assignment at Grade 8", R.style.Toast2).show();
                        return;
                    }

                    if (g1.getText().toString().equalsIgnoreCase("F")) {
                        g1.setText(0 + "");
                    }
                    if ( g2.getText().toString().equalsIgnoreCase("F")) {
                        g2.setText(0 + "");
                    }
                    if ( g3.getText().toString().equalsIgnoreCase("F")) {
                        g3.setText(0 + "");
                    }
                    if ( g4.getText().toString().equalsIgnoreCase("F")) {
                        g4.setText(0 + "");
                    }
                    if ( g5.getText().toString().equalsIgnoreCase("F")) {
                        g5.setText(0 + "");
                    }
                    if (g6.getText().toString().equalsIgnoreCase("F")) {
                        g6.setText(0 + "");
                    }
                    if ( g7.getText().toString().equalsIgnoreCase("F")) {
                        g7.setText(0 + "");
                    }
                    if ( g8.getText().toString().equalsIgnoreCase("F")) {
                        g8.setText(0 + "");
                    }

                    if (g1.getText().toString().equalsIgnoreCase("O")) {
                        g1.setText(10 + "");
                    }
                    if (g2.getText().toString().equalsIgnoreCase("O")) {
                        g2.setText(10 + "");
                    }
                    if (g3.getText().toString().equalsIgnoreCase("O")) {
                        g3.setText(10 + "");
                    }
                    if (g4.getText().toString().equalsIgnoreCase("O")) {
                        g4.setText(10 + "");
                    }
                    if (g5.getText().toString().equalsIgnoreCase("O")) {
                        g5.setText(10 + "");
                    }
                    if (g6.getText().toString().equalsIgnoreCase("O")) {
                        g6.setText(10 + "");
                    }
                    if (g7.getText().toString().equalsIgnoreCase("O")) {
                        g7.setText(10 + "");
                    }
                    if (g8.getText().toString().equalsIgnoreCase("O")) {
                        g8.setText(10 + "");
                    }
                    if (g1.getText().toString().equalsIgnoreCase("A+")) {
                        g1.setText(9 + "");
                    }
                    if (g2.getText().toString().equalsIgnoreCase("A+")) {
                        g2.setText(9 + "");
                    }
                    if (g3.getText().toString().equalsIgnoreCase("A+")) {
                        g3.setText(9 + "");
                    }
                    if (g4.getText().toString().equalsIgnoreCase("A+")) {
                        g4.setText(9 + "");
                    }
                    if (g5.getText().toString().equalsIgnoreCase("A+")) {
                        g5.setText(9 + "");
                    }
                    if (g6.getText().toString().equalsIgnoreCase("A+")) {
                        g6.setText(9 + "");
                    }
                    if (g7.getText().toString().equalsIgnoreCase("A+")) {
                        g7.setText(9 + "");
                    }
                    if (g8.getText().toString().equalsIgnoreCase("A+")) {
                        g8.setText(9 + "");
                    }
                    if (g1.getText().toString().equalsIgnoreCase("A")) {
                        g1.setText(8 + "");
                    }
                    if (g2.getText().toString().equalsIgnoreCase("A")) {
                        g2.setText(8 + "");
                    }
                    if (g3.getText().toString().equalsIgnoreCase("A")) {
                        g3.setText(8 + "");
                    }
                    if (g4.getText().toString().equalsIgnoreCase("A")) {
                        g4.setText(8 + "");
                    }
                    if (g5.getText().toString().equalsIgnoreCase("A")) {
                        g5.setText(8 + "");
                    }
                    if (g6.getText().toString().equalsIgnoreCase("A")) {
                        g6.setText(8 + "");
                    }
                    if (g7.getText().toString().equalsIgnoreCase("A")) {
                        g7.setText(8 + "");
                    }
                    if (g8.getText().toString().equalsIgnoreCase("A")) {
                        g8.setText(8 + "");
                    }
                    if (g1.getText().toString().equalsIgnoreCase("B+")) {
                        g1.setText(7 + "");
                    }
                    if (g2.getText().toString().equalsIgnoreCase("B+")) {
                        g2.setText(7 + "");
                    }
                    if (g3.getText().toString().equalsIgnoreCase("B+")) {
                        g3.setText(7 + "");
                    }
                    if (g4.getText().toString().equalsIgnoreCase("B+")) {
                        g4.setText(7 + "");
                    }
                    if (g5.getText().toString().equalsIgnoreCase("B+")) {
                        g5.setText(7 + "");
                    }
                    if (g6.getText().toString().equalsIgnoreCase("B+")) {
                        g6.setText(7 + "");
                    }
                    if (g7.getText().toString().equalsIgnoreCase("B+")) {
                        g7.setText(7 + "");
                    }
                    if (g8.getText().toString().equalsIgnoreCase("B+")) {
                        g8.setText(7 + "");
                    }
                    if (g1.getText().toString().equalsIgnoreCase("B")) {
                        g1.setText(6 + "");
                    }
                    if (g2.getText().toString().equalsIgnoreCase("B")) {
                        g2.setText(6 + "");
                    }
                    if (g3.getText().toString().equalsIgnoreCase("B")) {
                        g3.setText(6 + "");
                    }
                    if (g4.getText().toString().equalsIgnoreCase("B")) {
                        g4.setText(6 + "");
                    }
                    if (g5.getText().toString().equalsIgnoreCase("B")) {
                        g5.setText(6 + "");
                    }
                    if (g6.getText().toString().equalsIgnoreCase("B")) {
                        g6.setText(6 + "");
                    }
                    if (g7.getText().toString().equalsIgnoreCase("B")) {
                        g7.setText(6 + "");
                    }
                    if (g8.getText().toString().equalsIgnoreCase("B")) {
                        g8.setText(6 + "");
                    }
                    if (g1.getText().toString().equalsIgnoreCase("C")) {
                        g1.setText(5 + "");
                    }
                    if (g2.getText().toString().equalsIgnoreCase("C")) {
                        g2.setText(5 + "");
                    }
                    if (g3.getText().toString().equalsIgnoreCase("C")) {
                        g3.setText(5 + "");
                    }
                    if (g4.getText().toString().equalsIgnoreCase("C")) {
                        g4.setText(5 + "");
                    }
                    if (g5.getText().toString().equalsIgnoreCase("C")) {
                        g5.setText(5 + "");
                    }
                    if (g6.getText().toString().equalsIgnoreCase("C")) {
                        g6.setText(5 + "");
                    }
                    if (g7.getText().toString().equalsIgnoreCase("C")) {
                        g7.setText(5 + "");
                    }
                    if (g8.getText().toString().equalsIgnoreCase("C")) {
                        g8.setText(5 + "");
                    }
                    double gr1,cr1,gr2,cr2,gr3,cr3,gr4,cr4,gr5,cr5,gr6,cr6,gr7,cr7,gr8,cr8;
                    if(g1.getText().toString().isEmpty() || c1.getText().toString().isEmpty())
                    {
                        gr1=0.00;
                        cr1=0.00;
                    }
                    else
                    {
                       gr1 = Double.parseDouble(g1.getText().toString().trim());
                        cr1 = Double.parseDouble(c1.getText().toString().trim());
                    }
                    if(g2.getText().toString().isEmpty() || c2.getText().toString().isEmpty())
                    {
                        gr2=0.00;
                        cr2=0.00;
                    }
                    else
                    {
                        gr2 = Double.parseDouble(g2.getText().toString().trim());
                        cr2 = Double.parseDouble(c2.getText().toString().trim());
                    }
                    if(g3.getText().toString().isEmpty() || c3.getText().toString().isEmpty())
                    {
                        gr3=0.00;
                        cr3=0.00;
                    }
                    else
                    {
                        gr3 = Double.parseDouble(g3.getText().toString().trim());
                        cr3 = Double.parseDouble(c3.getText().toString().trim());
                    }
                    if(g4.getText().toString().isEmpty()|| c4.getText().toString().isEmpty())
                    {
                        gr4=0.00;
                        cr4=0.00;
                    }
                    else
                    {
                        gr4 = Double.parseDouble(g4.getText().toString().trim());
                        cr4 = Double.parseDouble(c4.getText().toString().trim());
                    }
                    if(g5.getText().toString().isEmpty()|| c5.getText().toString().isEmpty())
                    {
                        gr5=0.00;
                        cr5=0.00;
                    }
                    else
                    {
                        gr5 = Double.parseDouble(g5.getText().toString().trim());
                        cr5 = Double.parseDouble(c5.getText().toString().trim());
                    }
                    if(g6.getText().toString().isEmpty()|| c6.getText().toString().isEmpty())
                    {
                        gr6=0.00;
                        cr6=0.00;
                    }
                    else
                    {
                        gr6 = Double.parseDouble(g6.getText().toString().trim());
                        cr6 = Double.parseDouble(c6.getText().toString().trim());
                    }
                    if(g7.getText().toString().isEmpty()|| c7.getText().toString().isEmpty())
                    {
                        gr7=0.00;
                        cr7=0.00;
                    }
                    else
                    {
                        gr7 = Double.parseDouble(g7.getText().toString().trim());
                        cr7 = Double.parseDouble(c7.getText().toString().trim());
                    }
                    if(g8.getText().toString().isEmpty()|| c8.getText().toString().isEmpty())
                    {
                        gr8=0.00;
                        cr8=0.00;
                    }
                    else
                    {
                        gr8 = Double.parseDouble(g8.getText().toString().trim());
                        cr8 = Double.parseDouble(c8.getText().toString().trim());
                    }
                    double d = ((gr1 * cr1) + (gr2 * cr2) + (gr3 * cr3) + (gr4 * cr4) + (gr5 * cr5) + (gr6 * cr6) + (gr7 * cr7) + (gr8 * cr8)) / (cr1 + cr2 + cr3 + cr4 + cr5 + cr6 + cr7 + cr8);
                    Intent intent = new Intent(InsideGpa.this, Result.class);
                    intent.putExtra("gr1", gr1);
                    intent.putExtra("cr1", cr1);
                    intent.putExtra("gr2", gr2);
                    intent.putExtra("cr2", cr2);
                    intent.putExtra("gr3", gr3);
                    intent.putExtra("cr3", cr3);
                    intent.putExtra("gr4", gr4);
                    intent.putExtra("cr4", cr4);
                    intent.putExtra("gr5", gr5);
                    intent.putExtra("cr5", cr5);
                    intent.putExtra("gr6", gr6);
                    intent.putExtra("cr6", cr6);
                    intent.putExtra("gr7", gr7);
                    intent.putExtra("cr7", cr7);
                    intent.putExtra("gr8", gr8);
                    intent.putExtra("cr8", cr8);
                    intent.putExtra("Result", d);
                    startActivity(intent);
                    Animatoo.animateSplit(InsideGpa.this);
                }
                else
                {
                    StyleableToast.makeText(InsideGpa.this, "Not a valid alphabet", R.style.Toast3).show();
                    c1.setText("");
                    c2.setText("");
                    c3.setText("");
                    c4.setText("");
                    c5.setText("");
                    c6.setText("");
                    c7.setText("");
                    c8.setText("");
                    g1.setText("");
                    g2.setText("");
                    g3.setText("");
                    g4.setText("");
                    g5.setText("");
                    g6.setText("");
                    g7.setText("");
                    g8.setText("");
                    return;
                }
            }
        });
    }
}
