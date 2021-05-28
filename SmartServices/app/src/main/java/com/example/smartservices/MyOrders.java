package com.example.smartservices;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.List;

public class MyOrders extends AppCompatActivity {
Ordersdb user;
FirebaseFirestore db=FirebaseFirestore.getInstance();
List<String> list=new ArrayList<>();
    ArrayAdapter<String> adapter1;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    ImageView back;
    ListView lv;
    private SlidrInterface slidr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getSupportActionBar().hide();
        slidr= Slidr.attach(this);
        setContentView(R.layout.activity_orders);
        String em=getIntent().getStringExtra("email");
        lv=findViewById(R.id.list1);
        back=findViewById(R.id.header);
        final Toolbar toolbar = findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        appBarLayout = findViewById(R.id.appbar);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        if (InitApplication.getInstance().isNightModeEnabled()) {
            back.setImageResource(R.drawable.can);
        }
        collapsingToolbar.setTitle("My Orders");
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        db.collection("orders").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable final QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                String data, data1;
                list.removeAll(list);
                for (QueryDocumentSnapshot documentSnapshot : documentSnapshots) {
                    data = "";
                    data1 = "";
                    user = documentSnapshot.toObject(Ordersdb.class);
                    String item = user.getItems();
                    String quantity = user.getQuantity();
                    String email=user.getEmail();
                    String id=documentSnapshot.getId();
                    if(email!=null && email.equalsIgnoreCase(em))
                    {
                        list.add(item+" "+"X"+" "+quantity+"\n"+"Order Number: "+id);
                    }
                }
               /* if(list.size()==0)
                {
                    Intent intent=new Intent(MyOrders.this,EmptyOrders.class);
                    startActivity(intent);
                    finish();
                }*/
                adapter1 = new ArrayAdapter(MyOrders.this, android.R.layout.simple_list_item_1, list);
               lv.setAdapter(adapter1);
                LayoutAnimationController controller= AnimationUtils.loadLayoutAnimation(MyOrders.this,R.anim.list_layout_controller);
                lv.setLayoutAnimation(controller);
            }
        });
    }
}
