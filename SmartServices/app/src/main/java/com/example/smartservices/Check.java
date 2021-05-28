package com.example.smartservices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Check#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Check extends Fragment {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    private ArrayList<Item> items = new ArrayList<>();
    Button found;

    ImageRetrieve user;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Check() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_All.
     */
    // TODO: Rename and change types and number of parameters
    public static Check newInstance(String param1, String param2) {
        Check fragment = new Check();
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
        View view=inflater.inflate(R.layout.fragment_check, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        found=view.findViewById(R.id.found);
        recyclerView.setHasFixedSize(true);
        String email=getActivity().getIntent().getStringExtra("email");
        //items.add(new Item(R.drawable.icon,"Brazilian Coffee","In Brazil, they love coffee, they don\\’t just adore it. The average Brazilian drinks up"));
        db.collection("lost").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null)
                {
                    return;
                }
                String data;
                items.removeAll(items);
                for (QueryDocumentSnapshot documentSnapshot : documentSnapshots) {
                    data = "";
                    user = documentSnapshot.toObject(ImageRetrieve.class);
                    String item = user.getLostitem();
                    String place = user.getPlace();
                    String url=user.getImageurl();
                    String email1=user.getEmail();
                    String name=user.getName();
                    String section=user.getSection();
                    if(email.equalsIgnoreCase(email1)) {

                        items.add(new Item(url, item, "Name : "+name+"\n"+"Section : "+section+"\n"+"Place : "+place+"\n"+"email : "+email1));
                    }
                }
                if(items.size()==0)
                {

                    items.add(new Item("Hello","No Previous data found",""));
                }
                recyclerView.setAdapter(new Adapter(items,getContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
   return view;
    }
}
