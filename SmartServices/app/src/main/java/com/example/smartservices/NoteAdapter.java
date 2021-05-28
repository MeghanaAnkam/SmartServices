package com.example.smartservices;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.vstechlab.easyfonts.EasyFonts;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note,NoteAdapter.Noteholder> {


    private OnItemClickListener listener;
    Context context;
    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull Noteholder holder, int position, @NonNull Note model) {
        holder.link.setText(model.getLink());
        holder.desc.setText(model.getDesc());
        holder.title.setText(model.getTitle());
        holder.id.setText(model.getId());
        holder.title.setTypeface(EasyFonts.robotoBlack(context));
        holder.id.setVisibility(View.INVISIBLE);
        //    holder.prior.setText(String.valueOf(model.getPrior()));

    }

    @NonNull
    @Override
    public Noteholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,
                parent,false);

        return new Noteholder(v);
    }

    class Noteholder extends RecyclerView.ViewHolder{
        TextView link;
        //private OnItemClickListener listener;
        TextView desc;
        TextView title;
        TextView id;
        CardView cardView;
        // TextView prior;

        public Noteholder(@NonNull final View itemView) {
            super(itemView);
            link=itemView.findViewById(R.id.textview_link);
            desc=itemView.findViewById(R.id.textview_des);
            title=itemView.findViewById(R.id.textview_title);
            id=itemView.findViewById(R.id.id);
            cardView=itemView.findViewById(R.id.cardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in=new Intent(itemView.getContext(),Events_Admin1.class);
                    in.putExtra("link",link.getText().toString());
                    in.putExtra("desc",desc.getText().toString());
                    in.putExtra("title",title.getText().toString());
                    in.putExtra("id",id.getText().toString());
                    context.startActivity(in);
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
