package com.example.smartservices;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.firestore.FirebaseFirestore;

public class Events_Admin1 extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Users user;
    TextView tv1,tv2,link;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private Menu collapsedMenu;
    ImageView imageView;
    Button btn;
    private boolean appBarExpanded = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events__admin1);
        tv2=findViewById(R.id.textviewdes1);
        link=findViewById(R.id.link2);
        String title= getIntent().getStringExtra("title");
        String desc=getIntent().getStringExtra("desc");
        imageView=findViewById(R.id.header1);
        String link1=getIntent().getStringExtra("link");
        String id=getIntent().getStringExtra("id");
        tv2.setText(desc);
        final Toolbar toolbar = findViewById(R.id.anim_toolbar1);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appBarLayout = findViewById(R.id.appbar1);
        link.setText(link1);
        btn=findViewById(R.id.delete_event);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar1);
        //  collapsingToolbar.setCollapsedTitleTextAppearance(R.font.shade);
        collapsingToolbar.setTitle(title);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.events2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Events_Admin1.this).create();
                alertDialog.setTitle("Are you Sure");
                alertDialog.setMessage("The event will be deleted..");
                alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                db.collection("events").document(id).delete();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        /*Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @SuppressWarnings("ResourceType")
            @Override
            public void onGenerated(Palette palette) {
                int vibrantColor = palette.getVibrantColor(R.color.primary_500);
                collapsingToolbar.setContentScrimColor(vibrantColor);
                collapsingToolbar.setStatusBarScrimColor(R.color.black_trans80);
            }
        });*/
    }
}
