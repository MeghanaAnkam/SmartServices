package com.example.smartservices;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    private Context mcontext;
    private List<Ordersdb> list;

    public ListAdapter(Context mcontext, List<Ordersdb> list) {
        this.mcontext = mcontext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=View.inflate(mcontext,R.layout.orders_product,null);
        TextView tv1=view.findViewById(R.id.tvname);
        TextView tv2=view.findViewById(R.id.tvquan);
        TextView tv3=view.findViewById(R.id.tvorder);
        tv1.setText(list.get(position).getOrderno());
        tv2.setText(list.get(position).getItems());
        tv3.setText(list.get(position).getEmail());
        return view;
    }
}
