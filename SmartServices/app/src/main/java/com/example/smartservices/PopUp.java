package com.example.smartservices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PopUp extends Activity {
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
       progressDialog.setCanceledOnTouchOutside(false);
        String image=getIntent().getStringExtra("Image");
        ImageView imageView=findViewById(R.id.dblostimg1);
        //Picasso.get().load(image).into(imageView);
        Picasso.get().load(image).into(imageView, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                //Image loaded successfully, now dismiss progress bar.
                progressDialog.dismiss();
            }

            @Override
            public void onError(Exception e) {

            }
        });
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.6));

    }

}
