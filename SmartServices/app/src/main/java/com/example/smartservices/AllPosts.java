package com.example.smartservices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllPosts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllPosts extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    View view;
    ImageRetrieve users;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    private List<String> expandableListNombres;
    private ArrayList<String> listaContactos;
    List<String> list;
    private int lastExpandedPosition = -1;

    public AllPosts() {

    }

    public static AllPosts newInstance(String param1, String param2) {
        AllPosts fragment = new AllPosts();
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
       view=inflater.inflate(R.layout.fragment_all_posts, container, false);
       list=new ArrayList<>();
       expandableListView =view.findViewById(R.id.expandableListView);
       expandableListNombres = new ArrayList<>();
        listaContactos=new ArrayList<>();
        db.collection("lost").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null)
                {
                    return;
                }
                list.removeAll(list);
                expandableListNombres.removeAll(expandableListNombres);
                for(QueryDocumentSnapshot documentSnapshot:documentSnapshots)
                {
                    users=documentSnapshot.toObject(ImageRetrieve.class);
                    String name=users.getName();
                    String phno=users.getPhoneno();
                    String place=users.getPlace();
                    String section=users.getSection();
                    String image=users.getImageurl();
                    String lostitem=users.getLostitem();
                    expandableListNombres.add(name+"\n"+phno+"\n"+section+"\n"+place+"\n"+lostitem);
                    list.add(image);
                }
                if(expandableListNombres.size()==0)
                {
                    expandableListNombres.add("Empty Records");
                    list.add("");
                }
                expandableListAdapter = new CustomExpandableListAdapter(getContext(),
                        expandableListNombres, list);
                expandableListView.setAdapter(expandableListAdapter);
            }
        });
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(lastExpandedPosition != -1 && groupPosition != lastExpandedPosition){
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        return view;
    }

}
