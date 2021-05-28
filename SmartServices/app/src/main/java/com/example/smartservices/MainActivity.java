package com.example.smartservices;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;
import com.vstechlab.easyfonts.EasyFonts;
import com.yalantis.pulltomakesoup.PullToRefreshView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView list;
    TextView tv1, tv2, menu;
    String[] stringArray;
    ImageView imageButton;
    long mLastClickTime = 0;
    private SwipeRefreshLayout refreshLayout;
    String[] stringArray1;
    private SlidrInterface slidr;
    DatabaseHelper1 mydb;
    int imgList[] = {R.drawable.din2};
    ImageView imageView;
    ArrayList<String> al;
    ArrayList<String> al1;
    private static final int REFRESH_DELAY = 1000;
    PullToRefreshView mPullToRefreshView;
    private ArrayAdapter<Users> arrayAdapter;
    Users user;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                window.setStatusBarColor(Color.parseColor("#1e3d59"));
            }
        }
        setContentView(R.layout.activity_main);
        al = new ArrayList<>();
        imageButton = findViewById(R.id.imgbtn);
        slidr = Slidr.attach(this);
        imageView = findViewById(R.id.img_item);
        tv1 = findViewById(R.id.txt_name);
        menu = findViewById(R.id.menu_items);
        menu.setTypeface(EasyFonts.captureIt(MainActivity.this));
        String email = getIntent().getStringExtra("email");
        tv2 = findViewById(R.id.price);
        mydb = new DatabaseHelper1(MainActivity.this);
        //    refreshLayout=findViewById(R.id.swipe_refresh_layout);
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        db.collection("canteen").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable final QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                    return;
                                }
                                String data, data1;
                                al.removeAll(al);
                                for (QueryDocumentSnapshot documentSnapshot : documentSnapshots) {
                                    data = "";
                                    data1 = "";
                                    user = documentSnapshot.toObject(Users.class);
                                    String orgName = user.getItem();
                                    String price = user.getPrice();
                                    data += orgName + "\n";
                                    data1 += "Rs." + " " + price + "\n";
                                    al.add(data);
                                    al1.add(data1);
                                    stringArray = al.toArray(new String[al.size()]);
                                    stringArray1 = al1.toArray(new String[al1.size()]);
                                }
                                // arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, al);
                                CustomAdapter adapter = new CustomAdapter(MainActivity.this, stringArray, imgList, stringArray1);
                                list.setAdapter(adapter);
                            }
                        });
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        });
        al1 = new ArrayList<>();
        list = (ListView) findViewById(R.id.lv);
        db.collection("canteen").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable final QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                String data, data1;
                al.removeAll(al);
                for (QueryDocumentSnapshot documentSnapshot : documentSnapshots) {
                    data = "";
                    data1 = "";
                    user = documentSnapshot.toObject(Users.class);
                    String orgName = user.getItem();
                    String price = user.getPrice();
                    data += orgName + "\n";
                    data1 += "Rs." + " " + price + "\n";
                    al.add(data);
                    al1.add(data1);
                    stringArray = al.toArray(new String[al.size()]);
                    stringArray1 = al1.toArray(new String[al1.size()]);
                }
                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MainActivity.this, R.anim.list_layout_controller);
                list.setLayoutAnimation(controller);
                // arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, al);
                CustomAdapter adapter = new CustomAdapter(MainActivity.this, stringArray, imgList, stringArray1);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        String name = documentSnapshots.getDocuments().get(position).get("item").toString();
                        String price = documentSnapshots.getDocuments().get(position).get("price").toString();
                        Intent intent = new Intent(MainActivity.this, Second.class);
                        Pair[] pairs = new Pair[3];
                        pairs[0] = new Pair<View, String>(imageView, "images");
                        pairs[1] = new Pair<View, String>(tv1, "text1");
                        pairs[2] = new Pair<View, String>(tv2, "text2");

                        View transitionView = view.findViewById(R.id.img_item);
                        View transitionView1 = view.findViewById(R.id.txt_name);
                        View transitionView2 = view.findViewById(R.id.price);
                        Pair<View, String> p1 = Pair.create(transitionView, "images");
                        Pair<View, String> p2 = Pair.create(transitionView1, "text1");
                        Pair<View, String> p3 = Pair.create(transitionView2, "text2");
                        ViewCompat.setTransitionName(transitionView, "images");
                        ViewCompat.setTransitionName(transitionView1, "text1");
                        ViewCompat.setTransitionName(transitionView2, "text2");
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, p1);
                        intent.putExtra("name", name);
                        intent.putExtra("price", price);
                        startActivity(intent, options.toBundle());
                        //Animatoo.animateSlideRight(MainActivity.this);
                    }
                });
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(MainActivity.this, Cart1.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }

   /* @Override
    protected void onStart() {
        super.onStart();
        al = new ArrayList<>();
        imageButton = findViewById(R.id.imgbtn);
        imageView = findViewById(R.id.img_country);
        tv1 = findViewById(R.id.txt_name);
        tv2 = findViewById(R.id.price);
     //   refreshLayout = findViewById(R.id.swipe_refresh_layout);
        al1 = new ArrayList<>();
        list = (ListView) findViewById(R.id.lv);
        db.collection("canteen").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable final QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                String data, data1;
                al.removeAll(al);
                for (QueryDocumentSnapshot documentSnapshot : documentSnapshots) {
                    data = "";
                    data1 = "";
                    user = documentSnapshot.toObject(Users.class);
                    String orgName = user.getTitle();
                    String price = user.getPrice();
                    data += orgName + "\n";
                    data1 += "Rs." + " " + price + "\n";
                    al.add(data);
                    al1.add(data1);
                    stringArray = al.toArray(new String[al.size()]);
                    stringArray1 = al1.toArray(new String[al1.size()]);
                }
                // arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, al);
                CustomAdapter adapter = new CustomAdapter(MainActivity.this, stringArray, imgList, stringArray1);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        String name = documentSnapshots.getDocuments().get(position).get("title").toString();
                        String price = documentSnapshots.getDocuments().get(position).get("price").toString();
                        Intent intent = new Intent(MainActivity.this, Second.class);
                        Pair[] pairs = new Pair[3];
                        pairs[0] = new Pair<View, String>(imageView, "images");
                        pairs[1] = new Pair<View, String>(tv1, "text1");
                        pairs[2] = new Pair<View, String>(tv2, "text2");

                        View transitionView = view.findViewById(R.id.img_country);
                        View transitionView1 = view.findViewById(R.id.txt_name);
                        View transitionView2 = view.findViewById(R.id.price);
                        Pair<View, String> p1 = Pair.create(transitionView, "images");
                        Pair<View, String> p2 = Pair.create(transitionView1, "text1");
                        Pair<View, String> p3 = Pair.create(transitionView2, "text2");
                        ViewCompat.setTransitionName(transitionView, "images");
                        ViewCompat.setTransitionName(transitionView1, "text1");
                        ViewCompat.setTransitionName(transitionView2, "text2");
                        //ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                    /*    ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(MainActivity.this, transitionView, "images");*/
                   /*     ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, p1);
                        intent.putExtra("name", name);
                        intent.putExtra("price", price);
                        startActivity(intent, options.toBundle());
                        //Animatoo.animateSlideRight(MainActivity.this);
                    }
                });
            }
        });

    }*/
}
