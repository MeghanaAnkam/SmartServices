package com.example.smartservices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Admin_List#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Admin_List extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

ListView lv;
ArrayList<String> al=new ArrayList<>();
ArrayAdapter<String> adapter;
FirebaseFirestore db=FirebaseFirestore.getInstance();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Admin_List() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Admin_List.
     */
    // TODO: Rename and change types and number of parameters
    public static Admin_List newInstance(String param1, String param2) {
        Admin_List fragment = new Admin_List();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.activity_list, container, false);
       lv= view.findViewById(R.id.list_admin);

       db.collection("users").document("roles").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               final DocumentSnapshot documentSnapshot=task.getResult();
               if(documentSnapshot.exists())
               {
                   final String str="Canteen Admin : "+"\n"+documentSnapshot.get("canteen_admin").toString()+"\n";
                  String str1= "Clean Admin : "+"\n"+ documentSnapshot.get("clean_admin").toString()+"\n";
                  String str2= "Events Admin : "+"\n"+documentSnapshot.get("events_admin").toString()+"\n";
                 al.add(str);
                 al.add(str1);
                 al.add(str2);
                   adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,al);
                   lv.setAdapter(adapter);
               }


           }
       });
        return view;
    }
}
