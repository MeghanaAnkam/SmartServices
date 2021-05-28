package com.example.smartservices;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CleanAdminAdapter extends RecyclerView.Adapter<CleanAdminAdapter.CleanAdminViewHolder> {
    private Context mCtx;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    //we are storing all the products in a list
    private List<String> productList;

    //getting the context and product list with constructor
    public CleanAdminAdapter(Context mCtx, List<String> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public CleanAdminViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_admin_recycler, null);
        return new CleanAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CleanAdminViewHolder holder, int position) {
        //getting the product of the specified position
        String product = productList.get(position);
        String[] str=product.split("\n");
        if(str[0].equalsIgnoreCase("No Previous data found"))
        {
            holder.imageView.setImageResource(R.drawable.images);
            holder.textViewTitle.setText("No Previous data found");
            holder.textViewShortDesc.setVisibility(View.INVISIBLE);
            holder.textViewRating.setVisibility(View.INVISIBLE);
            holder.cardView.setOnClickListener(null);
        }
        else {
            //binding the data with the viewholder views
            holder.textViewTitle.setText(str[0]);
            holder.textViewShortDesc.setText(str[3]);
            holder.textViewRating.setText(String.valueOf(str[1]));
            Picasso.get().load(str[2]).into(holder.imageView);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mCtx, PopUp.class);
                    Pair[] pairs = new Pair[3];
                    pairs[0] = new Pair<View, String>(holder.imageView, "postname");
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mCtx, pairs[0]);
                    intent.putExtra("Image", str[2]);
                    mCtx.startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class CleanAdminViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView;
        CardView cardView;

        public CleanAdminViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            imageView = itemView.findViewById(R.id.clean_image);
            cardView=itemView.findViewById(R.id.clean_card);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder= new AlertDialog.Builder(mCtx);
                    builder.setIcon(android.R.drawable.stat_sys_warning);
                    builder.setTitle("Are you sure");
                    builder.setMessage("Did the task is completed ?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.collection("cleanliness").document(textViewTitle.getText().toString()).delete();

                            Toast.makeText(mCtx, "Great work...", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }
            });
        }
    }
}
