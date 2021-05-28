package com.example.smartservices;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;
    Context context;
    public CardPagerAdapter(Context context) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        mData.removeAll(mData);
        mViews.removeAll(mViews);
        this.context=context;
    }


    public void addCardItem(CardItem item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {

        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }
        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(final CardItem item, View view) {
        final TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        final TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
        final TextView link=view.findViewById(R.id.link);
        titleTextView.setText(item.getTitle());
        contentTextView.setText(item.getText());
        link.setText(item.getlink());
        link.setVisibility(View.INVISIBLE);
        CardView cardView=view.findViewById(R.id.cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(context,EventsActivity2.class);
                in.putExtra("title",titleTextView.getText().toString());
                in.putExtra("desc",contentTextView.getText().toString());
                in.putExtra("link",link.getText().toString());
                context.startActivity(in);
                Animatoo.animateZoom(context);
            }
        });

    }
}
