package com.example.smartservices;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;

public class Cart1 extends AppCompatActivity {
    private ExpandingList mExpandingList;
    ProgressBar progressBar;
    TextView tv;
    private SlidrInterface slidr;
    int tot1 = 0;
    long mLastClickTime = 0;
    ProgressDialog progressDialog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> list = new ArrayList<>();
    ImageView orders;
    ArrayAdapter<String> arrayAdapter;
    String email;
    DatabaseHelper1 mydb;
    private int refreshcount = 0;
    Button btn;
    private SwipeRefreshLayout refreshLayout;

    ListenerRegistration registration;

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
                window.setStatusBarColor(Color.parseColor("#1e3d59"));
            }

        }
        setContentView(R.layout.activity_cart1);
        slidr = Slidr.attach(this);
        orders = findViewById(R.id.myorders);
        email = getIntent().getStringExtra("email");
        mydb = new DatabaseHelper1(Cart1.this);
    /*    db.collection("rohith2712@gmail.com")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;
                                if(count==0)
                                {
                                    Intent intent = new Intent(Cart1.this, Empty.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        } else {
                            Log.d("Hello", "Error getting documents: ", task.getException());
                        }
                    }
                });*/
        mExpandingList = findViewById(R.id.expanding_list_main);
        //refreshLayout = findViewById(R.id.swipe_refresh_layout);
        btn = findViewById(R.id.btn_pay);
//        progressBar=findViewById(R.id.progressbar);
        mExpandingList.removeAllViews();
        tv = findViewById(R.id.tot);
        // progressBar.setVisibility(View.VISIBLE);
      /*progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );*/
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(Cart1.this, MyOrders.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
        createItems();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv.getText().toString() != null) {
                    Intent intent = new Intent(Cart1.this, Payment.class);
                    intent.putExtra("email", email);
                    intent.putExtra("total", tot1);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void createItems() {
        Cursor res = mydb.getAllData();
        if (res.getCount() == 0) {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Cart1.this, Empty.class);
            intent.putExtra("email", email);
            startActivity(intent);
            finish();
            return;
        }
        tot1 = 0;
        while (res.moveToNext()) {
            addItem(res.getString(0), new String[]{res.getString(1), res.getString(2), res.getString(3)}, R.color.sky, R.drawable.din);
            tot1 += Integer.parseInt(res.getString(3));
        }
        tv.setText("Total Bill Amount:" + " " + "RS." + " " + tot1);
    }

    private void addItem(final String title, String[] subItems, int colorRes, int iconRes) {
        //Let's create an item with R.layout.expanding_layout

        final ExpandingItem item = mExpandingList.createNewItem(R.layout.expanding_layout);
        subItems[0] = "Price: " + subItems[0];
        subItems[1] = "Quantity: " + subItems[1];
        subItems[2] = "Total: " + subItems[2];
        //If item creation is successful, let's configure it
        if (item != null) {
            item.setIndicatorColorRes(colorRes);
            item.setIndicatorIconRes(iconRes);
            //It is possible to get any view inside the inflated layout. Let's set the text in the item
            ((TextView) item.findViewById(R.id.title)).setText(title);

            //We can create items in batch.
            item.createSubItems(subItems.length);
            for (int i = 0; i < item.getSubItemsCount(); i++) {
                //Let's get the created sub item by its index
                final View view = item.getSubItemView(i);

                //Let's set some values in
                configureSubItem(item, view, subItems[i]);
            }
            item.findViewById(R.id.add_more_sub_items).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //progressBar.setVisibility(View.VISIBLE);
//                    progressDialog.show();
                    mExpandingList.removeAllViews();
                    Cursor res = mydb.getAllData();
                    mExpandingList.removeItem(item);
                    while (res.moveToNext()) {
                        if (title.equalsIgnoreCase(res.getString(0))) {
                            mydb.updateData(res.getString(0), res.getString(1), String.valueOf(Integer.parseInt(res.getString(2)) + 1), String.valueOf((Integer.parseInt(res.getString(2)) + 1) * (Integer.parseInt(res.getString(1)))));
                        }
                    }
                    createItems();

                }

            });

            item.findViewById(R.id.remove_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    progressDialog.show();
                    mExpandingList.removeAllViews();
                    Cursor res = mydb.getAllData();
                    mExpandingList.removeItem(item);
                    while (res.moveToNext()) {
                        if (title.equalsIgnoreCase(res.getString(0))) {
                            mydb.deleteData(res.getString(0));
                        }
                    }
                    createItems();
                }
            });

        }
    }

    private void configureSubItem(final ExpandingItem item, final View view, String subTitle) {
        ((TextView) view.findViewById(R.id.sub_title)).setText(subTitle);
    }


    interface OnItemCreated {
        void itemCreated(String title);
    }
}
