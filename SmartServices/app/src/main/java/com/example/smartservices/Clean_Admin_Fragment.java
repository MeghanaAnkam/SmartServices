package com.example.smartservices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Clean_Admin_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Clean_Admin_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    List<String> productList;
    //the recyclerview
    CleanAdminGetter cag;
    RecyclerView recyclerView;

    public Clean_Admin_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Clean_Admin_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Clean_Admin_Fragment newInstance(String param1, String param2) {
        Clean_Admin_Fragment fragment = new Clean_Admin_Fragment();
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
        View view= inflater.inflate(R.layout.activity_clean_admin, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.cleanAdminRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //initializing the productlist
        productList = new ArrayList<String>();

        db.collection("cleanliness").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null)
                {
                    return;
                }
                productList.removeAll(productList);
                for(QueryDocumentSnapshot queryDocumentSnapshot:documentSnapshots)
                {
                    cag=queryDocumentSnapshot.toObject(CleanAdminGetter.class);
                    String dustplace=cag.getDustplace();
                    String rollno=cag.getRollno();
                    String image=cag.getUrl();
                    String email=cag.getEmail();
                    productList.add(dustplace+"\n"+rollno+"\n"+image+"\n"+email);
                }
                if(productList.size()==0)
                {
                    productList.add("No Previous data found"+"\n"+" "+"\n"+" "+"\n"+" ");
                }
                CleanAdminAdapter adapter = new CleanAdminAdapter(getContext(), productList);

                //setting adapter to recyclerview
                recyclerView.setAdapter(adapter);
            }
        });

        return view;
    }
}
