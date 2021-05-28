package com.example.smartservices;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.github.tonywills.loadingbutton.LoadingButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.List;

public class Second extends AppCompatActivity {
    TextView tv1,tv2,pl;
    ImageView img;
    int count=1;
    DatabaseHelper1 mydb;
    boolean result=false;
    private LoadingButton sub;
    public ElegantNumberButton btn1;
    private SlidrInterface slidr;
    public ElegantNumberButton btn2;
    List<String> list=new ArrayList<>();
   // ImageButton add,del;
            //Button sub;

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        if (InitApplication.getInstance().isNightModeEnabled()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor("#212121"));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.WHITE);
            }
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_second);
        slidr= Slidr.attach(this);
        final String name=getIntent().getStringExtra("name");
        final String price=getIntent().getStringExtra("price");
        btn1 = findViewById(R.id.number_button);
      //  btn2 = findViewById(R.id.number_button2);
        btn1.setRange(1,10);
        btn1.setOnClickListener((ElegantNumberButton.OnClickListener) view -> {
            String number = btn1.getNumber();
            btn1.setNumber(number);
            count= Integer.parseInt(btn1.getNumber());
        });
        btn1.setOnValueChangeListener((view, oldValue, newValue) -> {
            Log.d("Hello", String.format("oldValue: %d   newValue: %d", oldValue, newValue));
        });
       /* btn2.setOnClickListener((ElegantNumberButton.OnClickListener) view -> {
            String number = btn2.getNumber();
          //  textView.setText(number);
            btn1.setNumber(number);
        });*/
        tv1=findViewById(R.id.text2);
       // add=findViewById(R.id.add);
    //    del=findViewById(R.id.del);
      //  pl=findViewById(R.id.count);
        sub=findViewById(R.id.btn);
        mydb=new DatabaseHelper1(Second.this);
        tv2=findViewById(R.id.text3);
        img=findViewById(R.id.img);
       if(InitApplication.getInstance().isNightModeEnabled())
       {
           img.setImageResource(R.drawable.din2_dark);
       }
        tv1.setText("Item name: "+name);
        tv2.setText("Price: "+"Rs. "+price);
      /*  add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                     pl.setText(String.valueOf(count));
            }
        });
       del.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               count--;
               if(count<=0)
               {
                   pl.setText("");
                   count=0;
               }
               else {
                   pl.setText(String.valueOf(count));
               }
           }
       });*/
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub.setLoading(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Cursor res=mydb.getAllData();
                        while (res.moveToNext())
                        {
                            list.add(res.getString(0));
                        }
                        for(int i=0;i<list.size();i++)
                        {
                            if(list.get(i).equalsIgnoreCase(name))
                            {
                                Toast.makeText(Second.this, name, Toast.LENGTH_SHORT).show();
                                result=true;
                                break;
                            }
                            else
                            {
                                result=false;
                            }
                        }
                        if(result==true)
                        {

                            mydb.updateData(name,price,String.valueOf(count), String.valueOf(Integer.parseInt(price) * count));
                            Toast.makeText(Second.this, "Data updated", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            boolean isInserted = mydb.insertData(name, price, String.valueOf(count), String.valueOf(Integer.parseInt(price) * count));
                            if (isInserted = true) {
                                Toast.makeText(Second.this, "data inserted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Second.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                            }
                        }
                        sub.setLoading(false);
                    }
                }, 1000);
            }
        });

    }


}
