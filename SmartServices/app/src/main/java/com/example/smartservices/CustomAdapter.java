package com.example.smartservices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    String itemlist[],price[];
    int imgList[];
    LayoutInflater inflater;  //for inflate layout items

    public CustomAdapter(Context context, String itemlist[], int imgList[], String price[])
    {
        this.itemlist=itemlist;
        this.imgList=imgList;
        this.price=price;
        inflater=(LayoutInflater.from(context));

    }

    @Override
    public int getCount() {

        return itemlist.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        view=inflater.inflate(R.layout.list_items,viewGroup,false);
        ImageView imgCountry=view.findViewById(R.id.img_item);
        TextView txtName=view.findViewById(R.id.txt_name);
        TextView txtpr=view.findViewById(R.id.price);
        if(position<imgList.length) {
            imgCountry.setImageResource(imgList[position]);
        }
        else
        {
            imgCountry.setImageResource(imgList[0]);
        }
        txtName.setText(itemlist[position]);
        txtpr.setText(price[position]);
        return view;
    }
}
