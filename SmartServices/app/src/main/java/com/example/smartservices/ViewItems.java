package com.example.smartservices;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ViewItems extends Fragment {
  ListView listView;
  List<String> list=new ArrayList<>();
  Users user;
  ArrayAdapter<Users> adapter;
  FirebaseFirestore db=FirebaseFirestore.getInstance();
    public ViewItems() {
        // Required empty public constructor
    }


    public static ViewItems newInstance(String param1, String param2) {
        ViewItems fragment = new ViewItems();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_view_items, container, false);
        listView=view.findViewById(R.id.viewItems);
        db.collection("canteen").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null)
                {
                    return;
                }
                String data;
                list.removeAll(list);
                for (QueryDocumentSnapshot documentSnapshot : documentSnapshots) {
                    data="";
                    user = documentSnapshot.toObject(Users.class);
                    String item="Item name :"+" "+user.getItem();
                    String price="Price :"+" "+"Rs. "+user.getPrice();
                    data+=item+"\n"+price;
                    list.add(data);
                    }
                adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
                listView.setAdapter(adapter);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Are you Sure");
        alertDialog.setMessage("You want to delete");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String str = parent.getItemAtPosition(position).toString();
                       String[] str1= str.split("\n");
                       String[] str2=str1[0].split(":");
                       str2[1]=str2[1].trim();
                        db.collection("canteen").document(str2[1]).delete();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
                return false;
            }
        });
        return view;
    }
}
