package com.example.smartservices;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class EventActivity1 extends AppCompatActivity
{
    Users2 users;
    private ViewPager mViewPager;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> al=new ArrayList<>();
    TextView title;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    //    private CardFragmentPagerAdapter mFragmentCardAdapter;
    CardView cardView;
    private boolean mShowingFragments = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (InitApplication.getInstance().isNightModeEnabled()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor("#16202C"));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.WHITE);
            }
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_events2);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        //   getSupportActionBar().hide();

        title=findViewById(R.id.eventstitle);
        title.setTypeface(EasyFonts.captureIt(EventActivity1.this));
        db.collection("events").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable final QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null)
                    return;
                mCardAdapter = new CardPagerAdapter(EventActivity1.this);
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                {
                    users=documentSnapshot.toObject(Users2.class);
                    String desc=users.getDesc();
                    String title=users.getTitle();
                    String link=users.getLink();
                    mCardAdapter.addCardItem(new CardItem(title, desc,link));
                }
                if(mCardAdapter.getCount()==0)
                {
                    mCardAdapter.addCardItem(new CardItem("No data","",""));
                }
                //   mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                //         dpToPixels(2, MainActivity.this));

                mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
                mCardShadowTransformer.enableScaling(true);
                //   mFragmentCardShadowTransformer.enableScaling(true);
                mViewPager.setAdapter(mCardAdapter);
                mViewPager.setPageTransformer( false, mCardShadowTransformer);
                mViewPager.setOffscreenPageLimit(3);
            }
        });

    }
    //phone connect cheyy ra
    //em vastale open ayindha...ayindhi kani em ratle
    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }
}
