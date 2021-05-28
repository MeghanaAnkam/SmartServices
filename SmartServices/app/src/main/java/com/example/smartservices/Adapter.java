package com.example.smartservices;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;

import ru.embersoft.expandabletextview.ExpandableTextView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    ProgressDialog progressDialog;
    private ArrayList<Item> items;
    private Context context;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    long mLastClickTime = 0;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    public Adapter(ArrayList<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,
                parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, final int position) {

        final Item item = items.get(position);
        //holder.imageView.setImageResource(item.getImageResourse());
        holder.titleTextView.setText(item.getTitle());
        if(holder.titleTextView.getText().toString().contains("No Previous data"))
        {
            holder.found.setVisibility(View.INVISIBLE);
            holder.descTextView.setVisibility(View.INVISIBLE);
            holder.titleTextView.setPadding(25,20,15,15);
            holder.titleTextView.setTextColor(Color.parseColor("#000000"));
            //String a[]= email.split("@");
        }
        holder.descTextView.setText(item.getDesc());
        holder.descTextView.setOnStateChangeListener(new ExpandableTextView.OnStateChangeListener() {
            @Override
            public void onStateChange(boolean isShrink) {
                Item contentItem = items.get(position);
                contentItem.setShrink(isShrink);
                items.set(position, contentItem);
            }
        });
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        holder.descTextView.setText(item.getDesc());
        holder.descTextView.resetState(item.isShrink());
        holder.found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Are you Sure");
                alertDialog.setMessage("you found your lost item!!!");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                db.collection("lost").document(holder.titleTextView.getText().toString()).delete();
                                /*String storageUrl = "uploads/1590389616037.jpg";
                                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(storageUrl);
                                storageReference.delete();*/
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
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ExpandableTextView descTextView;
        TextView titleTextView;
        Button found;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            descTextView = itemView.findViewById(R.id.descTextView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            found=itemView.findViewById(R.id.found);
        }
    }
}
